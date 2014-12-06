package managementstate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

import common.Round;

public class TestRotation {
  private static final int NUM_SEATS = 4;
  private static final double PROBABILITY_FOR_FOLD_RAISE = .2;
  private static final int UTG = 3;
  private static final int BUTTON = 0;
  private static final int SMALL_BLIND = 1;
  private static final int BIG_BLIND = 2;

  public static IStateManagement setUp(final int numSeats) {
    return Carousel.normalGameStart(numSeats);
  }

  @Test
  public final void quitEndTest() {

    IStateManagement m = setUp(NUM_SEATS);

    IGameState state = m.step();

    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertTrue("state: " + state, state.gameEnded());
    assertTrue(state.getRound() == Round.QUIET_END);
  }

  @Test
  public final void richMiddleManTest() {

    IStateManagement m = setUp(NUM_SEATS);

    IGameState state = m.step();

    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();

    state = m.step();
    assertTrue("state" + state, state.gameEnded());
    assertTrue(state.getRound() == Round.SHOWDOWN);

  }

  @Test
  public final void earlyShowdownTest() {
    IStateManagement m = setUp(NUM_SEATS);
    IGameState state = m.step();

    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);

    state = m.step();
    assertTrue("state: " + state, state.gameEnded());
    assertTrue(state.getRound() == Round.SHOWDOWN);

  }

  @Test
  public final void allAllinTest() {
    IStateManagement m = setUp(NUM_SEATS);
    IGameState state = m.step();

    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertTrue(state.gameEnded());
    assertTrue(state.getRound() == Round.SHOWDOWN);
  }

  @Test
  public final void allAllinTest2() {
    IStateManagement m = setUp(NUM_SEATS);
    IGameState state = m.step();

    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertTrue(state.gameEnded());
    assertTrue(state.getRound() == Round.SHOWDOWN);
  }

  @Test
  public final void onlyOneAllInPlayer() {
    IStateManagement m = setUp(NUM_SEATS);
    IGameState state = m.step();

    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.allin();
    m.raised();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.fold();

    state = m.step();
    assertTrue(state.gameEnded());
    assertTrue(state.getRound() == Round.QUIET_END);

  }

  @Test
  public final void showDownTest() {
    IStateManagement m = setUp(NUM_SEATS);
    IGameState state = m.step();

    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.PREFLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.PREFLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.FLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.FLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.FLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.FLOP);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.TURN);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.TURN);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.TURN);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.TURN);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.RIVER);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.RIVER);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == UTG);
    assertTrue(state.getRound() == Round.RIVER);

    state = m.step();
    assertFalse(state.gameEnded());
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.RIVER);

    state = m.step();
    assertTrue(state.gameEnded());
    assertTrue(state.getRound() == Round.SHOWDOWN);

  }

  @Test
  public final void randomTest() {
    IStateManagement m = setUp(NUM_SEATS);
    Collection<Integer> folded = new ArrayList<>();
    Collection<Integer> allin = new ArrayList<>();

    IGameState state;

    for (state = m.step(); !state.gameEnded(); state = m.step()) {
      @SuppressWarnings("null")
      @NonNull
      Integer current = state.getCurrentPlayer();

      // its never the turn of a folded player
      assertFalse(folded.contains(current));
      // its never the turn of an allin player
      assertFalse(allin.contains(current));

      if (Math.random() < PROBABILITY_FOR_FOLD_RAISE) {
        m.fold();
        folded.add(current);
      } else if (Math.random() < PROBABILITY_FOR_FOLD_RAISE) {
        m.allin();
        allin.add(current);

        if (Math.random() < PROBABILITY_FOR_FOLD_RAISE) {
          m.raised();

        }
      }
    }
  }

  @Test
  public final void allinSeveralRoundsTest() {
    final int numPlayers = 3;
    IStateManagement m = setUp(numPlayers);
    IGameState state;

    state = m.step();
    assertTrue(state.getCurrentPlayer() == BUTTON);
    assertTrue(state.getRound() == Round.PREFLOP);
    m.raised();
    m.allin();

    state = m.step();
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);

    state = m.step();
    assertTrue(state.getCurrentPlayer() == BIG_BLIND);
    assertTrue(state.getRound() == Round.PREFLOP);

    state = m.step();
    assertTrue(state.getCurrentPlayer() == SMALL_BLIND);
    assertTrue(state.getRound() == Round.FLOP);
    m.fold();

    state = m.step();
    assertTrue(state.getRound() == Round.SHOWDOWN);

  }

  @Test
  public final void randomTest2() {
    IStateManagement m = setUp(NUM_SEATS);
    Set<Integer> activePlayers = new HashSet<>();
    Set<Integer> todoPlayers = new HashSet<>();

    for (int i = 0; i < NUM_SEATS; i++) {
      activePlayers.add(i);
      todoPlayers.add(i);
    }

    int allinPlayers = 0;

    Round old = Round.PREFLOP;
    IGameState state;
    for (state = m.step(); !state.gameEnded(); state = m.step()) {
      if (state.getRound() != old) {
        assertTrue(state.toString() + "__" + todoPlayers,
            todoPlayers.isEmpty());
        assertTrue(activePlayers.size() + allinPlayers >= 2);

        old = state.getRound();
        todoPlayers = new HashSet<>(activePlayers);
      }
      assertTrue(state.toString() + "__" + todoPlayers,
          todoPlayers.contains(state.getCurrentPlayer()));
      assertTrue(activePlayers.contains(state.getCurrentPlayer()));
      todoPlayers.remove(state.getCurrentPlayer());

      if (Math.random() < PROBABILITY_FOR_FOLD_RAISE) {
        // System.out.println(state + ": fold");
        activePlayers.remove(state.getCurrentPlayer());
        m.fold();
      } else {
        if (Math.random() < PROBABILITY_FOR_FOLD_RAISE) {
          if (Math.random() < PROBABILITY_FOR_FOLD_RAISE) {
            // System.out.println(state + ": allin");
            allinPlayers++;
            m.allin();
            activePlayers.remove(state.getCurrentPlayer());
          } else {
            // System.out.println(state + ": raised");
            todoPlayers = new HashSet<>(activePlayers);
            todoPlayers.remove(state.getCurrentPlayer());
            m.raised();
          }
        }
      }

    }
    assertTrue(state.gameEnded());
    // System.out.println(state);
  }
}
