package start;

import input_output.MyOutput;
import input_output.Raw_Situation;
import input_output.ScreenScraper;
import old.Situation;
import strategy.BoketSituation;
import strategy.ISituation;
import strategy.IStrategy;
import strategy.TypeOfDecision;
import tools.Pos;

public class Start {
	public Pos pos(int x, int y) {
		return new Pos(x, y);
	}

	public static void main(String[] _) throws InterruptedException {
		while (true) {
			ScreenScraper scraper = new ScreenScraper();

			Raw_Situation raw = scraper.getSituation();

			if (raw.isItsMyTurn() && raw.getHand() != null) {
				ISituation sit = new BoketSituation(raw);
				System.out.println(sit);
				IStrategy shitStrategy = StrategyDefinitions.s;

				// System.out.println("situation:");
				// System.out.println(raw);

				TypeOfDecision d = shitStrategy.decide(sit);

				System.out.println(d);
				decision2ouput(d, scraper.logo, raw);
			} else {
				System.out.println("not my turn");
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
