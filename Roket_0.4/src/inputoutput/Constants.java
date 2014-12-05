package inputoutput;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import tools.Pos;

public final class Constants {
  private Constants() {
  }

  public static final Color CARD_EDGE = new Color(123, 123, 123);
  public static final int RADIUS_OF_PLAYER_CIRCLES = 40;
  public static final int CARD_OFFSET = 54;

  public static final List<@NonNull Pos> CENTERS_OF_PLAYER_CIRLES = new ArrayList<>();
  static {
    CENTERS_OF_PLAYER_CIRLES.add(pos(47, 245));
    CENTERS_OF_PLAYER_CIRLES.add(pos(130, 116));
    CENTERS_OF_PLAYER_CIRLES.add(pos(276, 57));
    CENTERS_OF_PLAYER_CIRLES.add(pos(509, 57));
    CENTERS_OF_PLAYER_CIRLES.add(pos(649, 116));
    CENTERS_OF_PLAYER_CIRLES.add(pos(729, 245));
    CENTERS_OF_PLAYER_CIRLES.add(pos(605, 361));
    CENTERS_OF_PLAYER_CIRLES.add(pos(388, 388));
    CENTERS_OF_PLAYER_CIRLES.add(pos(174, 360));
  }

  public static final Pos FLOP = pos(250, 150);

  private static Pos pos(final int x, final int y) {
    return new Pos(x, y);
  }
}
