package managementState;

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

	public static IStateManagement setUp(int numSeats) {
		return Carousel.normalGameStart(numSeats);
	}

	@Test
	public void QuitEndTest() {

		IStateManagement m = setUp(4);

		IGameState state = m.step();

		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertTrue("state: " + state, state.gameEnded());
		assertTrue(state.getRound() == Round.QUIET_END);
	}

	@Test
	public void RichMiddleManTest() {

		IStateManagement m = setUp(4);

		IGameState state = m.step();

		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.PREFLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();

		state = m.step();
		assertTrue("state" + state, state.gameEnded());
		assertTrue(state.getRound() == Round.SHOWDOWN);

	}

	@Test
	public void EarlyShowdownTest() {
		IStateManagement m = setUp(4);
		IGameState state = m.step();

		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.PREFLOP);

		state = m.step();
		assertTrue("state: " + state, state.gameEnded());
		assertTrue(state.getRound() == Round.SHOWDOWN);

	}

	@Test
	public void allAllinTest() {
		IStateManagement m = setUp(4);
		IGameState state = m.step();

		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertTrue(state.gameEnded());
		assertTrue(state.getRound() == Round.SHOWDOWN);
	}

	@Test
	public void allAllinTest2() {
		IStateManagement m = setUp(4);
		IGameState state = m.step();

		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertTrue(state.gameEnded());
		assertTrue(state.getRound() == Round.SHOWDOWN);
	}

	@Test
	public void onlyOneAllInPlayer() {
		IStateManagement m = setUp(4);
		IGameState state = m.step();

		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.allin();
		m.raised();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.fold();

		state = m.step();
		assertTrue(state.gameEnded());
		assertTrue(state.getRound() == Round.QUIET_END);

	}

	@Test
	public void ShowDownTest() {
		IStateManagement m = setUp(4);
		IGameState state = m.step();

		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.PREFLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.PREFLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.PREFLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.PREFLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.FLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.FLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.FLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.FLOP);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.TURN);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.TURN);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.TURN);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.TURN);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.RIVER);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.RIVER);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 3);
		assertTrue(state.getRound() == Round.RIVER);

		state = m.step();
		assertFalse(state.gameEnded());
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.RIVER);

		state = m.step();
		assertTrue(state.gameEnded());
		assertTrue(state.getRound() == Round.SHOWDOWN);

	}

	@Test
	public void randomTest() {
		IStateManagement m = setUp(4);
		Collection<Integer> folded = new ArrayList<>();
		Collection<Integer> allin = new ArrayList<>();

		IGameState state;

		while (!(state = m.step()).gameEnded()) {
			@SuppressWarnings("null")
			@NonNull
			Integer current = state.getCurrentPlayer();

			// its never the turn of a folded player
			assertFalse(folded.contains(current));
			// its never the turn of an allin player
			assertFalse(allin.contains(current));

			if (Math.random() < .2) {
				m.fold();
				folded.add(current);
			} else if (Math.random() < .2) {
				m.allin();
				allin.add(current);

				if (Math.random() < .2) {
					m.raised();

				}
			}
		}
	}

	@Test
	public void allinSeveralRoundsTest() {
		IStateManagement m = setUp(3);
		IGameState state;

		state = m.step();
		assertTrue(state.getCurrentPlayer() == 0);
		assertTrue(state.getRound() == Round.PREFLOP);
		m.raised();
		m.allin();

		state = m.step();
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.PREFLOP);

		state = m.step();
		assertTrue(state.getCurrentPlayer() == 2);
		assertTrue(state.getRound() == Round.PREFLOP);

		state = m.step();
		assertTrue(state.getCurrentPlayer() == 1);
		assertTrue(state.getRound() == Round.FLOP);
		m.fold();

		state = m.step();
		assertTrue(state.getRound() == Round.SHOWDOWN);

	}

	@Test
	public void randomTest2() {
		IStateManagement m = setUp(4);
		Set<Integer> activePlayers = new HashSet<>();
		activePlayers.add(0);
		activePlayers.add(1);
		activePlayers.add(2);
		activePlayers.add(3);

		Set<Integer> todoPlayers = new HashSet<>();
		todoPlayers.add(0);
		todoPlayers.add(1);
		todoPlayers.add(2);
		todoPlayers.add(3);

		int allinPlayers = 0;

		Round old = Round.PREFLOP;
		IGameState state;
		while (!(state = m.step()).gameEnded()) {
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

			if (Math.random() < .2) {
				// System.out.println(state + ": fold");
				activePlayers.remove(state.getCurrentPlayer());
				m.fold();
			} else {
				if (Math.random() < .2) {
					if (Math.random() < .2) {
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
				} else {
					// System.out.println(state + ": call ");
				}
			}

		}
		assertTrue(state.gameEnded());
		// System.out.println(state);
	}
}
