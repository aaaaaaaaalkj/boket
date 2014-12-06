package strategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TypeOfDecision {
  POST_SB(1, true), POST_BB(1, true), FOLD(1, true), CALL(1, true),
  RAISE_QUARTER_POT(.25, true), RAISE_HALF_POT(.5, true),
  RAISE_POT_SIZE(1, true), RAISE_DOUBLE_POT(2, true),
  RAISE_TENTH_STACK(.1, false), RAISE_FIFTH_STACK(.2, false),
  RAISE_HALF_STACK(.5, false), ALL_IN(1, true);

  private final boolean potPentred;
  private final double factor;

  private TypeOfDecision(final double factor, final boolean potCentred) {
    this.potPentred = potCentred;
    this.factor = factor;
  }

  public double getFactor() {
    return factor;
  }

  public boolean isPotCentred() {
    return potPentred;
  }

  @SuppressWarnings("null")
  private static final List<TypeOfDecision> VALUES =
      Collections.unmodifiableList(Arrays.asList(values()));

  public static TypeOfDecision random(final Random rnd) {
    return VALUES.get(rnd.nextInt(VALUES.size()));
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
