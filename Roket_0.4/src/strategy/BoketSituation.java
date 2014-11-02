package strategy;

import input_output.Raw_Situation;

import java.util.Arrays;

import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.Cathegory;
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

	public BoketSituation(Raw_Situation s) {
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
			if (b)
				count++;
		}
		this.numActive = NumActiveType.fromInt(count);

		double pot = s.getPot();
		double toPay = Arrays.stream(s.getPosts()).max().getAsDouble()
				- s.getPosts()[0];

		// System.out.println(pot);
		// System.out.println(toPay);

		this.contribution = ContributionType.fromDouble(toPay / pot);
		this.pot = PotType.of(pot / s.getStack());

		assert s.getHand() != null : "hand is null";

		this.connector = ConnectorType.fromRanks(s.getHand().first.getRank(),
				s.getHand().second.getRank()
				);
		this.suited = s.getHand().isSuited()
				? SuitedType.SUITED
				: SuitedType.OFF_SUIT;

		;
		Cat_Rec catRec = new Cat_Rec(
				s.getHand().getCards(),
				s.getCommunityCards()
				);

		draw = catRec.checkDraw();
		flushDanger = catRec.checkFlushDanger();
		straightDanger = catRec.checkStraightDanger();
		pairBasedDanger = catRec.checkPairBasedDanger();

		cathegory = catRec.check().getCathegory();
	}

	@Override
	public Round getRound() {
		return round;
	}

	@Override
	public NumActiveType getNumActive() {
		return numActive;
	}

	@Override
	public ContributionType getContribution() {
		return contribution;
	}

	@Override
	public PotType getPot() {
		return pot;
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
	public Cathegory getCathegory() {
		return cathegory;
	}

	@Override
	public PairBasedDanger getPairBasedDanger() {
		return pairBasedDanger;
	}

	public FlushDanger getFlushDanger() {
		return flushDanger;
	}

	@Override
	public StraightDanger getStraightDanger() {
		return straightDanger;
	}

	@Override
	public DrawType getDraw() {
		return draw;
	}

	@Override
	public String toString() {
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
