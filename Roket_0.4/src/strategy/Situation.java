package strategy;

import managementCards.cards.Hand;
import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.ComboType;
import strategy.conditions.postflop.DangerType;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.preflop.ConnectorType;
import strategy.conditions.preflop.SuitedType;

import common.Round;

public interface Situation {

	Round getRound();

	Hand getHand();

	ConnectorType getConnectorType();

	ContributionType getContribution();

	NumActiveType getNumActivePlayers();

	SuitedType getSuit();

	PotType getPot();

	ComboType getCombo();

	DangerType getDanger();

	DrawType getDraw();

}
