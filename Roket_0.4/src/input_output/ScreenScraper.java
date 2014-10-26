package input_output;

import static input_output.MyRobot.getPixelColor;
import static input_output.MyRobot.pixelCheckSum;
import static input_output.MyRobot.pixelSearch;

import java.awt.Color;

import old.Situation;
import tools.Pos;

public class ScreenScraper extends Thread {
	public Pos logo;
	Raw_Situation situation;

	public ScreenScraper() {
		situation = new Raw_Situation();
	}

	public void run() {
		if (!recognizeLogo())
			return;

		recognizeBrownButtons(situation);
		searchButton(situation);
		tableCheckSum(situation);
		activePlayers(situation);
		postRecognition(situation);

		situation.hand = Card_Recognition.recognizeHand(logo);
		situation.communityCards = Card_Recognition
				.recognizeCommunityCards(logo);
	}

	private static Pos pos(int x, int y) {
		return new Pos(x, y);
	}

	private void activePlayers(Raw_Situation situation) {

		// 10-player-table
		Pos[] positions = { pos(105, 207), pos(176, 132), pos(238, 108),
				pos(540, 108), pos(603, 133), pos(675, 208), pos(589, 289),
				pos(427, 351), pos(355, 351), pos(187, 284) };

		// 9-player-table
		positions = new Pos[] { pos(95, 215), pos(169, 124), pos(231, 98),
				pos(528, 98), pos(590, 126), pos(664, 216), pos(580, 289),
				pos(412, 324), pos(165, 288) };

		situation.activeStatus = new boolean[Situation.numSeats];

		for (int i = 0; i < Situation.numSeats; i++) {
			int dif = MyRobot.maxColor(positions[i].plus(logo), pos(25, 30));
			if (dif == 146)
				situation.activeStatus[i] = true;
		}
		situation.activeStatus[0] = situation.itsMyTurn;
	}

	private void tableCheckSum(Raw_Situation situation) {
		situation.checkSum = pixelCheckSum(logo.plus(new Pos(15, -5)),
				logo.plus(new Pos(50, 5)));
		// lobby = 1439337809
	}

	private boolean recognizeLogo() {
		// Farbe im Logo von Pokerstars
		Color c = new Color(0x00FFCAC5);
		logo = pixelSearch(0, 0, 100, 250, c);
		if (logo == null)
			return false;
		// TODO: checken sind die zwei Pixel für win7 ?
		// logo = logo.plus(0, 2);
		return true;
	}

	void searchButton(Raw_Situation situation) {
		Pos[] centersOfPlayerCirles = Constants.centersOfPlayerCirles;
		Color c = new Color(12010269);
		Pos button = pixelSearch(logo.x, logo.y, 1000, 550, c);
		if (null == button) {
			System.out.println("cant find button");
			return;
		}
		int minIndex = -1;
		double minDist = Double.MAX_VALUE;

		for (int i = 0; i < centersOfPlayerCirles.length; i++) {
			Pos p = centersOfPlayerCirles[i];
			double d = p.dist(button);
			if (d < minDist) {
				minDist = d;
				minIndex = i;
			}
		}
		situation.button = minIndex;
	}

	private void postRecognition(Raw_Situation situation) {
		// für OCR werden Rechtecke gebraucht, wo nach Ziffern gesucht wird.
		// for 10-seats-table:
		// Pos[] positions = { pos(116, 197), pos(141, 156), pos(218, 114),
		// pos(390, 117), pos(486, 157), pos(491, 194), pos(480, 260),
		// pos(413, 307), pos(247, 309), pos(159, 261) };
		Pos[] positions = { pos(0, 0), pos(169, 176), pos(218, 114),
				pos(390, 117), pos(486, 157), pos(491, 194), pos(480, 260),
				pos(413, 307), pos(247, 309), pos(159, 261) };

		positions = new Pos[] { pos(139 - 14, 248 - 14),
				pos(169 - 14, 176 - 14), pos(259 - 14, 147 - 14),
				pos(463, 100), pos(417, 136), pos(538 - 14, 241 - 14),
				pos(455 - 14, 304 - 14), pos(313 - 14, 300 - 14),
				pos(219 - 14, 300 - 14) };
		situation.pot = 0;
		situation.posts = new double[Situation.numSeats];
		for (int i = 0; i < Situation.numSeats; i++) {
			situation.posts[i] = ocr(logo, positions[i]);
			situation.pot += situation.posts[i];
		}
		situation.pot += ocr(logo, (pos(308 - 14, 260 - 14)));
	}

	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private double ocr(Pos logo, Pos pos) {
		final boolean REAL_MONEY = true;

		if (REAL_MONEY)
			return ocr_real_money(logo, pos);
		else
			return ocr_play_money(logo, pos);

	}

