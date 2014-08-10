package start;

import static strategy.TypeOfDecision.ALL_IN;
import static strategy.TypeOfDecision.CALL;
import static strategy.TypeOfDecision.FOLD;
import static strategy.TypeOfDecision.RAISE_DOUBLE_POT;
import static strategy.TypeOfDecision.RAISE_HALF_POT;
import static strategy.TypeOfDecision.RAISE_POT_SIZE;
import static strategy.TypeOfDecision.RAISE_QUARTER_POT;
import static strategy.conditions.postflop.ComboType.FLUSH;
import static strategy.conditions.postflop.ComboType.FOUR_OF_A_KIND;
import static strategy.conditions.postflop.ComboType.FULL_HOUSE;
import static strategy.conditions.postflop.ComboType.GOOD_SET;
import static strategy.conditions.postflop.ComboType.STRAIGHT;
import static strategy.conditions.postflop.ComboType.STRAIGHT_FLUSH;
import static strategy.conditions.postflop.ComboType.TWO_PAIR;
import static strategy.conditions.postflop.DrawType.DOUBLE_GUTSHOT;
import static strategy.conditions.postflop.DrawType.FLUSH_DRAW;
import static strategy.conditions.postflop.DrawType.MONSTER_DRAW;
import static strategy.conditions.postflop.DrawType.OESD;
import static strategy.conditions.preflop.ConnectorType.CONNECTOR;
import static strategy.conditions.preflop.ConnectorType.POCKET_PAIR;
import static strategy.conditions.preflop.SuitedType.SUITED;
import strategy.IStrategy;
import strategy.Strategy2;
import strategy.conditions.common.ContributionType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;

public class Test {
	public static IStrategy s = (sit -> FOLD);
	public final static IStrategy shitStrategy = new Strategy2();

