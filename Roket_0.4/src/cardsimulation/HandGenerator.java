package cardsimulation;

import java.util.List;

import managementcards.cards.Card;

public interface HandGenerator {
	List<Card> getHand(int numPlayers, double contribution, double stddev);
}
