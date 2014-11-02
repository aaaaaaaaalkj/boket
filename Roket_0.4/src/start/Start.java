package start;

import input_output.MyOutput;
import input_output.Raw_Situation;
import input_output.ScreenScraper;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import old.Situation;
import strategy.BoketSituation;
import strategy.ISituation;
import strategy.TypeOfDecision;
import tools.Pos;

public class Start {
	public Pos pos(int x, int y) {
		return new Pos(x, y);
	}

	private static int counter = 0;

	private static void saveImage(BufferedImage capture) {
		try {
			String type = "png";
			File outputfile = new File("screenshots\\" + (counter++) + "."
					+ type);
			ImageIO.write(capture, type, outputfile);
		} catch (IOException e) {
			e.printStackTrace();
			// ignore
		}
	}

	private static void handleSituation(ScreenScraper scraper,
			boolean myTurn) {
		Raw_Situation raw = scraper.getSituation();
		saveImage(scraper.getScreenshot());

		ISituation sit = new BoketSituation(raw);

		TypeOfDecision d = StrategyDefinitions.s.decide(sit);

		if (d == TypeOfDecision.FOLD || myTurn) {
			System.out.println();
			System.out.println(sit);
			// System.out.println(raw);
			System.out.println(d);
			decision2ouput(d, scraper.getLogo(), raw);
		} else {
			System.out.println("wait for my turn (" + d + ")");
		}
	}

	public static void main(String[] _) throws InterruptedException,
			AWTException {
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
				System.out.print(".");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}

	}

	public static void start2(Situation s) {
		// funktioniert nur im Preflop und da nur in der ersten Raisrunde,
		// sprich gibt komische Positionen bei zweiter Raisrunde aus, macht aber
		// nichts

		// PreflopSelector sel = new PreflopSelector();
		// Position pos = Position.BB;

	}

	public static void decision2ouput(TypeOfDecision d, Pos logo,
			Raw_Situation raw) {
		MyOutput out = new MyOutput();

		double pot = raw.getPot();
		double stack = 2;

		switch (d) {
		case ALL_IN:
			break;
		case CALL:
			out.clickCallButton(logo);
			break;
		case FOLD:
			out.clickFoldButton(logo);
			break;
		case RAISE_DOUBLE_POT:
			out.type2(pot * 2, logo);
			out.clickRaiseButton(logo);
			break;
		case RAISE_FIFTH_STACK:
			out.type2(stack / 5, logo);
			out.clickRaiseButton(logo);
			break;
		case RAISE_HALF_POT:
			out.type2(pot / 2, logo);
			out.clickRaiseButton(logo);
			break;
		case RAISE_HALF_STACK:
			out.type2(stack / 2, logo);
			out.clickRaiseButton(logo);
			break;
		case RAISE_POT_SIZE:
			out.type2(pot, logo);
			out.clickRaiseButton(logo);
			break;
		case RAISE_QUARTER_POT:
			out.type2(pot / 4, logo);
			out.clickRaiseButton(logo);
			break;
		case RAISE_TENTH_STACK:
			out.type2(stack / 10, logo);
			out.clickRaiseButton(logo);
			break;
		default:
			throw new IllegalStateException("unexpected decision: " + d);
		}

	}

}
