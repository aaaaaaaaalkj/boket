package start;

import static managementcards.catrecnew.Cathegory.FLUSH;
import static managementcards.catrecnew.Cathegory.FOUR_OF_A_KIND;
import static managementcards.catrecnew.Cathegory.FULL_HOUSE;
import static managementcards.catrecnew.Cathegory.STRAIGHT;
import static managementcards.catrecnew.Cathegory.STRAIGHT_FLUSH;
import static managementcards.catrecnew.Cathegory.THREE_OF_A_KIND;
import static managementcards.catrecnew.Cathegory.TWO_PAIR;
import static strategy.TypeOfDecision.ALL_IN;
import static strategy.TypeOfDecision.CALL;
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
import strategy.manualStrategy.StrategyImpl;

public final class StrategyDefinitions {
  public static final StrategyImpl STR = new StrategyImpl();

  private StrategyDefinitions() {
  }

  // poket pair (TT), am Flop Luschen. Ich bette pot-sized, er called. Am turn
  // kommt eine Dame, ich gehe all-in, er called. Er hatte AK und hat nichts
  // getroffen.
  static {

    STR.preflop(
        SUITED.or(CONNECTOR).or(POCKET_PAIR)
            .and(ContributionType.LOW.or(PotType.SMALL))
        , CALL);

    STR.flop(MONSTER_DRAW // 15 outs
        .and(PairBasedDanger.NONE) // no pair in flop
        , CALL);

    STR.flop(OESD.or(DOUBLE_GUTSHOT).and(FlushDanger.NONE) // rainbow
        .and(PairBasedDanger.NONE) // no pair in flop
        .and(ContributionType.MIDDLE.orLower()), CALL); // pay more
    // here?

    // strategy.flop(OESD.or(DOUBLE_GUTSHOT) // dangerous
    // .and(CONTRIBUTION_LESS_THAN(0.1)), CALL); // cheap chance

    STR.flop(FLUSH_DRAW // 9 outs
        .and(PairBasedDanger.NONE) // no pair in flop
        .and(ContributionType.MIDDLE.orLower()), CALL);

    // nutStrategy.flop( // besser formulieren und ausarbeiten. Sonst ist
    // das zu gefährlich
    // TWO_PAIR // 4 outs
    // .and(NO_FLUSH_DANGER).and(NO_STRAIGHT_DANGER)
    // .and(CONTRIBUTION_LESS_THAN(0.1)), RAISE(15));

    STR.flop(TWO_PAIR // 4 outs
        .and(ContributionType.LOW), CALL);

    ICondition set = THREE_OF_A_KIND.and(POCKET_PAIR);

    STR.flop(
        set.and(ContributionType.LOW)
            .and(StraightDanger.SIGNIFICANT.orLower()
                .or(FlushDanger.MODERATE.orLower())
            )
        , RAISE_QUARTER_POT
        );

    STR.flop(
        set.and(ContributionType.MIDDLE.orHigher())
            .and(StraightDanger.SIGNIFICANT.orLower()
                .or(FlushDanger.MODERATE.orLower())
            )
        , RAISE_DOUBLE_POT
        );

    STR.flop(STRAIGHT, RAISE_HALF_POT);
    STR.flop(FLUSH, RAISE_POT_SIZE);
    STR.flop(FULL_HOUSE, RAISE_POT_SIZE);
    STR.flop(FOUR_OF_A_KIND, RAISE_POT_SIZE);
    STR.flop(STRAIGHT_FLUSH, RAISE_POT_SIZE);

    STR.turn(STRAIGHT, RAISE_DOUBLE_POT);
    STR.turn(FLUSH, RAISE_DOUBLE_POT);
    STR.turn(FULL_HOUSE, RAISE_DOUBLE_POT);
    STR.turn(FOUR_OF_A_KIND, RAISE_DOUBLE_POT);
    STR.turn(STRAIGHT_FLUSH, RAISE_DOUBLE_POT);

    STR.turn(MONSTER_DRAW // 15 outs
        .and(PairBasedDanger.NONE) // no pair in flop
        .and(ContributionType.MIDDLE.orLower()), CALL);

    STR.turn(OESD.or(DOUBLE_GUTSHOT).and(FlushDanger.NONE) // rainbow
        .and(PairBasedDanger.NONE) // no pair in flop
        .and(ContributionType.MIDDLE.orLower()), CALL); // pay more
    // here?
    STR.turn(FLUSH_DRAW // 9 outs
        .and(PairBasedDanger.NONE) // no pair in flop
        .and(ContributionType.MIDDLE.orLower()), CALL);

    STR.turn(TWO_PAIR // 4 outs
        .and(ContributionType.LOW), CALL);

    STR.turn(set.and(
        ContributionType.LOW
        )
        .and(
            StraightDanger.SIGNIFICANT.orLower().or(
                FlushDanger.MODERATE.orLower())
        )
        , RAISE_HALF_POT);
    STR.turn(set.and(
        ContributionType.HIGH.orLower())
        .and(
            StraightDanger.SIGNIFICANT.orLower().or(
                FlushDanger.MODERATE.orLower())
        )
        , RAISE_DOUBLE_POT
        );

    STR.river(STRAIGHT.and(FlushDanger.MODERATE.orLower()).and(
        PairBasedDanger.NONE),
        ALL_IN);
    STR.river(
        STRAIGHT.and(FlushDanger.SIGNIFICANT.orLower().or(
            PairBasedDanger.MODERATE.orLower())),
        CALL);
    STR.river(FLUSH, ALL_IN);
    STR.river(FULL_HOUSE, ALL_IN);
    STR.river(FOUR_OF_A_KIND, ALL_IN);
    STR.river(STRAIGHT_FLUSH, ALL_IN);

    STR.river(TWO_PAIR.and(ContributionType.LOW), CALL);

    STR.river(set.and(
        ContributionType.MIDDLE.orLower()
            .and(
                StraightDanger.SIGNIFICANT.orLower().or(
                    FlushDanger.MODERATE.orLower())
            )
        ), RAISE_QUARTER_POT
        );
    STR.river(set.and(
        ContributionType.MIDDLE.orHigher()
        ).and(
            StraightDanger.SIGNIFICANT.orLower().or(
                FlushDanger.MODERATE.orLower())
        )
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
