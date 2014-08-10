package strategy.conditions;

import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.ComboType;
import strategy.conditions.postflop.DangerType;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.preflop.ConnectorType;
import strategy.conditions.preflop.SuitedType;

import common.Round;

public interface ISituation {
	ContributionType getContribution();

	NumActiveType getNumActive();

	PotType getPot();

	ComboType getCombo();

	DangerType getDanger();

	DrawType getDraw();

	ConnectorType getConnector();

	SuitedType getSuit();

	Round getRound();

	ConnectorType getConnectorType();

	NumActiveType getNumActivePlayers();
}
