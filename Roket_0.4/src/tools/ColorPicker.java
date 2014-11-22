package tools;

import input_output.MyRobot;
import input_output.Raw_Situation;
import input_output.ScreenScraper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import org.eclipse.jdt.annotation.Nullable;

import pot_odds_strategy.PotOddsDecision;
import pot_odds_strategy.PotOddsStrategy;

public class ColorPicker {

	public ColorPicker() {
		// TODO Auto-generated constructor stub
	}



	public void test2() throws AWTException {
		ScreenScraper scraper = new ScreenScraper();
		Raw_Situation raw = scraper.getSituation();
		raw.print();
		PotOddsStrategy strategy = new PotOddsStrategy(raw);
		System.out.println(strategy);
		PotOddsDecision d = strategy.decide();
		// ISituation sit = new BoketSituation(raw);
		// System.out.println(sit);

		System.out.println(d);

	}

	public static void main(String[] args) throws Exception {
		new ColorPicker().test2();

		// Color c = new Color(12010269);
		// logo = recognizeLogo();
		//
		// Pos p = pixelSearch(logo.x, logo.y, 1000, 550, c);
		// System.out.println(p);
		// MyRobot.mouseMove(p.plus(logo));

	}

	public void findColorAtMousePosition() {
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

	private @Nullable Pos recognizeLogo(MyRobot robot) {
		// Farbe im Logo von Pokerstars
		Color c = new Color(0x00FFCAC5);
		Pos logo = robot.pixelSearch(0, 0, 100, 250, c);
		return logo;
	}

	public void test() {
		// Color cardColor = getPixelColor(new Pos().plus(9, 25));
	}

	public void start(MyRobot robot) {
		Pos logo = recognizeLogo(robot);
		// Color refColor = new Color(12010269);
		// System.out.println(refColor);

		if (logo == null) {
			System.out.println("logo is null");
		} else {

			Pos p = new Pos(40, 314).plus(logo);
			Color c = robot.getPixelColor(p);
			System.out.println(c);

			robot.mouseMove(p);
		}
		// 38,231
	}

	public static Pos pos(int x, int y) {
		return new Pos(x, y);
	}
}
