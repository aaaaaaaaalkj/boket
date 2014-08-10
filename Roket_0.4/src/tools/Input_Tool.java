package tools;


import java.awt.Color;

public class Input_Tool {

	public static int toRGB(Color c) {
		return c.getRGB() & 0x00ffffff;
	}
	public static Pattern pat(int... x) {
		return new Pattern(x);
	}
	
	public static Pos pos(int x, int y) {
		return new Pos(x, y);
	}

}
