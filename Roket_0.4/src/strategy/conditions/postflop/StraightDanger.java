package strategy.conditions.postflop;

import org.eclipse.jdt.annotation.NonNull;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum StraightDanger implements ICondition {
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
  public boolean eval(final ISituation sit) {
    return this == sit.getStraightDanger();
  }

  public ICondition orHigher() {
    return (sit -> sit.getStraightDanger().ordinal() >= ordinal());
  }

  public ICondition orLower() {
    return (sit -> sit.getStraightDanger().ordinal() <= ordinal());
  }

  @Override
  public String toString() {
    if (this == NONE) {
      return "NO";
    } else {
      @SuppressWarnings("null")
      @NonNull
      String res = super.toString();
      return res;
    }
  }

  public static ICondition isHigh() {
    return sit -> sit.getStraightDanger() == HIGH
        || sit.getStraightDanger() == VERY_HIGH
        || sit.getStraightDanger() == CERTAIN_STRAIGHT;
  }

}
