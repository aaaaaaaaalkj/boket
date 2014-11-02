package strategy.conditions.postflop;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum FlushDanger implements ICondition {
	NONE, // rainbow
	MODERATE, // flush-draw possible (2 cards of same suit)
	SIGNIFICANT, // flush possible (3 cards of same suit)
	HIGH, // four cards of the same suit in community-cards
	CERTAIN_FLUSH; // five of the same suit in community-cards

	public static final FlushDanger[] VALUES = values();

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getFlushDanger();
	}

	public static FlushDanger fromLong(long count_max_cards_of_same_suit) {
		return values()[Math.min((int) count_max_cards_of_same_suit - 1,
				VALUES.length - 1)];
	}

	public ICondition orHigher() {
		return (sit -> sit.getFlushDanger().ordinal() >= ordinal());
	}

	@Override
	public String toString() {
		if (this == NONE) {
			return "NO";
		} else {
			return super.toString();
		}
	}

}
