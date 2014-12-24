package tools;

import io.screen.Pattern;

import java.awt.Color;

public final class InputTool {

	private InputTool() {
	}

	private static final int MAX_RGB = 0x00ffffff;

	public static int toRGB(final Color c) {
		return c.getRGB() & MAX_RGB;
	}

	public static Pattern pat(final int... x) {
		return new Pattern(x);
	}

	public static Pos pos(final int x, final int y) {
		return new Pos(x, y);
	}

}
