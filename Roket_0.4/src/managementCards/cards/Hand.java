package managementCards.cards;

public final class Hand {
	private final Card first;
	private final Card second;

	public Hand(Card c1, Card c2) {
		this.first = c1;
		this.second = c2;
	}

	public Card getFirst() {
		return first;
	}

	public Card getSecond() {
		return second;
	}

	public static Hand fromDeck(Deck deck) {
		assert deck.size() >= 2 : "not enough cards in deck";
		return new Hand(deck.get(), deck.get());
	}

	public int getDifference() {
		return first.getRank().ordinal() - second.getRank().ordinal();
	}

	public boolean isPocketPair() {
		return first.getRank() == second.getRank();
	}

	public boolean isSuited() {
		return first.getSuit() == second.getSuit();
	}

	public boolean isConnector() {
		return first.getRank().isConnected(second.getRank());
	}

	public String toString() {
		return first + " " + second;
	}
}
