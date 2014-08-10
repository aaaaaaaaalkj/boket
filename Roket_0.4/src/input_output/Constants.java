package input_output;

import java.awt.Color;

import tools.Pos;

public class Constants {
	public static final Color cardEdge = new Color(123, 123, 123);
	public static final int radiusOfPlayerCircles = 40;
	public static final int cardOffset = 54;

	public static final Pos[] centersOfPlayerCirles = new Pos[] { pos(47, 245),
			pos(130, 116), pos(276, 57), pos(509, 57), pos(649, 116),
			pos(729, 245), pos(605, 361), pos(388, 388), pos(174, 360) };

	public static final Pos flop = pos(250, 150);

	private static Pos pos(int x, int y) {
		return new Pos(x, y);
	}
}
