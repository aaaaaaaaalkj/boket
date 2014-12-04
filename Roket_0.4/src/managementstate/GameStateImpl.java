package managementstate;

import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;

import common.Round;

public final class GameStateImpl implements IGameState {
	private final Round round;
	private final Round old;
	private final int next;
	private final int numActive;
	private final Set<Integer> notFoldedPlayers;

  private static final int ILLEGAL_NEXT = -1;
  private static final int ILLEGAL_NUM_ACTIVE = -1;

	private static final String[] POSITIONS = { "BUTTON", "SB", "BB", "UTG",
			"UTG+1", "UTG+2", "UTG+3", "UTG+4", "UTG+5", "UTG+6" };

  private GameStateImpl(final Round round, final Round oldRound, final int next,
      final int numActive, final Set<Integer> notFolded) {
		this.round = round;
		this.old = oldRound;
		this.next = next;
		this.numActive = numActive;
		this.notFoldedPlayers = notFolded;
	}

  public static GameStateImpl showDown(final Round old, final Set<Integer> notFolded) {
		return new GameStateImpl(Round.SHOWDOWN, old, ILLEGAL_NEXT,
				ILLEGAL_NUM_ACTIVE,
				notFolded);
	}

  public static GameStateImpl quietEnd(final Round old, final int winner,
      final Set<Integer> notFolded) {
		return new GameStateImpl(Round.QUIET_END, old, winner,
				ILLEGAL_NUM_ACTIVE, notFolded);
	}

  public static GameStateImpl create(final Round r, final Round oldRound, final int next,
      final int numActive, final Set<Integer> notFolded) {
		return new GameStateImpl(r, oldRound, next, numActive, notFolded);
	}

	@Override
	public int getCurrentPlayer() {
		return next;
	}

	public String toString() {
		return round + (next == ILLEGAL_NEXT ? "" : " " + POSITIONS[next]);
	}

	@Override
	public Round getRound() {
		return round;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + next;
		result = prime
				* result
				+ (notFoldedPlayers.hashCode());
		result = prime * result + numActive;
		result = prime * result + (old.hashCode());
		result = prime * result + (round.hashCode());
		return result;
	}

	@Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
		GameStateImpl other = (GameStateImpl) obj;
    if (next != other.next) {
      return false;
    }
    if (!notFoldedPlayers.equals(other.notFoldedPlayers)) {
      return false;
    }
    if (numActive != other.numActive) {
      return false;
    }
    if (old != other.old) {
      return false;
    }
    if (round != other.round) {
      return false;
    }
		return true;
	}

}
