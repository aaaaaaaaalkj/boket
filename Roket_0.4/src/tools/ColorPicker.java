package tools;

import static managementCards.cards.Rank.Ace;
import static managementCards.cards.Rank.Five;
import static managementCards.cards.Rank.Four;
import static managementCards.cards.Rank.Three;
import static managementCards.cards.Rank.Two;
import input_output.MyRobot;
import input_output.Raw_Situation;
import input_output.ScreenScraper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.List;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.ResultImpl;

import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.LoggerFactory;

import pot_odds_strategy.PotOddsDecision;
import pot_odds_strategy.PotOddsStrategy;
import test_parkour.TestParkour;

public class ColorPicker {
	@SuppressWarnings("null")
	final static org.slf4j.Logger logger = LoggerFactory
			.getLogger(ColorPicker.class);

	public ColorPicker() {
		// TODO Auto-generated constructor stub
	}

	public void test2() throws AWTException {
		ScreenScraper scraper = new ScreenScraper();
		Raw_Situation raw = scraper.getSituation();
		raw.print();
		PotOddsStrategy strategy = new PotOddsStrategy(raw);
		logger.info("{}", strategy);
		PotOddsDecision d = strategy.decide();
		// ISituation sit = new BoketSituation(raw);
		// logger.info(sit);

		logger.info("{}", d);

	}

	static List<Card> all = Tools.asList(Card._3s, Card._7c, Card.Ac, Card.Ad,
			Card.Ah, Card.Kc, Card.Qc);
	static List<Rank> window = Tools.asList(Five, Four, Three, Two, Ace);
	static List<Rank> ranks = Tools.asList(Rank.Five, Rank.King, Rank.Three,
			Rank.Seven, Rank.Four,
			Rank.Two, Rank.Two);

	public static void test1() {
		ResultImpl res = new Cat_Rec(
				Card._3s, Card.Ad, Tools.asList(Card.Kc, Card.Qc, Card.Qd,
						Card.Kd, Card.Ah))
				.check();

	}

	private static void test1b() {
	}

	public static void test1c() {
	}

	public static void comparePerformance() {

		long sum1 = 0;
		long sum2 = 0;
		long sum3 = 0;
		long l;

		for (int i = 0; i < 50001; i++) {

			l = System.currentTimeMillis();
			test1();
			l = System.currentTimeMillis() - l;
			sum1 += l;

			l = System.currentTimeMillis();
			test1b();
			l = System.currentTimeMillis() - l;
			sum2 += l;

			l = System.currentTimeMillis();
			test1c();
			l = System.currentTimeMillis() - l;
			sum3 += l;
		}

		logger.info("{}\n{}\n{}", sum1, sum2, sum3);
	}

	private void generateCodeForCards() {
		for (Suit s : Suit.VALUES) {
			for (Rank r : Rank.VALUES) {
				String underScore = r.ordinal() < 8 ? "_" : "";
				String str = "public static final Card " + underScore
						+ r.shortString()
						+ s.shortString()
						+ " = new Card(Rank." + r + ", Suit." + s + ");";

				logger.info(str);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		logger.info("Entering application.");
		// new ColorPicker().test2();
		new TestParkour().test();

		// ScreenScraper scraper = new ScreenScraper();
		// Raw_Situation raw = scraper.getSituation();
		// raw.print();
		// new StrenghtAnalysis(raw);

		// ResultImpl res = new Cat_Rec(
		// Card._3s, Card.Ad, Tools.asList(Card.Kc, Card.Qc, Card.Qd,
		// Card.Kd, Card.Ah))
		// .check();

		// Color c = new Color(12010269);
		// logo = recognizeLogo();
		//
		// Pos p = pixelSearch(logo.x, logo.y, 1000, 550, c);
		// logger.info(p);
		// MyRobot.mouseMove(p.plus(logo));

	}

	public void findColorAtMousePosition() {
		try {
			Robot r = new Robot();
			Point p = MouseInfo.getPointerInfo().getLocation();
			// logo = recognizeLogo();
			// Pos p2 = new Pos(p.x, p.y);
			//
			// logger.info(p2.minus(logo));

			Color c = r.getPixelColor(p.x, p.y);

			logger.debug("{}", c);
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
		// logger.info(refColor);

		if (logo == null) {
			logger.warn("logo is null");
		} else {

			Pos p = new Pos(40, 314).plus(logo);
			Color c = robot.getPixelColor(p);
			logger.debug("[}", c);

			robot.mouseMove(p);
		}
		// 38,231
	}

	public static Pos pos(int x, int y) {
		return new Pos(x, y);
	}
}
