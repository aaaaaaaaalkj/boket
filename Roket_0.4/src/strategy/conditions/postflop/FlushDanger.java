package strategy.conditions.postflop;

import org.eclipse.jdt.annotation.NonNull;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum FlushDanger implements ICondition {
	NONE, // rainbow
	MODERATE, // flush-draw possible (2 cards of same suit)
	SIGNIFICANT, // flush possible (3 cards of same suit)
	HIGH, // four cards of the same suit in community-cards
	CERTAIN_FLUSH; // five of the same suit in community-cards

	public static final FlushDanger @NonNull [] VALUES = values();

	public static int getCount() {
		return VALUES.length;
	}

	@SuppressWarnings("null")
	public static FlushDanger get(int index) {
		return VALUES[index];
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getFlushDanger();
	}

	public static FlushDanger fromLong(long count_max_cards_of_same_suit) {
		int index = Math.min((int) count_max_cards_of_same_suit - 1,
				VALUES.length - 1);
		return get(index);
	}

	public ICondition orHigher() {
		return (sit -> sit.getFlushDanger().ordinal() >= ordinal());
	}

	public ICondition orLower() {
		return (sit -> sit.getFlushDanger().ordinal() <= ordinal());
	}

	@Override
	public String toString() {
		@SuppressWarnings("null")
		@NonNull
		String res = super.toString();
		if (this == NONE) {
			res = "NO";
		}
		return res;
	}
}
