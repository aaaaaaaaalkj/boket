package managementcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import managementcards.cards.Card;
import managementcards.cards.Deck;
import managementcards.catrecnew.CatRec;
import managementcards.catrecnew.IResult;

import org.eclipse.jdt.annotation.NonNull;

import common.Round;

public class CardManagement implements ICardManagement {
	private final Deck deck;
	private final List<@NonNull Card> communityCards;
	private final List<@NonNull List<@NonNull Card>> hands;

  public CardManagement(final int countPlayers, final Random rand) {
		this.deck = Deck.freshDeck(rand);
		this.communityCards = new ArrayList<>();
		this.hands = new ArrayList<>();
		for (int i = 0; i < countPlayers; i++) {
			List<@NonNull Card> hand = new ArrayList<>();
			hand.add(deck.pop());
			hand.add(deck.pop());
			this.hands.add(hand);
		}
	}

  public final void openCards(final Round r) {
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

  public final String toString() {
		String res = "hands: ";
		res += hands.toString();
		res += "community: ";
		res += communityCards.toString();
		return res;
	}

  public final List<@NonNull Card> getCommunityCards() {
		return communityCards;
	}

  public final List<@NonNull Card> getHand(final int player) {
		return hands.get(player);
	}

  public final CatRec getCatRec(final int player) {
		return new CatRec(getHand(player), communityCards);
	}

  public final List<@NonNull IResult> getResults() {
		List<IResult> res = new ArrayList<>();
		for (int i = 0; i < hands.size(); i++) {
			res.add(getCatRec(i).check());
		}
		return res;
	}

}
