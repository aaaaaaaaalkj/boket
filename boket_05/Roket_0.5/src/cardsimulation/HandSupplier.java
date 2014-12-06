package cardsimulation;

import java.util.List;

import management.cards.cards.Card;

public interface HandSupplier {
	List<Card> getHand(int numPlayers, double contribution, double stddev);
}
