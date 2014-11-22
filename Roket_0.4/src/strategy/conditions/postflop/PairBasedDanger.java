package strategy.conditions.postflop;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum PairBasedDanger implements ICondition {
	NONE, // no pair in community-cards
	MODERATE, // one pair in community-cards
	HIGH, // two pairs or three of a kind in community-cards
	CERTAIN_COMBO; // full-house of four-of-a-kind in communit-cards

	@SuppressWarnings("null")
	public static final List<PairBasedDanger> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));

	public static int getCount() {
		return VALUES.size();
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getPairBasedDanger();
	}

	public ICondition orHigher() {
		return (sit -> sit.getPairBasedDanger().ordinal() >= ordinal());
	}

	public static PairBasedDanger fromLong(long l) {
		return VALUES.get(Math.min((int) l, getCount() - 1));
	}

	public String toString() {
		@SuppressWarnings("null")
		@NonNull
		String res = super.toString();
		if (this == NONE) {
			return "NO";
		} else {
			return res;
		}
	}

	public ICondition orLower() {
		return (sit -> sit.getPairBasedDanger().ordinal() <= ordinal());
	}
}
