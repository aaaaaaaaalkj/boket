package managementstate;

import java.util.Set;

import common.Round;

public interface IGameState {

	int getCurrentPlayer();

	Round getRound();

	boolean newRound();

	int numActivePlayers();

	default boolean gameEnded() {
		return getRound().gameEnded();
	}

	Set<Integer> getNotFoledePlayers();

}
