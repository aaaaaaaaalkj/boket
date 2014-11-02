package start;

import static managementCards.cat_rec_new.Cathegory.FLUSH;
import static managementCards.cat_rec_new.Cathegory.FOUR_OF_A_KIND;
import static managementCards.cat_rec_new.Cathegory.FULL_HOUSE;
import static managementCards.cat_rec_new.Cathegory.STRAIGHT;
import static managementCards.cat_rec_new.Cathegory.STRAIGHT_FLUSH;
import static managementCards.cat_rec_new.Cathegory.THREE_OF_A_KIND;
import static managementCards.cat_rec_new.Cathegory.TWO_PAIR;
import static strategy.TypeOfDecision.ALL_IN;
import static strategy.TypeOfDecision.CALL;
import static strategy.TypeOfDecision.FOLD;
import static strategy.TypeOfDecision.RAISE_DOUBLE_POT;
import static strategy.TypeOfDecision.RAISE_HALF_POT;
import static strategy.TypeOfDecision.RAISE_POT_SIZE;
import static strategy.TypeOfDecision.RAISE_QUARTER_POT;
import static strategy.conditions.postflop.DrawType.DOUBLE_GUTSHOT;
import static strategy.conditions.postflop.DrawType.FLUSH_DRAW;
import static strategy.conditions.postflop.DrawType.MONSTER_DRAW;
import static strategy.conditions.postflop.DrawType.OESD;
import static strategy.conditions.preflop.ConnectorType.CONNECTOR;
import static strategy.conditions.preflop.ConnectorType.POCKET_PAIR;
import static strategy.conditions.preflop.SuitedType.SUITED;
import strategy.conditions.ICondition;
import strategy.conditions.common.ContributionType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;
import strategy.manualStrategy.Strategy2;

public class StrategyDefinitions {
	public static Strategy2 s = new Strategy2();

	static {
		s.preflop(
				SUITED.or(CONNECTOR).or(POCKET_PAIR)
						.and(ContributionType.LOW.or(PotType.SMALL))
				, CALL);

		s.flop(MONSTER_DRAW // 15 outs
				.and(PairBasedDanger.NONE) // no pair in flop
				.and(ContributionType.HIGH.orLower()), CALL);

		s.flop(OESD.or(DOUBLE_GUTSHOT).and(FlushDanger.NONE) // rainbow
				.and(PairBasedDanger.NONE) // no pair in flop
				.and(ContributionType.MIDDLE.orLower()), CALL); // pay more
																// here?

		// strategy.flop(OESD.or(DOUBLE_GUTSHOT) // dangerous
		// .and(CONTRIBUTION_LESS_THAN(0.1)), CALL); // cheap chance

		s.flop(FLUSH_DRAW // 9 outs
				.and(PairBasedDanger.NONE) // no pair in flop
				.and(ContributionType.MIDDLE.orLower()), CALL);

		// nutStrategy.flop( // besser formulieren und ausarbeiten. Sonst ist
		// das zu gefährlich
		// TWO_PAIR // 4 outs
		// .and(NO_FLUSH_DANGER).and(NO_STRAIGHT_DANGER)
		// .and(CONTRIBUTION_LESS_THAN(0.1)), RAISE(15));

		s.flop(TWO_PAIR // 4 outs
				.and(ContributionType.LOW), CALL);

		ICondition set = THREE_OF_A_KIND.and(POCKET_PAIR);
		s.flop(
				set.and(StraightDanger.HIGH.orHigher()
						.or(FlushDanger.SIGNIFICANT.orHigher())
						)
				, FOLD
				);

		s.flop(
				set.and(ContributionType.LOW)
				, RAISE_QUARTER_POT
				);

		s.flop(
				set.and(ContributionType.HIGH.orLower())
				, RAISE_DOUBLE_POT
				);

		s.flop(STRAIGHT, RAISE_HALF_POT);
		s.flop(FLUSH, RAISE_POT_SIZE);
		s.flop(FULL_HOUSE, RAISE_POT_SIZE);
		s.flop(FOUR_OF_A_KIND, RAISE_POT_SIZE);
		s.flop(STRAIGHT_FLUSH, RAISE_POT_SIZE);

		s.turn(STRAIGHT, RAISE_DOUBLE_POT);
		s.turn(FLUSH, RAISE_DOUBLE_POT);
		s.turn(FULL_HOUSE, RAISE_DOUBLE_POT);
		s.turn(FOUR_OF_A_KIND, RAISE_DOUBLE_POT);
		s.turn(STRAIGHT_FLUSH, RAISE_DOUBLE_POT);

		s.turn(MONSTER_DRAW // 15 outs
				.and(PairBasedDanger.NONE) // no pair in flop
				.and(ContributionType.MIDDLE), CALL);

		s.turn(OESD.or(DOUBLE_GUTSHOT).and(FlushDanger.NONE) // rainbow
				.and(PairBasedDanger.NONE) // no pair in flop
				.and(ContributionType.MIDDLE), CALL); // pay more here?

		s.turn(FLUSH_DRAW // 9 outs
				.and(PairBasedDanger.NONE) // no pair in flop
				.and(ContributionType.MIDDLE), CALL);

		s.turn(TWO_PAIR // 4 outs
				.and(ContributionType.LOW), CALL);

		s.turn(set.and(
				StraightDanger.HIGH.orHigher().or(
						FlushDanger.SIGNIFICANT.orHigher())
				)
				, FOLD);
		s.turn(set.and(
				ContributionType.LOW
				), RAISE_HALF_POT);
		s.turn(set.and(
				ContributionType.HIGH.orLower())
				, RAISE_DOUBLE_POT
				);

		s.river(STRAIGHT.and(FlushDanger.NONE).and(PairBasedDanger.NONE),
				ALL_IN);
		s.river(
				STRAIGHT.and(FlushDanger.SIGNIFICANT.or(PairBasedDanger.HIGH)),
				CALL);
		s.river(FLUSH, ALL_IN);
		s.river(FULL_HOUSE, ALL_IN);
		s.river(FOUR_OF_A_KIND, ALL_IN);
		s.river(STRAIGHT_FLUSH, ALL_IN);

		s.river(TWO_PAIR.and(ContributionType.LOW), CALL);

		s.river(set.and(
				StraightDanger.HIGH.orHigher().or(
						FlushDanger.SIGNIFICANT.orHigher())
				)
				, FOLD);
		s.river(set.and(
				ContributionType.LOW), RAISE_QUARTER_POT
				);
		s.river(set.and(
				ContributionType.HIGH.orHigher().or(
						ContributionType.MIDDLE.orHigher()))
				, RAISE_HALF_POT);

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
