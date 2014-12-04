package common;

import java.util.List;

import managementcards.cards.Card;
import managementpaymentstmp.AmountOfJetons;
import managementstate.PlayerState;
import strategy.IStrategy;

/**
 * Interface for Players.
 * 
 * @author Combat-Ready
 * 
 */
public interface IPlayer {
	IStrategy getStrategy();

	List<Card> getHand();

	void dealCards(Card first, Card second);

	Stack getStack();

	AmountOfJetons getPost();

	void setPost(AmountOfJetons amount);

	PlayerState getState();

	void setState(PlayerState state);

	/**
	 * 0 = SMALL_BLIND
	 * 
	 * 1 = BIG_BLIND
	 * 
	 * 2 = ...
	 * 
	 * n = BUTTON
	 * 
	 * @return
	 */
	int getPosition();
}
