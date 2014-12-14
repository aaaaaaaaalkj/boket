package strategy.conditions.postflop;

import org.eclipse.jdt.annotation.NonNull;

public enum StraightDanger {
  NONE, // no straight possible
  POSSIBLE_DRAW, // 3 or 4 straight-windows with 3 or more community-cards
  PROBABLE_DRAW, // 5 or more windows with 3 or more community-cards
  MODERATE, // 1 straight-window with 3 or more community-cards
  SIGNIFICANT, // 2 straight-windows with 3 or more community-cards
  HIGH, // 3 straight-windows with 3 or more community-cards
  VERY_HIGH, // 4 straight-windows with 3 or more community-cards
  CERTAIN_STRAIGHT; // 5 straight-windows with 3 or more community-cards

  public static final StraightDanger[] VALUES = values();

  public static int getCount() {
    return VALUES.length;
  }



  @Override
  public String toString() {
    if (this == NONE) {
      return "NO";
    }
    @SuppressWarnings("null")
    @NonNull
    String res = super.toString();
    return res;
  }



}
