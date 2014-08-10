package start;

import input_output.ScreenScraper;
import old.Situation;
import tools.Pos;

public class Start {
	public Pos pos(int x, int y) {
		return new Pos(x, y);
	}

	public static void main(String[] _) throws InterruptedException {

		// Excel.start();Excel.getPreflopStrategy();
//		PreflopStrategy strat = new PreflopStrategy();

//		long start = System.currentTimeMillis();// timestamp
		ScreenScraper scraper = new ScreenScraper();
		scraper.start();
		scraper.join();

//		System.exit(1);
//		Situation s = scraper.getSituation();
//		if (s.getHand() != null) {
//			s.print();
//			PreflopSelector selector = new PreflopSelector();
//
//			selector.setSituation(s.pSit);
//
//			selector.setBuket(s.getHand().getPreflopBuket());
//
//			selector.setPosition(s.itsMe.getPosition());
//
//			selector.print();
//			Decision d = strat.get(selector);
//			System.out.println("Decision: " + d);
//		}
//		long stop = System.currentTimeMillis();// timestamp
//		System.out.println((stop - start) + " Millisekunden.");
	}

	public static void start2(Situation s) {
		// funktioniert nur im Preflop und da nur in der ersten Raisrunde,
		// sprich gibt komische Positionen bei zweiter Raisrunde aus, macht aber
		// nichts

		// PreflopSelector sel = new PreflopSelector();
		// Position pos = Position.BB;

	}
}
