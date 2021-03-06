package managementstate;

import java.util.HashSet;
import java.util.Set;

import managementpaymentsnewtmp.decisions.DecisionType;

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

    if (activePlayers.isEmpty() && allinPlayers.isEmpty()) {
      throw new IllegalStateException("no winning player found");
    }
    Set<Integer> notFolded = getNotFolded();
    // quiteEnd means there is only one player left
    winningPlayer = notFolded.iterator().next();

    return GameStateImpl.quietEnd(
        old,
        winningPlayer,
        notFolded);
  }

  private GameStateImpl gameEnd(final Round old) {
    return allinPlayers.size() + activePlayers.size() > 1
        ? showDown(old)
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

    goToNextRoundIfEverybodyIsDone();

    findNextActivePlayer();

    return GameStateImpl.create(
        round,
        oldRound,
        currentIndex,
        activePlayers.size(),
        getNotFolded());
  }

  private final void findNextActivePlayer() {
    do {
      currentIndex = (currentIndex == numSeats) ? 0 : currentIndex + 1;
    } while (!activePlayers.contains(currentIndex));
    todoPlayers.remove(currentIndex);
  }

  private final void goToNextRoundIfEverybodyIsDone() {
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
