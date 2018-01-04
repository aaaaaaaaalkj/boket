package ranges.atry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public enum CountPlayers {
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10);

  @SuppressWarnings("null")
  public static final List<@NonNull CountPlayers> VALUES = Collections
      .unmodifiableList(Arrays.asList(values()));
  // The first value is for two players and is located at position 0.
  // Therefore we need to substract an offset of 2
  private static final int OFFSET = 2;
  private final int count;

  private CountPlayers(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public int getIndex() {
    return count - OFFSET;
  }

  public static CountPlayers fromIndex(int i) {
    return VALUES.get(i);
  }

  public static int size() {
    return VALUES.size();
  }

}
