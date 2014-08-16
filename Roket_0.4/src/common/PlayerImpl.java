package common;

import java.util.List;

import managementCards.cards.Card;
import managementCards.cards.Hand;
import strategy.IStrategy;

public final class PlayerImpl implements IPlayer {
	private final String name;
	private final IStrategy strategy;
	private Hand hand;

	public PlayerImpl(String name, IStrategy strategy) {
		this.name = name;
		this.strategy = strategy;
		this.hand = Hand.empty();
	}

	public String toString() {
		return name;
	}

	@Override
	public IStrategy getStrategy() {
		return strategy;
	}

	public void dealCards(Card first, Card second) {
		hand.setCards(first, second);
	}

	@Override
	public List<Card> getHand() {
		return hand.getCards();
	}

}
