package tools;

import static input_output.MyRobot.pixelSearch;
import input_output.MyRobot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

public class ColorPicker {
	private static Pos logo;

	public static void main(String[] args) throws Exception {
		// findColorAtMousePosition();

		while (true) {

//			Point p = MouseInfo.getPointerInfo().getLocation();

//			Pos p2 = pos(p.x, p.y);
			//=255,g=246,b=207]
			findColorAtMousePosition();
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// ignore
			}
		}

		// Color c = new Color(12010269);
		// logo = recognizeLogo();
		//
		// Pos p = pixelSearch(logo.x, logo.y, 1000, 550, c);
		// System.out.println(p);
		// MyRobot.mouseMove(p.plus(logo));

	}

	public static void findColorAtMousePosition() {
		try {
			Robot r = new Robot();
			Point p = MouseInfo.getPointerInfo().getLocation();
			// logo = recognizeLogo();
			// Pos p2 = new Pos(p.x, p.y);
			//
			// System.out.println(p2.minus(logo));

			Color c = r.getPixelColor(p.x, p.y);

			System.out.println(c);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Pos recognizeLogo() {
		// Farbe im Logo von Pokerstars
		Color c = new Color(0x00FFCAC5);
		logo = pixelSearch(0, 0, 100, 250, c);
		return logo;
	}

	public static void test() {
		// Color cardColor = getPixelColor(new Pos().plus(9, 25));
	}

	public static void start() {
		recognizeLogo();
		Color refColor = new Color(12010269);
		System.out.println(refColor);

		Pos p = new Pos(656, 172).plus(logo);
		Color c = MyRobot.getPixelColor(p);
		System.out.println(c);

		MyRobot.mouseMove(p);
		// 38,231
	}

	public static Pos pos(int x, int y) {
		return new Pos(x, y);
	}
}
