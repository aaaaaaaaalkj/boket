package managementCards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import managementCards.cards.Card;
import managementCards.cards.CommunityCards;
import managementCards.cards.Deck;
import managementCards.cards.Hand;

import common.Outcome;
import common.PlayerId;
import common.Round;

public class CardManagement {
	private final Deck deck;
	private final CommunityCards communityCards;
	private final List<PlayerHand> players;

	private static final class PlayerHand {
		private PlayerId player;
		private Hand hand;

		PlayerHand(PlayerId player) {
			this.player = player;
		}

		public void assignHand(Hand hand) {
			this.hand = hand;
		}

		public Hand getHand() {
			return hand;
		}

		public PlayerId getPlayerId() {
			return player;
		}

		public String toString() {
			return player + ": " + hand;
		}
	}

	public CardManagement(Random rand) {
		this.deck = new Deck(rand);
		this.communityCards = CommunityCards.empty();
		this.players = new ArrayList<>();
	}

	public Round getRound() {
		return this.communityCards.getRound();
	}

	public void register(PlayerId player) {
		this.players.add(new PlayerHand(player));
	}

	public void dealCards() {
		players.stream().forEach(p -> p.assignHand(Hand.fromDeck(deck)));
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

	public Hand getHand(PlayerId player) {
		return players
				.stream()
				.filter(p -> p.getPlayerId().equals(player))
				.map(PlayerHand::getHand)
				.findAny()
				.orElseThrow(
						() -> new IllegalStateException("player " + player
								+ " is unknown"));
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

	public List<Card> getOpenCards(PlayerId player) {
		List<Card> open = communityCards.getCards();
		open.add(getHand(player).getFirst());
		open.add(getHand(player).getSecond());
		return open;
	}

	public Outcome getOutcome() {
		Outcome1 outcome = new Outcome1();

		players.forEach(player ->
				outcome.computeResult(
						player.getPlayerId(),
						player.getHand(),
						communityCards)
				);
		return outcome;
	}

}
