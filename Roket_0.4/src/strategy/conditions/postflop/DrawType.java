package strategy.conditions.postflop;

import java.util.EnumSet;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum DrawType implements ICondition {
  NONE, GUTSHOT, OESD, DOUBLE_GUTSHOT, FLUSH_DRAW, MONSTER_DRAW;
  @SuppressWarnings("null")
  public static final EnumSet<DrawType> VALUES = EnumSet
      .allOf(DrawType.class);

  @Override
  public boolean eval(final ISituation sit) {
    return this == sit.getDraw();
  }

}
