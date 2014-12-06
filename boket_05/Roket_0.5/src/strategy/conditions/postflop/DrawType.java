package strategy.conditions.postflop;

import java.util.EnumSet;

public enum DrawType {
  NONE, GUTSHOT, OESD, DOUBLE_GUTSHOT, FLUSH_DRAW, MONSTER_DRAW;
  @SuppressWarnings("null")
  public static final EnumSet<DrawType> VALUES = EnumSet
      .allOf(DrawType.class);



}
