package managementPaymentsNew;

import java.util.HashSet;
import java.util.Set;

public class SidePot {
	private Set<Integer> participants;
	private int value;

	public SidePot() {
		this.value = 0;
		this.participants = new HashSet<>();
	}

	public int getValue() {
		return value;
	}

	public Set<Integer> getParticipants() {
		return participants;
	}

	public void add(Integer player, int amount) {
		this.participants.add(player);
		this.value += amount;
	}

}
