package card_simulation;

import java.util.List;

import managementCards.cards.Card;

public interface HandGenerator {
	List<Card> getHand(int numPlayers, double contribution);
}
