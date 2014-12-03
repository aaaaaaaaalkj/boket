package managementState;

import java.util.HashSet;
import java.util.Set;

import managementPaymentsNew.decisions.DecisionType;

import org.eclipse.jdt.annotation.NonNull;

import common.Round;

public class Carousel implements IStateManagement {
  private Round round;
  private Set<Integer> allinPlayers = new HashSet<>();
  private boolean nobodyRaised;
  private Set<Integer> activePlayers = new HashSet<>();
  private Set<Integer> todoPlayers = new HashSet<>();
  private int currentIndex;
  private int numSeats;

  public Carousel(final int numSeats) {
    this.nobodyRaised = true;
    this.allinPlayers = new HashSet<>();
    this.round = Round.PREFLOP;
    this.numSeats = numSeats;
    for (Integer player = 0; player < numSeats; player++) {
      activePlayers.add(player);
      todoPlayers.add(player);
    }
    currentIndex = 2;
  }

  private GameStateImpl showDown(final Round old) {
    return GameStateImpl.showDown(old, getNotFolded());
  }

  private GameStateImpl quiteEnd(final Round old) {
    int winningPlayer;
    if (activePlayers.size() > 0) {
      winningPlayer = activePlayers.iterator().next();
    } else {
      throw new IllegalStateException("no winning player found");
    }
    return GameStateImpl.quietEnd(
        old,
        winningPlayer,
        getNotFolded());
  }

  private GameStateImpl gameEnd(final Round old) {
    return allinPlayers.size() + activePlayers.size() > 1 ?
        showDown(old)
        : quiteEnd(old);
  }

  public final IGameState step() {
    Round oldRound = round;
    if (activePlayers.isEmpty()) {
      return gameEnd(oldRound);
    }
    if ((nobodyRaised || todoPlayers.isEmpty())
        && activePlayers.size() == 1) {
      return gameEnd(oldRound);
    }

    if (todoPlayers.isEmpty()) {
      Round next = round.next();
      if (next == null) {
        throw new IllegalStateException(
            "cant proceed to next round after " + round);
      } else {
        round = next;
        todoPlayers = new HashSet<>(activePlayers);
        currentIndex = 0;
        nobodyRaised = true;
      }
    }

    do {
      currentIndex = currentIndex == numSeats ? 0 : currentIndex + 1;
    } while (!activePlayers.contains(currentIndex));

    todoPlayers.remove(currentIndex);

    return GameStateImpl.create(round, oldRound, currentIndex,
        activePlayers.size(), getNotFolded());
  }

  private Set<Integer> getNotFolded() {
    Set<Integer> res = new HashSet<>();
    res.addAll(activePlayers);
    res.addAll(allinPlayers);
    return res;
  }

  public static Carousel normalGameStart(final int numSeats) {
    return new Carousel(numSeats);
  }

  @Override
  public final void update(final DecisionType d) {
    switch (d) {
    case ALL_IN:
      @SuppressWarnings("null")
      @NonNull
      Integer currentPlayer = currentIndex;
      allinPlayers.add(currentPlayer);
      activePlayers.remove(currentIndex);
      break;
    case BET: // same as raise
    case RAISE:
      nobodyRaised = false;
      todoPlayers = new HashSet<>(activePlayers);
      todoPlayers.remove(currentIndex);
      break;
    case CALL: // do nothing
      break;
    case CHECK: // do nothing
      break;
    case FOLD:
      activePlayers.remove(currentIndex);
      break;
    default:
      throw new IllegalStateException("Unexpected enum-constant: " + d);
    }
  }
}
