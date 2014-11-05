package strategy;

import managementCards.cat_rec_new.Cathegory;
import strategy.conditions.Utility;
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

public interface ISituation {
	ContributionType getContribution();

	NumActiveType getNumActive();

	PotType getPot();

	Cathegory getCathegory();

	StraightDanger getStraightDanger();

	FlushDanger getFlushDanger();

	PairBasedDanger getPairBasedDanger();

	DrawType getDraw();

	ConnectorType getConnector();

	SuitedType getSuit();

	Round getRound();

	Utility getUtility();

}
