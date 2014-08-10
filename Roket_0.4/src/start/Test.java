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

public class Test {
	public final static IStrategy nutStrategy = new Strategy2();
	public final static IStrategy shitStrategy = new Strategy2();

	static {
		nutStrategy.preflop(
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

		nutStrategy.flop(MONSTER_DRAW // 15 outs
				.and(NO_FULL_HOUSE_DANGER) // no pair in flop
				.and(ContributionType.HIGH.orLower()), CALL);

		nutStrategy.flop(OESD.or(DOUBLE_GUTSHOT).and(NO_FLUSH_DANGER) // rainbow
				.and(NO_FULL_HOUSE_DANGER) // no pair in flop
				.and(ContributionType.MIDDLE.orLower()), CALL); // pay more
																// here?

		// strategy.flop(OESD.or(DOUBLE_GUTSHOT) // dangerous
		// .and(CONTRIBUTION_LESS_THAN(0.1)), CALL); // cheap chance

		nutStrategy.flop(FLUSH_DRAW // 9 outs
				.and(NO_FULL_HOUSE_DANGER) // no pair in flop
				.and(ContributionType.MIDDLE.orLower()), CALL);

		// nutStrategy.flop( // besser formulieren und ausarbeiten. Sonst ist
		// das zu gefährlich
		// TWO_PAIR // 4 outs
		// .and(NO_FLUSH_DANGER).and(NO_STRAIGHT_DANGER)
		// .and(CONTRIBUTION_LESS_THAN(0.1)), RAISE(15));

		nutStrategy.flop(TWO_PAIR // 4 outs
				.and(ContributionType.LOW), CALL);

		nutStrategy.flop(GOOD_SET)
				.if_(STRAIGHT_DANGER_HIGHER(10).or(FLUSH_DANGER)).then(FOLD)
				.if_(ContributionType.LOW).then(RAISE_QUARTER_POT)
				.if_(ContributionType.HIGH.orLower()).then(RAISE_DOUBLE_POT);

		nutStrategy.flop(STRAIGHT, RAISE_HALF_POT);
		nutStrategy.flop(FLUSH, RAISE_POT_SIZE);
		nutStrategy.flop(FULL_HOUSE, RAISE_POT_SIZE);
		nutStrategy.flop(FOUR_OF_A_KIND, RAISE_POT_SIZE);
		nutStrategy.flop(STRAIGHT_FLUSH, RAISE_POT_SIZE);

		nutStrategy.turn(STRAIGHT, RAISE_DOUBLE_POT);
		nutStrategy.turn(FLUSH, RAISE_DOUBLE_POT);
		nutStrategy.turn(FULL_HOUSE, RAISE_DOUBLE_POT);
		nutStrategy.turn(FOUR_OF_A_KIND, RAISE_DOUBLE_POT);
		nutStrategy.turn(STRAIGHT_FLUSH, RAISE_DOUBLE_POT);

		nutStrategy.turn(MONSTER_DRAW // 15 outs
				.and(NO_FULL_HOUSE_DANGER) // no pair in flop
				.and(ContributionType.MIDDLE), CALL);

		nutStrategy.turn(OESD.or(DOUBLE_GUTSHOT).and(NO_FLUSH_DANGER) // rainbow
				.and(NO_FULL_HOUSE_DANGER) // no pair in flop
				.and(ContributionType.MIDDLE), CALL); // pay more here?

		nutStrategy.turn(FLUSH_DRAW // 9 outs
				.and(NO_FULL_HOUSE_DANGER) // no pair in flop
				.and(ContributionType.MIDDLE), CALL);

		nutStrategy.turn(TWO_PAIR // 4 outs
				.and(ContributionType.LOW), CALL);

		nutStrategy.turn(GOOD_SET)
				.if_(STRAIGHT_DANGER_HIGHER(10).or(FLUSH_DANGER)).then(FOLD)
				.if_(ContributionType.LOW).then(RAISE_HALF_POT)
				.if_(ContributionType.HIGH.orLower()).then(RAISE_DOUBLE_POT);

		nutStrategy
				.river(STRAIGHT.and(NO_FLUSH_DANGER).and(NO_FULL_HOUSE_DANGER),
						ALL_IN);
		nutStrategy.river(STRAIGHT.and(FLUSH_DANGER.or(FULL_HOUSE_DANGER)),
				CALL);
		nutStrategy.river(FLUSH, ALL_IN);
		nutStrategy.river(FULL_HOUSE, ALL_IN);
		nutStrategy.river(FOUR_OF_A_KIND, ALL_IN);
		nutStrategy.river(STRAIGHT_FLUSH, ALL_IN);

		nutStrategy.river(TWO_PAIR.and(ContributionType.LOW), CALL);

		nutStrategy.river(GOOD_SET)
				.if_(STRAIGHT_DANGER_HIGHER(10).or(FLUSH_DANGER)).then(FOLD)
				.if_(ContributionType.LOW).then(RAISE_QUARTER_POT)
				.if_(ContributionType.HIGH.orLower()).then(RAISE_HALF_POT);

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
