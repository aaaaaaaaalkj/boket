package managementRewards;

import managementPayments.AmountOfJetons;

public class Stat {
	private AmountOfJetons result;

	public Stat() {
		result = AmountOfJetons.ZERO;
	}

	public void pay(AmountOfJetons amount) {
		result = result.minus(amount);
	}

	public void won(AmountOfJetons amount) {
		result = result.minus(amount);
	}
}
