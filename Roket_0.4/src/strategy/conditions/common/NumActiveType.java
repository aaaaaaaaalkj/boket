package strategy.conditions.common;

import java.util.List;

import strategy.ISituation;
import strategy.conditions.ICondition;
import tools.Tools;

public enum NumActiveType implements ICondition {
  TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);
  @SuppressWarnings("null")
  public static final List<NumActiveType> VALUES = Tools.asList(values());

  private final int value;

  NumActiveType(final int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public static int getCount() {
    return VALUES.size();
  }

  @Override
  public boolean eval(final ISituation sit) {
    return this == sit.getNumActive();
  }

  public ICondition orMore() {
    return (sit -> sit.getNumActive().ordinal() >= ordinal());
  }

  public ICondition orLess() {
    return (sit -> sit.getNumActive().ordinal() <= ordinal());
  }

  private static final int MAX_COUNT_PLAYERS = 9;
  private static final int MIN_COUNT_PLAYERS = 2;

  public static NumActiveType fromInt(final int size) {
    if (size == 1) {
      // this can happen shortly aufter last fold
      throw new IllegalStateException(
          "illegal intermediate state. only one player at table");
    }
    assert size >= MIN_COUNT_PLAYERS && size <= MAX_COUNT_PLAYERS : "Too many or too few active players: "
        + size;

    return VALUES.stream()
        .filter(v -> v.getValue() == size)
        .findAny()
        .orElseThrow(
            () -> new IllegalStateException(
                "no NumActiveType found for " + size
                    + " players"));
  }

}
