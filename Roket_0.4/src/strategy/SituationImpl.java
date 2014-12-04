package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import managementcards.CardManagement;
import managementcards.cards.Card;
import managementcards.cards.Rank;
import managementcards.catrecnew.CatRec;
import managementcards.catrecnew.Cathegory;
import managementpaymentsnewtmp.IPayManagement2;
import managementstate.IGameState;

import org.eclipse.jdt.annotation.NonNull;

import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;
import strategy.conditions.preflop.ConnectorType;
import strategy.conditions.preflop.SuitedType;

import common.Round;

public final class SituationImpl implements ISituation {
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

  // private final AmountOfJetons to_pay;
  // private final AmountOfJetons highest_bid;
  // private final AmountOfJetons pot2;
  // private final AmountOfJetons stack2;

  // private final int open_faces;

  public SituationImpl(
      final CardManagement table,
      final IGameState state,
      final IPayManagement2 payManagement) {
    int currentPlayer = state.getCurrentPlayer();

    CatRec catRec = table.getCatRec(currentPlayer);

    draw = catRec.checkDraw();
    flushDanger = catRec.checkFlushDanger();
    straightDanger = catRec.checkStraightDanger();
    pairBasedDanger = catRec.checkPairBasedDanger();

    cathegory = catRec.check().getCathegory();

    List<@NonNull Card> hand = table.getHand(currentPlayer);

    @SuppressWarnings("null")
    @NonNull
    OptionalInt optional = hand.stream()
        .map(Card::getRank)
        .mapToInt(Rank::ordinal)
        .reduce((x, y) -> Math.abs(x - y));

    connector = ConnectorType.fromInt(optional);
    long count = hand.stream()
        .map(Card::getSuit)
        .distinct()
        .count();
    switch ((int) count) {
    case 0:
      suited = SuitedType.NONE;
      break;
    case 1:
      suited = SuitedType.SUITED;
      break;
    default:
      suited = SuitedType.OFF_SUIT;
      break;
    }

    // to_pay = payManagement.toPay(currentPlayer);
    // highest_bid = payManagement.getHighestBid(currentPlayer);
    // pot2 = payManagement.computeTotalPot(currentPlayer);
    // stack2 = payManagement.getStack(currentPlayer);

    numActive = NumActiveType.fromInt(state.numActivePlayers());

    pot = payManagement.getPotType(currentPlayer);

    // pot = pot2.greaterOrEqual(stack2) ?
    // PotType.HIGH : pot2.greaterOrEqual(stack2.divideToEven(3)) ?
    // PotType.MIDDLE
    // : PotType.LOW;

    round = state.getRound();

    // if (table.isRainbow()) {
    // conditions.add(RAINBOW);
    // }

    List<Card> community = table.getCommunityCards();
    List<Card> allOpen = new ArrayList<>();
    allOpen.addAll(community);
    allOpen.addAll(hand);

    // if (connector == POCKET_PAIR && combo2 == Cathegory.THREE_OF_A_KIND)
    // {
    // combo2 = GOOD_SET;
    // }

    int toPay = payManagement.getToCall(currentPlayer);

    double contribution2 = ((double) toPay)
        / (payManagement.getPotSize() + toPay);
    contribution = contribution2 < .15
        ? ContributionType.LOW : (contribution2 < .3
            ? ContributionType.MIDDLE
            : ContributionType.HIGH);

  }

  public Round getRound() {
    return round;
  }

  @Override
  public ContributionType getContribution() {
    return contribution;
  }

  @Override
  public NumActiveType getNumActive() {
    return numActive;
  }

  @Override
  public PotType getPot() {
    return pot;
  }

  @Override
  public DrawType getDraw() {
    return draw;
  }

  @Override
  public ConnectorType getConnector() {
    return connector;
  }

  @Override
  public SuitedType getSuit() {
    return suited;
  }

  @Override
  public StraightDanger getStraightDanger() {
    return straightDanger;
  }

  @Override
  public FlushDanger getFlushDanger() {
    return flushDanger;
  }

  @Override
  public PairBasedDanger getPairBasedDanger() {
    return pairBasedDanger;
  }

  @Override
  public Cathegory getCathegory() {
    return cathegory;
  }

}