	static {
		s = s.preflop(
				SUITED.or(CONNECTOR).or(POCKET_PAIR)
				, CALL);
		// nutStrategy
		// .preFlop(
		// SUITED.or(CONNECTOR)
		// .or(POCKET_PAIR)
		// .and(TO_PAY_LESS_OR_EQ_TO(3)
		// .and(POT_LESS_OR_EQ_TO(18)
		// .and(NUM_ACTIVE_PLAYERS(5)
		// .or(CONTRIBUTION_LESS_THAN(0.125))))),
		// CALL);

		s = s.flop(MONSTER_DRAW // 15 outs
				.and(PairBasedDanger.LOW) // no pair in flop
				.and(ContributionType.HIGH.orLower()), CALL);

		s = s.flop(OESD.or(DOUBLE_GUTSHOT).and(FlushDanger.LOW) // rainbow
				.and(PairBasedDanger.LOW) // no pair in flop
				.and(ContributionType.MIDDLE.orLower()), CALL); // pay more
																// here?

		// strategy.flop(OESD.or(DOUBLE_GUTSHOT) // dangerous
		// .and(CONTRIBUTION_LESS_THAN(0.1)), CALL); // cheap chance

		s = s.flop(FLUSH_DRAW // 9 outs
				.and(PairBasedDanger.LOW) // no pair in flop
				.and(ContributionType.MIDDLE.orLower()), CALL);

		// nutStrategy.flop( // besser formulieren und ausarbeiten. Sonst ist
		// das zu gefährlich
		// TWO_PAIR // 4 outs
		// .and(NO_FLUSH_DANGER).and(NO_STRAIGHT_DANGER)
		// .and(CONTRIBUTION_LESS_THAN(0.1)), RAISE(15));

		s = s.flop(TWO_PAIR // 4 outs
				.and(ContributionType.LOW), CALL);

		s = s.flop(GOOD_SET)
				.if_(StraightDanger.HIGH.or(FlushDanger.HIGH))
				.then(FOLD)
				.if_(ContributionType.LOW).then(RAISE_QUARTER_POT)
				.if_(ContributionType.HIGH.orLower()).then(RAISE_DOUBLE_POT)
				.build();

		s = s.flop(STRAIGHT, RAISE_HALF_POT);
		s = s.flop(FLUSH, RAISE_POT_SIZE);
		s = s.flop(FULL_HOUSE, RAISE_POT_SIZE);
		s = s.flop(FOUR_OF_A_KIND, RAISE_POT_SIZE);
		s = s.flop(STRAIGHT_FLUSH, RAISE_POT_SIZE);

		s = s.turn(STRAIGHT, RAISE_DOUBLE_POT);
		s = s.turn(FLUSH, RAISE_DOUBLE_POT);
		s = s.turn(FULL_HOUSE, RAISE_DOUBLE_POT);
		s = s.turn(FOUR_OF_A_KIND, RAISE_DOUBLE_POT);
		s = s.turn(STRAIGHT_FLUSH, RAISE_DOUBLE_POT);

		s = s.turn(MONSTER_DRAW // 15 outs
				.and(PairBasedDanger.LOW) // no pair in flop
				.and(ContributionType.MIDDLE), CALL);

		s = s.turn(OESD.or(DOUBLE_GUTSHOT).and(FlushDanger.LOW) // rainbow
				.and(PairBasedDanger.LOW) // no pair in flop
				.and(ContributionType.MIDDLE), CALL); // pay more here?

		s = s.turn(FLUSH_DRAW // 9 outs
				.and(PairBasedDanger.LOW) // no pair in flop
				.and(ContributionType.MIDDLE), CALL);

		s = s.turn(TWO_PAIR // 4 outs
				.and(ContributionType.LOW), CALL);

		s = s.turn(GOOD_SET)
				.if_(StraightDanger.HIGH.or(FlushDanger.HIGH)).then(FOLD)
				.if_(ContributionType.LOW).then(RAISE_HALF_POT)
				.if_(ContributionType.HIGH.orLower()).then(RAISE_DOUBLE_POT)
				.build();

		s = s.river(STRAIGHT.and(FlushDanger.LOW).and(PairBasedDanger.LOW),
				ALL_IN);
		s = s.river(
				STRAIGHT.and(FlushDanger.HIGH.or(PairBasedDanger.HIGH)),
				CALL);
		s = s.river(FLUSH, ALL_IN);
		s = s.river(FULL_HOUSE, ALL_IN);
		s = s.river(FOUR_OF_A_KIND, ALL_IN);
		s = s.river(STRAIGHT_FLUSH, ALL_IN);

		s = s.river(TWO_PAIR.and(ContributionType.LOW), CALL);

		s = s.river(GOOD_SET)
				.if_(StraightDanger.HIGH.or(FlushDanger.HIGH)).then(FOLD)
				.if_(ContributionType.LOW).then(RAISE_QUARTER_POT)
				.if_(ContributionType.HIGH.or(ContributionType.MIDDLE))
				.then(RAISE_HALF_POT)
				.build();

	}

	// public static void main(String[] args) throws InterruptedException {
	// // MyOutput m = new MyOutput();
	// // Pos logo = recognizeLogo();
	// // m.clickRaiseButton(logo);
	//
	// // MapOfInteger<CardNum> map = new MapOfInteger<>();
	// // map.inc(CardNum.Ace);
	// //
	// // System.out.println(map.size());
	//
	// while (true) {
	// ScreenScraper scraper = new ScreenScraper();
	// scraper.start();
	// scraper.join();
	//
	// Raw_Situation sit = scraper.getSituation();
	//
	// // Situation2 sit3 = new Situation2(sit);
	// // System.out.println(sit);
	// // System.out.println(sit3);
	// //
	// // Decision d2 = nutStrategy.decide(sit3);
	// // System.out.println(d2);
	//
	// if (sit.itsMyTurn) {
	// Situation sit2 = new Situation(sit);
	// Decision d = nutStrategy.decide(sit2);
	// // Decision d = shitStrategy.decide(sit2);
	// System.out.println(d);
	//
	// MyOutput out = new MyOutput();
	// if (d.getTypeOfDecision() == FOLD)
	// out.clickFoldButton(scraper.logo);
	// else if (d.getTypeOfDecision() == CALL)
	// out.clickCallButton(scraper.logo);
	// else {
	// System.out.println(d);
	// }
	// }
	// try {
	// Thread.sleep(1000 * 2);
	// } catch (InterruptedException e) {
	// // should never happen.
	// e.printStackTrace();
	// }
	// }
	// }
}
