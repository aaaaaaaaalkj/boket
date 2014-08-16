package managementCards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import managementCards.cards.Card;
import managementCards.cards.CommunityCards;
import managementCards.cards.Deck;

import common.IOutcome;
import common.IPlayer;
import common.Round;

public class CardManagement {
	private final Deck deck;
	private final CommunityCards communityCards;
	private final List<IPlayer> players;

	public CardManagement(Random rand) {
		this.deck = new Deck(rand);
		this.communityCards = CommunityCards.empty();
		this.players = new ArrayList<>();
	}

	public Round getRound() {
		return this.communityCards.getRound();
	}

	public void register(IPlayer player) {
		this.players.add(player);
	}

	public void dealCards() {
		for (IPlayer p : players) {
			p.dealCards(deck.pop(), deck.pop());
		}
	}

	public void openCards(Round r) {
		communityCards.open(r, deck);
	}

	public String toString() {
		String res = players.stream()
				.map(Object::toString)
				.collect(Collectors.joining(","));
		res += communityCards.toString();
		return res;
	}

	public List<Card> getHand(IPlayer player) {
		return player.getHand();
	}

	public boolean isRainbow() {
		return communityCards.isRainbow();
	}

	public List<Card> getCommunityCards() {
		return communityCards.getCards();
	}

	public CommunityCards getCommunityCards2() {
		return communityCards;
	}

	public List<Card> getOpenCards(IPlayer player) {
		List<Card> open = new ArrayList<>();
		open.addAll(communityCards.getCards());
		open.addAll(player.getHand());
		return open;
	}

	public IOutcome getOutcome() {
		OutcomeImpl outcome = new OutcomeImpl();

		// List<Optional<Card>> list = new ArrayList<>();
		// list.add(Optional.of(hand.getFirst()));
		// list.add(Optional.of(hand.getSecond()));
		// list.add(communityCards.getFlop1());
		// list.add(communityCards.getFlop2());
		// list.add(communityCards.getFlop3());
		// list.add(communityCards.getTurn());
		// list.add(communityCards.getRiver());
		//
		// List<Card> present = StreamTools.unpack(list);
		//
		players.forEach(player ->
				outcome.computeResult(
						player,
						communityCards)
				);
		return outcome;
	}

}
