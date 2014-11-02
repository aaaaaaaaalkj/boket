package tools;

import input_output.MyRobot;
import input_output.Raw_Situation;
import input_output.ScreenScraper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import start.StrategyDefinitions;
import strategy.BoketSituation;
import strategy.ISituation;
import strategy.TypeOfDecision;

public class ColorPicker {
	private static Pos logo;

	public static void main(String[] args) throws Exception {
		// findColorAtMousePosition();

		ScreenScraper scraper = new ScreenScraper();
		Raw_Situation raw = scraper.getSituation();
		// raw.print();

		ISituation sit = new BoketSituation(raw);
		System.out.println(sit);

		TypeOfDecision d = StrategyDefinitions.s.decide(sit);

		System.out.println(d);

		// start(new MyRobot());
		System.exit(1);
		while (true) {

			// Point p = MouseInfo.getPointerInfo().getLocation();

			// Pos p2 = pos(p.x, p.y);
			// =255,g=246,b=207]
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

	private static Pos recognizeLogo(MyRobot robot) {
		// Farbe im Logo von Pokerstars
		Color c = new Color(0x00FFCAC5);
		logo = robot.pixelSearch(0, 0, 100, 250, c);
		return logo;
	}

	public static void test() {
		// Color cardColor = getPixelColor(new Pos().plus(9, 25));
	}

	public static void start(MyRobot robot) {
		recognizeLogo(robot);
		// Color refColor = new Color(12010269);
		// System.out.println(refColor);

		Pos p = new Pos(40, 314).plus(logo);
		Color c = robot.getPixelColor(p);
		System.out.println(c);

		robot.mouseMove(p);
		// 38,231
	}

	public static Pos pos(int x, int y) {
		return new Pos(x, y);
	}
}
