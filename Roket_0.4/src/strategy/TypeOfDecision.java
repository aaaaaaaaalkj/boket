package strategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TypeOfDecision {
	POST_SB, POST_BB, FOLD, CALL, RAISE_QUARTER_POT, RAISE_HALF_POT, RAISE_POT_SIZE,
	RAISE_DOUBLE_POT, RAISE_TENTH_STACK, RAISE_FIFTH_STACK, RAISE_HALF_STACK, ALL_IN;

	@SuppressWarnings("null")
	private static final List<TypeOfDecision> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static TypeOfDecision random() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	// min_raise -> um überhaupt zu raisen
	// avg_raise -> (halber pot, falls im pot ordentlich was liegt )
	// high_raise -> (potsize bet/raise)
	// all_in

	// ----------------------------------------------- 1 2 --- 3 ---- 4
	// Flop, 50 bb im pot, 30 bb im stack, 10 toCall 12 25 30 30
	// Preflop, 2 bb im pot, 100 im stack, 1 toCall 1 6 12 100
	// Preflop, 20 bb im pot, 80 im stack, 2 toCall 2 10 20 80
	// Preflop, 20 bb im pot, 10 im stack, 2 toCall 2 10 10 10

}
