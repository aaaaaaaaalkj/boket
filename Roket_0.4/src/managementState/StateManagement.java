package managementState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.IPlayer;

public class StateManagement {
	private final LinkedList<IPlayer> allPlayers;
	private final Map<IPlayer, PlayerState> states;
	private final Set<IPlayer> waitingPlayers;
	private int currentIndex;

	public StateManagement() {
		this.allPlayers = new LinkedList<>();
		this.states = new HashMap<>();
		this.waitingPlayers = new HashSet<>();
	}

	public void register(IPlayer p) {
		states.put(p, PlayerState.ACTIVE);
		waitingPlayers.add(p);
		allPlayers.add(p);
		currentIndex = allPlayers.size() - 1; // button
	}

	public List<IPlayer> roundEnd() {
		currentIndex = allPlayers.size() - 1; // button
		waitingPlayers.addAll(getActivePlayers());

		List<IPlayer> res = getActivePlayers();
		res.addAll(getAllInPlayers());
		return res;
	}

	public void sb() {
		inc();
	}

	public void bb() {
		inc();
	}

	public List<IPlayer> getPlayersInGame() {
		List<IPlayer> res = getActivePlayers();
		res.addAll(getAllInPlayers());
		return res;
	}

	public List<IPlayer> getActivePlayers() {
		return getPlayersByStatus(PlayerState.ACTIVE);
	}

	public List<IPlayer> getAllInPlayers() {
		return getPlayersByStatus(PlayerState.ALL_IN);
	}

	private List<IPlayer> getPlayersByStatus(PlayerState s) {
		List<IPlayer> list = new ArrayList<>();
		for (IPlayer p : states.keySet()) {
			if (states.get(p).equals(s))
				list.add(p);
		}
		return list;
	}

	private PlayerState getState(int index) {
		return states.get(get(index));
	}

	private IPlayer get(int index) {
		return allPlayers.get(index);
	}

	public IPlayer getCurrent() {
		return get(currentIndex);
	}

	public void playerDone() {
		waitingPlayers.remove(getCurrent());
	}

	public IPlayer next() {
		inc();
		return getCurrent();
	}

	private void inc() {
		int old = currentIndex;
		do {
			currentIndex++;
			if (currentIndex >= allPlayers.size())
				currentIndex = 0;
			if (old == currentIndex)
				throw new IllegalStateException(
						"Only one active Player at Table");
		} while (getState(currentIndex) != PlayerState.ACTIVE);
	}

	public void allIn(boolean all_in) {
		if (all_in)
			states.put(getCurrent(), PlayerState.ALL_IN);
	}

	public void raised(boolean raised) {
		if (raised) {
			waitingPlayers.addAll(getActivePlayers());
		}
	}

	public void fold(IPlayer player) {
		states.put(player, PlayerState.FOLDED);
	}

	public boolean hasNext() {
		return waitingPlayers.size() > 0 && getActivePlayers().size() > 1;
	}

	// /**
	// * if one game is over the button moves to the next player
	// */
	// public void rotate() {
	// PlayerId first = allPlayers.getFirst();
	// allPlayers.add(allPlayers.removeFirst());
	// if (first == allPlayers.getFirst())
	// throw new IllegalStateException(
	// "Less than two active players at Table");
	// }
}