	private double ocr_real_money(Pos logo, Pos pos) {
		Color ref = new Color(16774863);

		Pos dollar = pixelSearch(logo.plus(pos), logo.plus(pos).plus(160, 60),
				ref);

		String letter;

		if (dollar != null) {
			String text = "";
			int position = 4;
			do {
				letter = recognizeLetter(
						logo.plus(pos).plus(dollar.plus(position, 0)), ref);

				if (letter.equals("."))
					position += 3;
				else
					position += 6;
				text += letter;
			} while (isNumber(letter) || ".".equals(letter));
			try {
				return Double.parseDouble(text);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}

	private Integer recognizeFirstLetter(Pos logo, Pos pos, Pos first, Color ref) {
		Pos x;
		Pos start = logo.plus(pos).plus(first);

		for (int i = 0; i > -4; i--) {
			start = start.minus(1, 0);
			x = pixelSearch(start, start.plus(1, 10), ref);
			if (x == null)
				return i;
		}
		return null;
	}

	private double ocr_play_money(Pos logo, Pos pos) {
		// r=255,g=246,b=207
		Color ref = new Color(16774863);

		Pos first = pixelSearch(logo.plus(pos), logo.plus(pos).plus(160, 60),
				ref);
		String letter;

		if (first != null) {
			Integer x = recognizeFirstLetter(logo, pos, first, ref);
			if (x == null) {
				System.out.println("can not recognize first letter");
				return 0.;
			}
			Pos start_delta = pos(x, -1);

			String text = "";
			do {
				letter = recognizeLetter(
						logo.plus(pos).plus(first.plus(start_delta)), ref);

				if (letter.equals(".")) {
					// do not add "." to text
					// its not a ".", its a ","
					// its play money -> big numbers
					start_delta = start_delta.plus(3, 0);
				} else {
					text += letter;
					start_delta = start_delta.plus(6, 0);
				}

			} while (isNumber(letter) || ".".equals(letter));
			try {
				return Double.parseDouble(text);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}

	private String recognizeLetter(Pos p, Color c) {

		Color c2 = getPixelColor(p.plus(0, 2));

		if (c.equals(c2)) {
			// 0,2,3,6,8,9
			c2 = getPixelColor(p.plus(0, 3));
			if (c.equals(c2)) {
				// 0,6,8,9
				c2 = getPixelColor(p.plus(2, 4));
				if (c.equals(c2)) {
					// 6,8
					c2 = getPixelColor(p.plus(0, 4));
					if (c.equals(c2))
						return "6";
					else
						return "8";
				} else {
					// 0,9
					c2 = getPixelColor(p.plus(0, 5));
					if (c.equals(c2))
						return "0";
					else
						return "9";
				}
			} else {
				// 2,3
				c2 = getPixelColor(p.plus(2, 4));
				if (c.equals(c2))
					return "3";
				else
					return "2";
			}
		} else {
			// 1,4,5,7,.
			c2 = getPixelColor(p.plus(0, 3));
			if (c.equals(c2)) {
				// 1,5
				c2 = getPixelColor(p.plus(0, 5));
				if (c.equals(c2))
					return "5";
				else
					return "1";
			} else {
				// 4,7,.
				c2 = getPixelColor(p.plus(0, 5));
				if (c.equals(c2))
					return "4";
				else {
					c2 = getPixelColor(p.plus(2, 4));
					if (c.equals(c2))
						return "7";
					else {
						// .," "
						c2 = getPixelColor(p.plus(0, 7));
						if (c.equals(c2))
							return ".";
						else
							return "";
					}
				}
			}
		}
	}

	private void recognizeBrownButtons(Raw_Situation situation) {
		Pos brownButton1 = new Pos(550, 525).plus(logo);
		Color brown1 = getPixelColor(brownButton1);

		int difference = Math.abs(brown1.getRed() - 148)
				+ Math.abs(brown1.getGreen() - 66)
				+ Math.abs(brown1.getBlue() - 15);

		// boolean b = brown1.equals(new Color(147, 65, 15));
		// b |= brown1.equals(new Color(137, 57, 11));

		if (difference < 30) {
			situation.itsMyTurn = true;
			// situation.emptySeat[0] = false;
			situation.activeStatus[0] = true;
			// System.out.println("brown buttons recognized");
		} else {
			// System.out.println("brown buttons not recognized");
		}

	}

	public Raw_Situation getSituation() {
		return situation;
	}

}
