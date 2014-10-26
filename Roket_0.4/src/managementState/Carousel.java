package managementState;

import java.util.HashSet;
import java.util.Set;

import managementPaymentsNew.decisions.DecisionType;

import common.Round;

public class Carousel implements IStateManagement {
	private Round round;
	Set<Integer> allinPlayers = new HashSet<>();
	boolean nobodyRaised;
	Set<Integer> activePlayers = new HashSet<>();
	Set<Integer> todoPlayers = new HashSet<>();
	int currentIndex;
	int numSeats;

	public Carousel(int numSeats) {
		this.nobodyRaised = true;
		this.allinPlayers = new HashSet<>();
		this.round = Round.PREFLOP;
		this.numSeats = numSeats;
		for (int i = 0; i < numSeats; i++) {
			activePlayers.add(i);
			todoPlayers.add(i);
		}
		currentIndex = 2;
	}

	private GameStateImpl showDown(Round old) {
		return GameStateImpl.showDown(old, getNotFolded());
	}

	private GameStateImpl quiteEnd(Round old) {
		return GameStateImpl.quietEnd(old,
				activePlayers.size() > 0 ?
						// find the winning player
						activePlayers.stream().findAny().orElse(null)
						: allinPlayers.stream().findAny().orElse(null)
				, getNotFolded());
	}

	private GameStateImpl gameEnd(Round old) {
		return allinPlayers.size() + activePlayers.size() > 1 ?
				showDown(old)
				: quiteEnd(old);
	}

	public IGameState step() {
		Round oldRound = round;
		if (activePlayers.isEmpty()) {
			return gameEnd(oldRound);
		}
		if ((nobodyRaised || todoPlayers.isEmpty())
				&& activePlayers.size() == 1) {
			return gameEnd(oldRound);
		}

		if (todoPlayers.isEmpty()) {
			round = round.next();
			todoPlayers = new HashSet<>(activePlayers);
			currentIndex = 0;
			nobodyRaised = true;
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

	public static Carousel normalGameStart(int numSeats) {
		return new Carousel(numSeats);
	}

	@Override
	public void update(DecisionType d) {
		switch (d) {
		case ALL_IN:
			allinPlayers.add(currentIndex);
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
