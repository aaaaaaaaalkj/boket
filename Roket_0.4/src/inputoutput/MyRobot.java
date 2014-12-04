package inputoutput;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

import org.eclipse.jdt.annotation.Nullable;

import tools.Pos;

public class MyRobot {
	private final BufferedImage capture;

	@SuppressWarnings("null")
	public MyRobot() throws AWTException {
		long l = System.currentTimeMillis();
		this.capture = new Robot().createScreenCapture(
				new Rectangle(0, 0, 1000, 800)
				);
		l = System.currentTimeMillis() - l;
		// System.out.println("screen-cpature took " + l + " millis");
	}

  public final BufferedImage getScreenshot() {
		return capture;
	}

  public final Color getPixelColor(final Pos p) {
		return new Color(capture.getRGB(p.x, p.y));
	}

  public final void mouseMove(final Pos p) {
		try {
			Robot rb = new Robot();
			rb.mouseMove(p.x, p.y);
			rb.mousePress(InputEvent.BUTTON1_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	  /**
   * pixelSearch search the Color c in the recktangle x,y,w,h.
   * 
   * @return coords of the color c
   */
  @Nullable
  public final Pos pixelSearch(final Pos p, final Pos p2, final Color c) {
		return pixelSearch(p.x, p.y, (p2.x - p.x), (p2.y - p.y), c);
	}

  @Nullable
  public final Pos pixelSearch(final int x, final int y, final int w, final int h,
      final Color c) {
		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		int[] rgbs = new int[w * h];
		int[] a = capture.getRGB(x, y, w, h, rgbs, 0, w);
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

  public final int maxColor(final Pos pos, final Pos delta) {
		int w = delta.x;
		int h = delta.y;
		int[] rgbs = new int[w * h];
		int[] a = capture.getRGB(pos.x, pos.y, w, h, rgbs, 0, w);

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

  public final int pixelCheckSum(final Pos pos, final Pos pos2) {
		int w = pos2.minus(pos).x;
		int h = pos2.minus(pos).y;
		int[] rgbs = new int[w * h];
		int[] a = capture.getRGB(pos.x, pos.y, w, h, rgbs, 0, w);
		int res = 0;
		for (int i = 0; i < a.length; i++) {
			res += i * a[i];
		}
		return res;
	}

}
