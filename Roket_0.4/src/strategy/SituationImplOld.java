package strategy;

import static strategy.conditions.postflop.ComboType.FLUSH;
import static strategy.conditions.postflop.ComboType.FOUR_OF_A_KIND;
import static strategy.conditions.postflop.ComboType.FULL_HOUSE;
import static strategy.conditions.postflop.ComboType.GOOD_SET;
import static strategy.conditions.postflop.ComboType.STRAIGHT;
import static strategy.conditions.postflop.ComboType.STRAIGHT_FLUSH;
import static strategy.conditions.postflop.ComboType.THREE_OF_A_KIND;
import static strategy.conditions.postflop.ComboType.TWO_PAIR;
import static strategy.conditions.postflop.DrawType.DOUBLE_GUTSHOT;
import static strategy.conditions.postflop.DrawType.FLUSH_DRAW;
import static strategy.conditions.postflop.DrawType.GUTSHOT;
import static strategy.conditions.postflop.DrawType.MONSTER_DRAW;
import static strategy.conditions.postflop.DrawType.OESD;
import static strategy.conditions.preflop.ConnectorType.POCKET_PAIR;
import static strategy.conditions.preflop.SuitedType.OFF_SUIT;
import static strategy.conditions.preflop.SuitedType.SUITED;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import managementCards.CardManagement;
import managementCards.cards.Card;
import managementCards.cards.Hand;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.Cathegory;
import managementCards.cat_rec_new.Result;
import managementCards.cat_rec_new.Window;
import managementPayments.AmountOfJetons;
import managementPayments.PaymentManagement;
import managementState.StateManagement;
import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.ComboType;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;
import strategy.conditions.preflop.ConnectorType;
import strategy.conditions.preflop.SuitedType;

import common.PlayerId;
import common.Round;

public class SituationImplOld implements ISituation {

	private final Hand hand;

	private final ContributionType contribution;
	private final NumActiveType numActive;
	private final PotType pot;
	private final ConnectorType connector;
	private final SuitedType suited;
	private final ComboType combo;
	private final PairBasedDanger pairBasedDanger;
	private final FlushDanger flushDanger;
	private final StraightDanger straightDanger;
	private final DrawType draw;
	private final Round round;

	private final AmountOfJetons to_pay;
	private final AmountOfJetons highest_bid;
	private final AmountOfJetons pot2;
	private final AmountOfJetons stack2;

	// private final int open_faces;

	public SituationImplOld(
			CardManagement table,
			StateManagement stateManagement,
			PaymentManagement payManagement) {
		PlayerId currentPlayer = stateManagement.getCurrent();
		hand = table.getHand(currentPlayer);

		connector = ConnectorType.fromInt(hand.getDifference());
		suited = hand.isSuited() ? SUITED : OFF_SUIT;

		to_pay = payManagement.toPay(currentPlayer);
		highest_bid = payManagement.getHighestBid(currentPlayer);
		pot2 = payManagement.computeTotalPot(currentPlayer);

		numActive = NumActiveType.fromInt(stateManagement.getActivePlayers()
				.size());
		stack2 = payManagement.getStack(currentPlayer);

		pot = pot2.greaterOrEqual(stack2) ?
				PotType.HIGH : pot2.greaterOrEqual(stack2.divideToEven(2)) ?
						PotType.MIDDLE
						: PotType.LOW;

		round = table.getRound();

		// if (table.isRainbow()) {
		// conditions.add(RAINBOW);
		// }

		List<Card> community = table.getCommunityCards();
		List<Card> allOpen = table.getOpenCards(currentPlayer);

		flushDanger = computeFlushDanger(community);
		straightDanger = computeStraightDanger(community);
		pairBasedDanger = computePairBasedDanger(community);

		DrawType draw2 = null;
		ComboType combo2 = null;
		if (round != Round.PREFLOP) {

			if (Cat_Rec.checkGutshot(allOpen))
				draw2 = GUTSHOT;

			if (Cat_Rec.checkOESD(allOpen))
				draw2 = OESD;

			if (Cat_Rec.checkDoubleGutshot(allOpen)) {
				draw2 = DOUBLE_GUTSHOT;
			}

			if (Cat_Rec.checkFlushDraw(allOpen)) {
				if (draw2 == OESD || draw2 == DOUBLE_GUTSHOT) {
					draw2 = MONSTER_DRAW;
				} else {
					draw2 = FLUSH_DRAW;
				}
			}

			Result r2 = Cat_Rec.check(allOpen);

			if (r2.getCathegory() == Cathegory.Two_Pair)
				combo2 = TWO_PAIR;
			if (r2.getCathegory() == Cathegory.Three_Of_A_Kind)
				combo2 = THREE_OF_A_KIND;

			if (r2.getCathegory() == Cathegory.Straight)
				combo2 = STRAIGHT;

			if (r2.getCathegory() == Cathegory.Flush)
				combo2 = FLUSH;

			if (r2.getCathegory() == Cathegory.Full_House)
				combo2 = FULL_HOUSE;

			if (r2.getCathegory() == Cathegory.Four_Of_A_Kind)
				combo2 = FOUR_OF_A_KIND;

			if (r2.getCathegory() == Cathegory.Straight_Flush)
				combo2 = STRAIGHT_FLUSH;
		}
		if (connector == POCKET_PAIR && combo2 == THREE_OF_A_KIND) {
			combo2 = GOOD_SET;
		}
		combo = combo2;
		draw = draw2;
		double contribution2 = ((double) to_pay.numSmallBlinds())
				/ (pot2.numSmallBlinds() + to_pay.numSmallBlinds());
		contribution = contribution2 < .15 ?
				ContributionType.LOW : (contribution2 < .3 ?
						ContributionType.MIDDLE :
						ContributionType.HIGH);

	}

	private PairBasedDanger computePairBasedDanger(Collection<Card> community) {
		return Arrays.stream(Rank.values())
				.map(
						rank -> community.stream()
								.map(Card::getRank)
								.filter(rank::equals)
								.count()
				)
				.max(Comparator.naturalOrder())
				.map(PairBasedDanger::fromLong)
				.orElse(PairBasedDanger.LOW);
	}

	private StraightDanger computeStraightDanger(Collection<Card> community) {
		return Window.getDescValues().stream()
				.map(
						window -> community.stream()
								.map(Card::getRank)
								.filter(window::contains)
								.distinct()
								.count()
				)
				.max(Comparator.naturalOrder())
				.map(StraightDanger::fromLong)
				.orElse(StraightDanger.LOW);
	}

	private FlushDanger computeFlushDanger(Collection<Card> community) {
		return Arrays.stream(Suit.values())
				.map(
						suit -> community.stream()
								.map(Card::getSuit)
								.filter(suit::equals)
								.count()
				)
				.max(Comparator.naturalOrder())
				.map(FlushDanger::fromLong)
				.orElse(FlushDanger.LOW);
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
	public ComboType getCombo() {
		return combo;
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
	public ConnectorType getConnectorType() {
		return connector;
	}

	@Override
	public NumActiveType getNumActivePlayers() {
		return numActive;
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
}
