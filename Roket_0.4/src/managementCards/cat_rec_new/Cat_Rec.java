package managementCards.cat_rec_new;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static managementCards.cards.Rank.Ace;
import static managementCards.cards.Rank.Eight;
import static managementCards.cards.Rank.Jack;
import static managementCards.cards.Rank.Seven;
import static managementCards.cards.Rank.Six;
import static managementCards.cards.Rank.Two;
import static managementCards.cat_rec_new.Cathegory.FOUR_OF_A_KIND;
import static managementCards.cat_rec_new.Cathegory.FULL_HOUSE;
import static managementCards.cat_rec_new.Cathegory.HIGH_CARD;
import static managementCards.cat_rec_new.Cathegory.PAIR;
import static managementCards.cat_rec_new.Cathegory.THREE_OF_A_KIND;
import static managementCards.cat_rec_new.Cathegory.TWO_PAIR;
import static managementCards.cat_rec_new.Freq.FOUR;
import static managementCards.cat_rec_new.Freq.ONE;
import static managementCards.cat_rec_new.Freq.THREE;
import static managementCards.cat_rec_new.Freq.TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;

public final class Cat_Rec implements ICatRec {
	List<Card> all;
	List<Card> community;

	final List<Rank> ranks; // Pair-Based

	public Cat_Rec(List<Card> hand, List<Card> community_cards) {
		all = new ArrayList<>();
		all.addAll(hand);
		all.addAll(community_cards);
		Collections.sort(all);
		Collections.reverse(all);

		community = new ArrayList<>(community_cards);

		ranks = all.stream()
				.map(Card::getRank)
				.collect(toList());
	}

	public ResultImpl check() {
		ResultImpl pairB = getPairBasedResult();
		ResultImpl flushB = getFlushOrStraightResult();

		return (flushB.compareTo(pairB) > 0) ? flushB : pairB;
	}

	private ResultImpl getPairBasedResult() {
		List<List<Rank>> choosenCards;
		if (has(FOUR)) {
			choosenCards = Arrays.asList(extract(FOUR), getTop(1));
			return result(FOUR_OF_A_KIND, choosenCards);
		}
		if (num(THREE) > 1) {
			choosenCards = Arrays.asList(extract(THREE),
					extract(THREE));
			return result(FULL_HOUSE, choosenCards);
		}
		if ((has(THREE) && has(TWO))) {
			choosenCards = Arrays.asList(extract(THREE),
					extract(TWO));
			return result(FULL_HOUSE, choosenCards);
		}
		if (has(THREE)) {
			choosenCards = Arrays.asList(extract(THREE), getTop(2));
			return result(THREE_OF_A_KIND, choosenCards);
		}
		if (num(TWO) > 1) {
			choosenCards = Arrays.asList(extract(TWO), extract(TWO), getTop(1));
			return result(TWO_PAIR, choosenCards);
		}
		if (has(TWO)) {
			choosenCards = Arrays.asList(extract(TWO), getTop(3));
			return result(PAIR, choosenCards);
		}
		if (has(ONE)) {
			choosenCards = Arrays.asList(getTop(5));
			return result(HIGH_CARD, choosenCards);
		}
		return result(HIGH_CARD, Collections.emptyList());
	}

	// +++++++++++++++++++++++++

	private List<Rank> getTop(int num) {
		Collections.sort(ranks); // ascending
		Collections.reverse(ranks); // desc

		List<Rank> top = ranks.subList(0, Math.min(num, ranks.size()));
		List<Rank> res = new ArrayList<>(top);
		top.clear(); // remove elems from ranks

		return res;
	}

	private List<Rank> extract(Freq f) {
		List<Rank> list = Rank.VALUES.stream()
				.filter(c -> Collections.frequency(ranks, c) == f.value)
				.collect(Collectors.toList());
		Collections.sort(list); // ascending
		Rank rank = list.get(list.size() - 1); // get last elem
		List<Rank> res = Collections.nCopies(f.value, rank);
		ranks.removeAll(res);
		return res;
	}

	private long num(Freq f) {
		return Rank.VALUES.stream()
				.filter(c -> Collections.frequency(ranks, c) == f.value)
				.count();
	}

	private boolean has(Freq f) {
		return num(f) > 0;
	}

	// +++++++++++++++++++++++++
	private ResultImpl result(Cathegory cat, List<List<Rank>> tieBreakers) {
		List<Rank> tie = tieBreakers.stream()
				.flatMap(Collection::stream)
				.limit(5)
				.collect(toList());
		return new ResultImpl(cat, tie);
	}

	private ResultImpl getFlushOrStraightResult() {

		Map<Suit, List<Rank>> map = all
				.stream()
				.collect(
						groupingBy(
								Card::getSuit,
								mapping(Card::getRank, toList())
						)
				);

		// StraightFlush
		for (Suit c : Suit.VALUES) {
			for (Window w : Window.getDescValues()) {
				if (map.get(c) != null && w.applies(map.get(c))) {
					return new ResultImpl(
							Cathegory.STRAIGHT_FLUSH,
							w.getRanks());
				}
			}
		}
		// Flush
		for (Suit c : Suit.values()) {
			if (map.get(c) != null && map.get(c).size() >= 5) {
				return new ResultImpl(Cathegory.FLUSH, map.get(c)
						.subList(0, 5));
			}
		}
		// Straight

		List<Rank> ranks2 = all.stream()
				.map(Card::getRank)
				.collect(toList());

		for (Window w : Window.getDescValues()) {
			if (w.applies(ranks2)) {
				return new ResultImpl(Cathegory.STRAIGHT, w.getRanks());
			}
		}
		// Nothing
		return new ResultImpl(Cathegory.HIGH_CARD); // worst possible result
	}

