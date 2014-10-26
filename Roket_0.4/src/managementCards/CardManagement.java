package managementCards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import managementCards.cards.Card;
import managementCards.cards.Deck;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.IResult;

import common.Round;

public class CardManagement implements ICardManagement {
	private final Deck deck;
	private final List<Card> communityCards;
	private final List<List<Card>> hands;

	public CardManagement(int countPlayers, Random rand) {
		this.deck = Deck.freshDeck(rand);
		this.communityCards = new ArrayList<>();
		this.hands = new ArrayList<>();
		for (int i = 0; i < countPlayers; i++) {
			List<Card> hand = new ArrayList<>();
			hand.add(deck.pop());
			hand.add(deck.pop());
			this.hands.add(hand);
		}
	}

	public void openCards(Round r) {
		if (r == Round.FLOP) {
			communityCards.add(deck.pop());
			communityCards.add(deck.pop());
			communityCards.add(deck.pop());
		}
		if (r == Round.TURN) {
			communityCards.add(deck.pop());
		}
		if (r == Round.RIVER) {
			communityCards.add(deck.pop());
		}
	}

	public String toString() {
		String res = "hands: ";
		res += hands.toString();
		res += "community: ";
		res += communityCards.toString();
		return res;
	}

	public List<Card> getCommunityCards() {
		return communityCards;
	}

	public List<Card> getHand(int player) {
		return hands.get(player);
	}

	public Cat_Rec getCatRec(int player) {
		return new Cat_Rec(getHand(player), communityCards);
	}

	public List<IResult> getResults() {
		List<IResult> res = new ArrayList<>();
		for (int i = 0; i < hands.size(); i++) {
			res.add(getCatRec(i).check());
		}
		return res;
	}

}
