package input_output;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import tools.Pos;
import tools.X;

public class MyRobot {
	private static Robot rb;

	static {
		try {
			rb = new Robot();
		} catch (AWTException e) {
			X.throwE(e);
		}
	}

	public static Color getPixelColor(Pos p) {
		return rb.getPixelColor(p.x, p.y);
	}

	public static void mouseMove(Pos p) {
		rb.mouseMove(p.x, p.y);
	}

	/**
	 * pixelSearch search the Color c in the recktangle x,y,w,h
	 * 
	 * @return coords of the color c
	 */
	public static Pos pixelSearch(Pos p, Pos p2, Color c) {
		return pixelSearch(p.x, p.y, (p2.x - p.x), (p2.y - p.y), c);
	}

	public static Pos pixelSearch(int x, int y, int w, int h, Color c) {
		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		Rectangle screenRect = new Rectangle(x, y, w, h);
		BufferedImage bimage = rb.createScreenCapture(screenRect);
		int[] rgbs = new int[w * h];
		int[] a = bimage.getRGB(0, 0, w, h, rgbs, 0, w);
		// Ab hier Wird das Array durchsucht
		for (int i = 0; i < a.length; i++) {
			if (new Color(a[i]).equals(c)) {
				int pointY = i / w;
				int pointX = (i) - (pointY * w);
				return new Pos(pointX, pointY);
			}
		}
		return null;
	}

	public static int maxColor(Pos pos, Pos delta) {
		int w = delta.x;
		int h = delta.y;
		int[] rgbs = new int[w * h];
		Rectangle screenRect = new Rectangle(pos.x, pos.y, w, h);
		BufferedImage bimage = rb.createScreenCapture(screenRect);
		int[] a = bimage.getRGB(0, 0, w, h, rgbs, 0, w);

		int b = (2 << 7) - 1;
		int g = (2 << 15) - 1 - b;
		int r = (2 << 23) - 1 - g - b;

		int maxDiff = -255;

		for (int i = 0; i < a.length; i++) {
			int red = (r & a[i]) >> 16;
			int green = (g & a[i]) >> 8;
			int blue = (b & a[i]);
			maxDiff = Math.max(maxDiff, red - Math.max(green, blue));
		}

		return maxDiff;
	}

	public static int pixelCheckSum(Pos pos, Pos pos2) {
		int w = pos2.minus(pos).x;
		int h = pos2.minus(pos).y;
		int[] rgbs = new int[w * h];
		Rectangle screenRect = new Rectangle(pos.x, pos.y, w, h);
		BufferedImage bimage = rb.createScreenCapture(screenRect);
		int[] a = bimage.getRGB(0, 0, w, h, rgbs, 0, w);
		int res = 0;
		for (int i = 0; i < a.length; i++) {
			res += i * a[i];
		}
		return res;
	}

}