	private boolean checkOESD() {
		Set<Rank> ranks = new HashSet<>();
		for (Card c : all) {
			ranks.add(c.getRank());
		}
		boolean res = false;
		for (Rank r = Two; r.lessThan(Jack); r = r.next()) {
			Rank forbidden1 = r.prev();
			Rank forbidden2 = r.next().next().next().next();

			boolean oesd = true;
			oesd &= !ranks.contains(forbidden1);
			oesd &= !ranks.contains(forbidden2);

			oesd &= ranks.contains(r);
			oesd &= ranks.contains(r.next());
			oesd &= ranks.contains(r.next().next());
			oesd &= ranks.contains(r.next().next().next());
			res |= oesd;
		}
		return res;
	}

	private boolean checkGutshot() {
		Set<Rank> ranks = new HashSet<>();
		for (Card c : all) {
			ranks.add(c.getRank());
		}
		boolean res = false;
		EnumSet<Rank> r2 = EnumSet.range(Two, Jack);
		r2.add(Ace);
		for (Rank r : r2) {
			boolean gutshot = true;
			int n = 0;
			n += ranks.contains(r.next(1)) ? 1 : 0;
			n += ranks.contains(r.next(2)) ? 1 : 0;
			n += ranks.contains(r.next(3)) ? 1 : 0;

			gutshot &= ranks.contains(r);
			gutshot &= ranks.contains(r.next(3));
			gutshot &= n == 2;

			res |= gutshot;

		}

		return res;
	}

	private boolean checkDoubleGutshot() {
		Set<Rank> ranks = new HashSet<>();
		for (Card c : all) {
			ranks.add(c.getRank());
		}
		boolean res = false;
		EnumSet<Rank> r2 = EnumSet.range(Two, Six);
		r2.add(Ace);
		for (Rank r : r2) {
			boolean doubleGutshot = true;
			doubleGutshot &= ranks.contains(r.next(0));
			doubleGutshot &= ranks.contains(r.next(1));
			doubleGutshot &= ranks.contains(r.next(2));
			doubleGutshot &= !ranks.contains(r.next(3));
			doubleGutshot &= ranks.contains(r.next(4));
			doubleGutshot &= !ranks.contains(r.next(5));
			doubleGutshot &= ranks.contains(r.next(6));
			doubleGutshot &= ranks.contains(r.next(7));
			doubleGutshot &= ranks.contains(r.next(8));

			res |= doubleGutshot;
		}
		r2.add(Seven);
		for (Rank r : r2) {
			boolean doubleGutshot = true;
			doubleGutshot &= ranks.contains(r.next(0));
			doubleGutshot &= ranks.contains(r.next(1));
			doubleGutshot &= !ranks.contains(r.next(2));
			doubleGutshot &= ranks.contains(r.next(3));
			doubleGutshot &= ranks.contains(r.next(4));
			doubleGutshot &= !ranks.contains(r.next(5));
			doubleGutshot &= ranks.contains(r.next(6));
			doubleGutshot &= ranks.contains(r.next(7));

			res |= doubleGutshot;
		}
		r2.add(Eight);
		for (Rank r : r2) {
			boolean doubleGutshot = true;
			doubleGutshot &= ranks.contains(r.next(0));
			doubleGutshot &= !ranks.contains(r.next(1));
			doubleGutshot &= ranks.contains(r.next(2));
			doubleGutshot &= ranks.contains(r.next(3));
			doubleGutshot &= ranks.contains(r.next(4));
			doubleGutshot &= !ranks.contains(r.next(5));
			doubleGutshot &= ranks.contains(r.next(6));

			res |= doubleGutshot;
		}
		return res;
	}

	private boolean checkFlushDraw() {
		return all.stream()
				.collect(groupingBy(Card::getSuit, counting()))
				.values().stream()
				.max(naturalOrder())
				.map(x -> x == 4)
				.orElse(false);
	}

	public PairBasedDanger checkPairBasedDanger() {
		return Rank.VALUES.stream()
				.map(
						rank -> community.stream()
								.map(Card::getRank)
								.filter(rank::equals)
								.count()
				)
				.max(Comparator.naturalOrder())
				.map(PairBasedDanger::fromLong)
				.orElse(PairBasedDanger.NONE);
	}

	public StraightDanger checkStraightDanger() {
		return Window.getDescValues().stream()
				.map(
						window -> community.stream()
								.map(Card::getRank)
								.filter(window::contains)
								.distinct()
								.count()
				)
				.max(Comparator.naturalOrder())
				.map(StraightDanger::fromLong)
				.orElse(StraightDanger.NONE);
	}

	public FlushDanger checkFlushDanger() {
		return Suit.VALUES.stream()
				.map(
						suit -> community.stream()
								.map(Card::getSuit)
								.filter(suit::equals)
								.count()
				)
				.max(Comparator.naturalOrder())
				.map(FlushDanger::fromLong)
				.orElse(FlushDanger.NONE);
	}

	public DrawType checkDraw() {
		if (checkDoubleGutshot()) {
			if (checkFlushDraw()) {
				return DrawType.MONSTER_DRAW;
			} else {
				return DrawType.DOUBLE_GUTSHOT;
			}
		} else if (checkOESD()) {
			if (checkFlushDraw()) {
				return DrawType.MONSTER_DRAW;
			} else {
				return DrawType.OESD;
			}
		} else if (checkFlushDraw()) {
			return DrawType.FLUSH_DRAW;
		} else if (checkGutshot()) {
			return DrawType.GUTSHOT;
		} else {
			return DrawType.NONE;
		}
	}

	// public static ResultImpl check(List<Card> list) {
	// return new Cat_Rec(list.subList(0, 2), list.subList(2, list.size()))
	// .check();
	// }

}
