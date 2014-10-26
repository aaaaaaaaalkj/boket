package managementState;

import java.util.Set;

import common.Round;

public class GameStateImpl implements IGameState {
	private final Round round;
	private final Round old;
	private final Integer next;
	private final Integer numActive;
	private final Set<Integer> notFoldedPlayers;

	private static final String[] POSITIONS = { "BUTTON", "SB", "BB", "UTG",
			"UTG+1", "UTG+2", "UTG+3", "UTG+4", "UTG+5", "UTG+6" };

	private GameStateImpl(Round round, Round oldRound, Integer next,
			Integer numActive, Set<Integer> notFolded) {
		this.round = round;
		this.old = oldRound;
		this.next = next;
		this.numActive = numActive;
		this.notFoldedPlayers = notFolded;
	}

	public static GameStateImpl showDown(Round old, Set<Integer> notFolded) {
		return new GameStateImpl(Round.SHOWDOWN, old, null, null, notFolded);
	}

	public static GameStateImpl quietEnd(Round old, int winner,
			Set<Integer> notFolded) {
		return new GameStateImpl(Round.QUIET_END, old, winner, null, notFolded);
	}

	public static GameStateImpl create(Round r, Round oldRound, Integer x,
			Integer numActive, Set<Integer> notFolded) {
		return new GameStateImpl(r, oldRound, x, numActive, notFolded);
	}

	@Override
	public Integer getCurrentPlayer() {
		return next;
	}

	public String toString() {
		return round + (next == null ? "" : " " + POSITIONS[next]);
	}

	@Override
	public Round getRound() {
		return round;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((round == null) ? 0 : round.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameStateImpl other = (GameStateImpl) obj;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (round != other.round)
			return false;
		return true;
	}

	@Override
	public boolean newRound() {
		return old != round && !round.gameEnded();
	}

	@Override
	public int numActivePlayers() {
		return numActive;
	}

	@Override
	public Set<Integer> getNotFoledePlayers() {
		return notFoldedPlayers;
	}
}
