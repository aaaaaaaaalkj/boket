package strategy;

import static common.Round.FLOP;
import static common.Round.PREFLOP;
import static common.Round.RIVER;
import static common.Round.TURN;
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
import static strategy.conditions.preflop.ConnectorType.CONNECTOR;
import static strategy.conditions.preflop.ConnectorType.POCKET_PAIR;
import static strategy.conditions.preflop.SuitedType.SUITED;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import managementCards.CardManagement;
import managementCards.cards.Card;
import managementCards.cards.Hand;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.Cathegory;
import managementCards.cat_rec_new.Result;
import managementPayments.AmountOfJetons;
import managementPayments.PaymentManagement;
import managementState.StateManagement;
import strategy.conditions.ICondition;
import strategy.conditions.ISituation;
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
import tools.MapOfInteger;

import common.PlayerId;
import common.Round;

public class SituationImpl implements ISituation {
	private final Set<ICondition> conditions = new HashSet<>();

	public final Hand hand;

	public final int num_active_players;
	public final AmountOfJetons to_pay;
	public final AmountOfJetons highest_bid;
	public final AmountOfJetons pot;
	public final AmountOfJetons stack;
	public final int flush_danger;
	public final int straight_danger;
	public final int open_faces;
	public final int pairbased_danger;
	public final double contribution;

	public SituationImpl(CardManagement table, StateManagement stateManagement,
			PaymentManagement payManagement) {
		PlayerId currentPlayer = stateManagement.getCurrent();
		hand = table.getHand(currentPlayer);
		if (hand != null) {
			if (hand.isSuited())
				conditions.add(SUITED);
			if (hand.isPocketPair())
				conditions.add(POCKET_PAIR);
			if (hand.isConnector())
				conditions.add(CONNECTOR);
		}

		to_pay = payManagement.toPay(currentPlayer);
		highest_bid = payManagement.getHighestBid(currentPlayer);
		pot = payManagement.computeTotalPot(currentPlayer);
		num_active_players = stateManagement.getActivePlayers().size();
		stack = payManagement.getStack(currentPlayer);

		conditions.add(table.getRound());

		// if (table.isRainbow()) {
		// conditions.add(RAINBOW);
		// }

		List<Card> community = table.getCommunityCards();
		List<Card> allOpen = table.getOpenCards(currentPlayer);

		flush_danger = computeFlushDanger(community);
		straight_danger = computeStraightDanger(community);

		if (isApplicably(FLOP) || isApplicably(TURN) || isApplicably(RIVER)) {

			if (Cat_Rec.checkOESD(allOpen))
				conditions.add(OESD);

			if (Cat_Rec.checkGutshot(allOpen))
				conditions.add(GUTSHOT);

			if (Cat_Rec.checkDoubleGutshot(allOpen))
				conditions.add(DOUBLE_GUTSHOT);

			if (Cat_Rec.checkFlushDraw(allOpen))
				conditions.add(FLUSH_DRAW);

			if (isApplicably(FLUSH_DRAW)
					&& (isApplicably(DOUBLE_GUTSHOT) || isApplicably(OESD)))
				conditions.add(MONSTER_DRAW);

			Result r2 = Cat_Rec.check(allOpen);

			if (r2.getCathegory() == Cathegory.Two_Pair)
				conditions.add(TWO_PAIR);
			if (r2.getCathegory() == Cathegory.Three_Of_A_Kind)
				conditions.add(THREE_OF_A_KIND);

			if (r2.getCathegory() == Cathegory.Straight)
				conditions.add(STRAIGHT);

			if (r2.getCathegory() == Cathegory.Flush)
				conditions.add(FLUSH);

			if (r2.getCathegory() == Cathegory.Full_House)
				conditions.add(FULL_HOUSE);

			if (r2.getCathegory() == Cathegory.Four_Of_A_Kind)
				conditions.add(FOUR_OF_A_KIND);
			if (r2.getCathegory() == Cathegory.Straight_Flush)
				conditions.add(STRAIGHT_FLUSH);

			Result r = Cat_Rec.checkPairBased(community);
			pairbased_danger = r.getCathegory().ordinal();

		} else {
			pairbased_danger = 0;
		}
		open_faces = table.getCommunityCards2().count(Rank.Ace, Rank.King,
				Rank.Queen, Rank.Jack);
		if (isApplicably(POCKET_PAIR) && isApplicably(THREE_OF_A_KIND))
			conditions.add(GOOD_SET);
		contribution = ((double) to_pay.numSmallBlinds())
				/ (pot.numSmallBlinds() + to_pay.numSmallBlinds());

	}

	private int computeStraightDanger(Collection<Card> community) {
		int[] ar = new int[Rank.VALUES.size()];
		for (Card c : community)
			ar[c.getRank().ordinal()] = 1;

		int sum = 0;
		for (int i = -1; i < 9; i++) {
			int sum2 = 0;
			for (int j = i; j < i + 5; j++) {
				if (j > 0)
					sum2 += ar[j];
				else
					sum2 += ar[ar.length - 1]; // ACE
			}
			if (sum2 > 1)
				sum += sum2;
		}
		return sum; // [2,25]
	}

	private int computeFlushDanger(Collection<Card> community) {
		int flush_danger = 0;
		MapOfInteger<Suit> map = new MapOfInteger<>();
		for (Card c : community)
			map.inc(c.getSuit());
		int sameColor = map.max();

		if (isApplicably(FLOP) || isApplicably(TURN)) {
			flush_danger = sameColor;
		} else if (isApplicably(RIVER)) {
			if (sameColor >= 3)
				flush_danger = sameColor;
			else
				// No Danger if sameColor < 3
				flush_danger = 0;
		} else { // PREFLOP
			flush_danger = 0;
		}
		return flush_danger;
	}

	public boolean isApplicably(ICondition c) {
		return conditions.contains(c);
	}

	public Round getRound() {
		if (conditions.contains(RIVER))
			return Round.RIVER;
		if (conditions.contains(TURN))
			return Round.TURN;
		if (conditions.contains(FLOP))
			return Round.FLOP;
		if (conditions.contains(PREFLOP))
			return Round.PREFLOP;
		throw new IllegalStateException(
				"round is neither of the four allowed rounds");
	}

	@Override
	public ContributionType getContribution() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumActiveType getNumActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PotType getPot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboType getCombo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DrawType getDraw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectorType getConnector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuitedType getSuit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectorType getConnectorType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumActiveType getNumActivePlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StraightDanger getStraightDanger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlushDanger getFlushDanger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PairBasedDanger getPairBasedDanger() {
		// TODO Auto-generated method stub
		return null;
	}
}