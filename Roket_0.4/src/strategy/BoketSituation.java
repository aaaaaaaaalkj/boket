package strategy;

import inputoutput.Raw_Situation;

import java.util.Arrays;

import managementcards.catrecnew.CatRec;
import managementcards.catrecnew.Cathegory;
import old.Hand;
import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;
import strategy.conditions.preflop.ConnectorType;
import strategy.conditions.preflop.SuitedType;
import tools.Tools;

import common.Round;

public class BoketSituation implements ISituation {
  private final Round round;
  private final NumActiveType numActive;

  private final ContributionType contribution;
  private final PotType pot;

  private final ConnectorType connector;
  private final SuitedType suited;
  private final Cathegory cathegory;
  private final PairBasedDanger pairBasedDanger;
  private final FlushDanger flushDanger;
  private final StraightDanger straightDanger;
  private final DrawType draw;

  public BoketSituation(final Raw_Situation s) {
    switch (s.getCommunityCards().size()) {
    case 0:
      this.round = Round.PREFLOP;
      break;
    case 3:
      this.round = Round.FLOP;
      break;
    case 4:
      this.round = Round.TURN;
      break;
    case 5:
      this.round = Round.RIVER;
      break;
    default:
      throw new IllegalStateException(
          "Unexpected number of community-cards: "
              + s.getCommunityCards().size());
    }
    int count = 0;
    for (boolean b : s.getActiveStatus()) {
      if (b) {
        count++;
      }
    }
    this.numActive = NumActiveType.fromInt(count);

    double potTmp = s.getPot();
    double toPay = Tools.round(
        Arrays.stream(s.getPosts()).max().getAsDouble() - s.getPosts()[0]);

    double contribution = Tools.round(toPay / (potTmp + toPay));

    this.contribution = ContributionType.fromDouble(contribution);

    // double potOdds = new CardSimulation(count, s.hand, s.communityCards)
    // .run();

    System.out.println("pot: " + potTmp);
    System.out.println("toPay: " + toPay);
    System.out.println("contribution : " + contribution);
    // System.out.println("potOdds: " + potOdds);

    this.pot = PotType.of(potTmp / s.getStack());

    Hand hand = s.getHand();

    if (hand == null) {
      throw new IllegalArgumentException("Raw-Situation's hand is null");
    } else {
      this.connector = ConnectorType.fromRanks(
          hand.first.getRank(),
          hand.second.getRank()
          );
      this.suited = hand.isSuited()
          ? SuitedType.SUITED
          : SuitedType.OFF_SUIT;

      CatRec catRec = new CatRec(
          hand.getCards(),
          s.getCommunityCards()
          );

      draw = catRec.checkDraw();
      flushDanger = catRec.checkFlushDanger();
      straightDanger = catRec.checkStraightDanger();
      pairBasedDanger = catRec.checkPairBasedDanger();

      cathegory = catRec.check().getCathegory();
    }
  }

  @Override
  public final Round getRound() {
    return round;
  }

  @Override
  public final NumActiveType getNumActive() {
    return numActive;
  }

  @Override
  public final ContributionType getContribution() {
    return contribution;
  }

  @Override
  public final PotType getPot() {
    return pot;
  }

  @Override
  public final ConnectorType getConnector() {
    return connector;
  }

  @Override
  public final SuitedType getSuit() {
    return suited;
  }

  @Override
  public final Cathegory getCathegory() {
    return cathegory;
  }

  @Override
  public final PairBasedDanger getPairBasedDanger() {
    return pairBasedDanger;
  }

  public final FlushDanger getFlushDanger() {
    return flushDanger;
  }

  @Override
  public final StraightDanger getStraightDanger() {
    return straightDanger;
  }

  @Override
  public final DrawType getDraw() {
    return draw;
  }

  @Override
  public final String toString() {
    if (round == Round.PREFLOP) {
      return "BoketSituation [round=" + round + ", numActive="
          + numActive
          + ", contribution=" + contribution + ", pot=" + pot
          + "\n" + (suited == SuitedType.SUITED ? suited : "") + " "
          + (connector != ConnectorType.NONE ? connector : "")
          + "]";
    } else {
      return "BoketSituation [ " + round + ", "
          + numActive.getValue()
          + " active players, " + contribution
          + " contribution, " + pot + " pot"
          + "\n" + cathegory
          + (draw != DrawType.NONE ? " + " + draw : "")
          + "\n"
          + pairBasedDanger.toString()
          + " Pair-based danger\n"
          + flushDanger.toString() + " Flush danger\n"
          + straightDanger.toString()
          + " Straight danger" + "]";
    }

  }

}
