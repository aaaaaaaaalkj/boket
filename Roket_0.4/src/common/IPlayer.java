package common;

import java.util.List;

import managementCards.cards.Card;
import strategy.IStrategy;

/**
 * Marker Interface for Players.
 * 
 * @author Combat-Ready
 * 
 */
public interface IPlayer {
	IStrategy getStrategy();

	List<Card> getHand();

	void dealCards(Card first, Card second);
}
