package start;

import input_output.MyOutput;
import input_output.Raw_Situation;
import input_output.ScreenScraper;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import managementPaymentsNew.decisions.DecisionType;
import pot_odds_strategy.PotOddsDecision;
import pot_odds_strategy.PotOddsStrategy;
import tools.Pos;

public class PotOddsStart {
	public Pos pos(int x, int y) {
		return new Pos(x, y);
	}

	private static int counter = 0;

	private static String fillZeros(int counter) {
		String res = String.valueOf(counter);
		while (res.length() < 3) {
			res = "0" + res;
		}
		return res;
	}

	private static void saveImage(BufferedImage capture) {
		try {
			String type = "png";
			File outputfile = new File("screenshots\\" + fillZeros(counter++)
					+ "."
					+ type);
			ImageIO.write(capture, type, outputfile);
		} catch (IOException e) {
			e.printStackTrace();
			// ignore
		}
	}

	public static void main(String[] _) throws InterruptedException,
			AWTException {
		System.out.println("main start");
		while (true) {
			ScreenScraper scraper = new ScreenScraper();

			Raw_Situation raw = scraper.getSituation();

			if (raw.brownButtons[0] && !raw.brownButtons[1]
					&& !raw.brownButtons[2]) {
				// fast fold possible
				handleSituation(scraper, false);
			}

			if (raw.isItsMyTurn() && raw.getHand() != null) {
				handleSituation(scraper, true);
			} else {
				System.out.println(raw);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private static void handleSituation(ScreenScraper scraper,
			boolean myTurn) {
		Raw_Situation raw = scraper.getSituation();
		saveImage(scraper.getScreenshot());

		PotOddsStrategy strategy = new PotOddsStrategy(raw);
		PotOddsDecision d = strategy.decide();

		if (d.getDec() == DecisionType.FOLD || myTurn) {

			System.out.println("---------------------");
			System.out.println(strategy);
			System.out.println(raw);
			System.out.println(d);

			Pos logo = scraper.getLogo();
			if (null == logo) {
				// do nothing
			} else {
				decision2ouput(d, logo, raw);
			}
		} else {
			System.out.println("wait for my turn (" + d + ")");
		}
	}

	public static void decision2ouput(PotOddsDecision d, Pos logo,
			Raw_Situation raw) {
		MyOutput out = new MyOutput();
		System.out.println("try click");
		switch (d.getDec()) {
		case ALL_IN: // should not happen
		case CALL:
			out.clickCallButton(logo);
			break;
		case FOLD:
			out.clickFoldButton(logo);
			break;
		case RAISE:
			out.type2(d.getValue(), logo);
			out.clickRaiseButton(logo);
			break;
		default:
			throw new IllegalStateException("unexpected decision: " + d);
		}

	}

}
