package managementCards.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import common.Round;

public class CommunityCards {
	private final List<Card> communityCards;
	private Round round;

	private CommunityCards(List<Card> cards, Round round) {
		this.communityCards = cards;
		this.round = round;
	}

	public static CommunityCards empty() {
		return new CommunityCards(new ArrayList<>(), Round.PREFLOP);
	}

	public static CommunityCards fromList(List<Card> list) {
		switch (list.size()) {
		case 0:
			return new CommunityCards(list, Round.PREFLOP);
		case 3:
			return new CommunityCards(list, Round.FLOP);
		case 4:
			return new CommunityCards(list, Round.TURN);
		case 5:
			return new CommunityCards(list, Round.RIVER);
		default:
			throw new IllegalArgumentException(
					"Unexpected number of community cards: "
							+ list.size());
		}
	}

	public static CommunityCards flop(Card first, Card second, Card third) {
		return new CommunityCards(Arrays.asList(first, second, third),
				Round.FLOP);
	}

	public static CommunityCards turn(Card first, Card second, Card third,
			Card turn) {
		return new CommunityCards(Arrays.asList(first, second, third, turn),
				Round.TURN);
	}

	public static CommunityCards river(Card first, Card second, Card third,
			Card turn, Card river) {
		return new CommunityCards(Arrays.asList(
				first,
				second,
				third,
				turn,
				river), Round.RIVER);
	}

	@Override
	public String toString() {
		return communityCards.stream()
				.map(Card::toString)
				.collect(Collectors.joining(", "));
	}

	public int count(Rank... ranks_) {
		List<Rank> ranks = Arrays.asList(ranks_);
		return (int) communityCards.stream()
				.map(Card::getRank)
				.filter(ranks::contains)
				.count();
	}

	public void open(Round r, Deck deck) {
		if (r == Round.PREFLOP) {
			// special case. do nothing here
			return;
		}
		if (r.followsAfter(round)) { // valid state-transition
			communityCards.add(deck.pop());
			if (r == Round.FLOP) {
				communityCards.add(deck.pop());
				communityCards.add(deck.pop());
			}
			round = round.next();
		} else {
			throw new IllegalStateException(
					"validation check failed in CommunityCards: " + round
							+ " -> " + r + " ");
		}
	}

	public Optional<Card> getFlop1() {
		if (communityCards.size() > 0) {
			return Optional.of(communityCards.get(0));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Card> getFlop2() {
		if (communityCards.size() > 1) {
			return Optional.of(communityCards.get(1));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Card> getFlop3() {
		if (communityCards.size() > 2) {
			return Optional.of(communityCards.get(2));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Card> getTurn() {
		if (communityCards.size() > 3) {
			return Optional.of(communityCards.get(3));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Card> getRiver() {
		if (communityCards.size() > 4) {
			return Optional.of(communityCards.get(4));
		} else {
			return Optional.empty();
		}
	}

	public Round getRound() {
		return round;
	}

	public boolean isRainbow() {
		return communityCards.stream()
				.map(Card::getSuit)
				.distinct()
				.count()
				== communityCards.size();
	}

	public List<Card> getCards() {
		return new ArrayList<>(communityCards);
	}

}
