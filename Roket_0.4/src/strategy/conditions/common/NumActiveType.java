package strategy.conditions.common;

import java.util.Arrays;

import org.eclipse.jdt.annotation.NonNull;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum NumActiveType implements ICondition {
	TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);
	@SuppressWarnings("null")
	public static final @NonNull NumActiveType @NonNull [] VALUES = values();

	private final int value;

	NumActiveType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getNumActive();
	}

	public ICondition orMore() {
		return (sit -> sit.getNumActive().ordinal() >= ordinal());
	}

	public ICondition orLess() {
		return (sit -> sit.getNumActive().ordinal() <= ordinal());
	}

	public static NumActiveType fromInt(int size) {
		if (size == 1) {
			// this can happen shortly aufter last fold
			throw new IllegalStateException(
					"illegal intermediate state. only one player at table");
		}
		assert size >= 2 && size <= 9 : "Too many or too few active players: "
				+ size;

		return Arrays
				.stream(VALUES)
				.filter(v -> v.getValue() == size)
				.findAny()
				.orElseThrow(
						() -> new IllegalStateException(
								"no NumActiveType found for " + size
										+ " players"));
	}

}
