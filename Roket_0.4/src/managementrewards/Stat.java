package managementrewards;

import managementpaymentstmp.AmountOfJetons;

public final class Stat {
	private AmountOfJetons result;

	public Stat() {
		result = AmountOfJetons.ZERO;
	}

  public void pay(final AmountOfJetons amount) {
		result = result.minus(amount);
	}

  public void won(final AmountOfJetons amount) {
		result = result.minus(amount);
	}
}
