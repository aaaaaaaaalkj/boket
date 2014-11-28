package managementCards.cat_rec_new;

import org.eclipse.jdt.annotation.NonNull;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum Cathegory implements ICondition {
	HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;

	public static final Cathegory @NonNull [] VALUES = values();

	@SuppressWarnings("null")
	public static Cathegory getCathegory(int index) {
		return VALUES[index];
	}

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getCathegory();
	}

	public ICondition orBetter() {
		return (sit -> sit.getCathegory().ordinal() >= ordinal());
	}

	public void print() {
		System.out.println(this);
	}
}
