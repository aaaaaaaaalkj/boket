package tools;

import inputoutput.Pattern;

import java.awt.Color;

public final class InputTool {

  private InputTool() {
  }

  public static int toRGB(final Color c) {
    return c.getRGB() & 0x00ffffff;
  }

  public static Pattern pat(final int... x) {
    return new Pattern(x);
  }

  public static Pos pos(final int x, final int y) {
    return new Pos(x, y);
  }

}
