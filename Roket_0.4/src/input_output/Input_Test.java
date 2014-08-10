package input_output;

import static input_output.MyRobot.pixelSearch;

import java.awt.Color;

import managementCards.cards.Card;
import tools.Pos;

public class Input_Test {
	public static void test3() {
		Color ref = new Color(16762401);
		Pos logo = recognizeLogo();

		Pos p = new Pos(20 - 14, 20 - 14).plus(logo);
		Pos p2 = new Pos(550 - 14, 470 - 14).plus(logo);

		Pos res = MyRobot.pixelSearch(p, p2, ref);
		System.out.println(res);
	}

	public static void main(String[] args) {

		// Color ref = new Color((248 << 16) + (121 << 8) + 121);
		Pos logo = recognizeLogo();

		if (null == logo) {
			System.out.println("no logo found");
			return;
		}

		// MyRobot.mouseMove(logo);

//		Hand c = Card_Recognition.start(logo);
//		
//		System.out.println(c);

		 new ScreenScraper().run();

		// Pos logo = recognizeLogo();
		// int button = ScreenScraper.searchButton(logo);
		// System.out.println("button: " + button);

		// Color ref = new Color(12010269);
		// Pos p = new Pos(0, 0);
		// Pos p2 = new Pos(1000, 1000);
		// Pos pp = MyRobot.pixelSearch(p, p2, ref);
		// System.out.println(pp.minus(recognizeLogo()));
	}

	public static void test1() {
		Pos pos;
		Card c;

		Pos logo = recognizeLogo();

		pos = new Pos(24, 217).plus(logo); // pocket1

		c = Card_Recognition.recognizeCard(pos);
		System.out.println(c);

		pos = new Pos(39, 221).plus(logo); // pocket2
		c = Card_Recognition.recognizeCard(pos);
		System.out.println(c);

		pos = new Pos(262, 168).plus(logo); // flop 1
		c = Card_Recognition.recognizeCard(pos);
		System.out.println(c);
		pos = new Pos(316, 168).plus(logo); // flop 2
		c = Card_Recognition.recognizeCard(pos);
		System.out.println(c);
		pos = new Pos(370, 168).plus(logo); // flop 3
		c = Card_Recognition.recognizeCard(pos);
		System.out.println(c);

		pos = new Pos(424, 168).plus(logo); // turn
		c = Card_Recognition.recognizeCard(pos);
		System.out.println(c);
		pos = new Pos(478, 168).plus(logo); // river
		c = Card_Recognition.recognizeCard(pos);
		System.out.println(c);

		// 1. Flopkarte: 262, 168
		// 2. flopkarte: 316,168
		// 3. flopkarte: 370, 168
		// 4. flopkarte: 424, 168
		// 5. flopkarte: 478, 168

	}

	private static Pos recognizeLogo() {
		// Farbe im Logo von Pokerstars
		Color c = new Color(0x00FFCAC5);
		Pos logo = pixelSearch(0, 0, 100, 250, c);
		return logo;
	}

}