package common;

import managementPayments.AmountOfJetons;

public class Stack {
	private AmountOfJetons value;

	private Stack(AmountOfJetons amount) {
		this.value = amount;
	}

	public static Stack create(int bb) {
		return new Stack(AmountOfJetons.BB(bb));
	}

	public static Stack create(AmountOfJetons amount) {
		return new Stack(amount);
	}

	public AmountOfJetons get() {
		return value;
	}

	public void removeFromStack(AmountOfJetons amount) {
		value = value.minus(amount);
	}

	public void addToStack(AmountOfJetons amount) {
		value = value.plus(amount);
	}

	public boolean isEmpty() {
		return value.isZero();
	}

}
