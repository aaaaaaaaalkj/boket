package managementcards.catrecnew;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Freq {
  ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4);

  @SuppressWarnings("null")
  public static final List<Freq> VALUES = Collections.unmodifiableList(Arrays
      .asList(values()));

  private int value;

  public int getValue() {
    return value;
  }

  Freq(final int k) {
    value = k;
  }
}
