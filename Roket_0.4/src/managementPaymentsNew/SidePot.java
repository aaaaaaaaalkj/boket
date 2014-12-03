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

  public final int getValue() {
		return value;
	}

  public final Set<Integer> getParticipants() {
		return participants;
	}

  public final void add(final Integer player, final int amount) {
		this.participants.add(player);
		this.value += amount;
	}

}
