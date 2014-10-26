package managementState;

import java.util.Set;

import common.Round;

public interface IGameState {

	Integer getCurrentPlayer();

	Round getRound();

	boolean newRound();

	int numActivePlayers();

	default boolean gameEnded() {
		return getRound().gameEnded();
	}

	Set<Integer> getNotFoledePlayers();

}
