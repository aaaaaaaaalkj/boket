package common;

import java.util.List;

import managementPayments.AmountOfJetons;
import managementState.PlayerState;
import managementcards.cards.Card;
import managementcards.cards.Hand;
import strategy.IStrategy;

public final class PlayerImpl implements IPlayer {
	private final String name;
	private final IStrategy strategy;
	private final int position;
	private Hand hand;
	private Stack stack;
	private PlayerState state;
	private AmountOfJetons post;

  public PlayerImpl(final String name, final int position, final Hand hand,
      final IStrategy strategy,
      final int bb) {
		this.name = name;
		this.position = position;
		this.strategy = strategy;
		this.hand = hand;
		this.stack = Stack.create(bb);
		this.state = PlayerState.ACTIVE;
		this.post = AmountOfJetons.ZERO;
	}

	public String toString() {
		return name;
	}

	@Override
	public IStrategy getStrategy() {
		return strategy;
	}

	@Override
  public void dealCards(final Card first, final Card second) {
		hand.setCards(first, second);
	}

	@Override
	public List<Card> getHand() {
		return hand.getCards();
	}

	@Override
	public Stack getStack() {
		return stack;
	}

	@Override
	public PlayerState getState() {
		return state;
	}

	@Override
  public void setState(final PlayerState state) {
		this.state = state;
	}

	@Override
	public AmountOfJetons getPost() {
		return post;
	}

	@Override
  public void setPost(final AmountOfJetons post) {
		this.post = post;
	}

	@Override
	public int getPosition() {
		return position;
	}

}
