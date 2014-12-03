package common;

import managementPayments.AmountOfJetons;

public final class Stack {
	private AmountOfJetons value;

  private Stack(final AmountOfJetons amount) {
		this.value = amount;
	}

  public static Stack create(final int bb) {
		return new Stack(AmountOfJetons.BB(bb));
	}

  public static Stack create(final AmountOfJetons amount) {
		return new Stack(amount);
	}

	public AmountOfJetons get() {
		return value;
	}

  public void removeFromStack(final AmountOfJetons amount) {
		value = value.minus(amount);
	}

  public void addToStack(final AmountOfJetons amount) {
		value = value.plus(amount);
	}

	public boolean isEmpty() {
		return value.isZero();
	}

}
