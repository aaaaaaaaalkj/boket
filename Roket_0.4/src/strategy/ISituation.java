package strategy;

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

import common.Round;

public interface ISituation {
	ContributionType getContribution();

	NumActiveType getNumActive();

	PotType getPot();

	ComboType getCombo();

	StraightDanger getStraightDanger();

	FlushDanger getFlushDanger();

	PairBasedDanger getPairBasedDanger();

	DrawType getDraw();

	ConnectorType getConnector();

	SuitedType getSuit();

	Round getRound();

}
