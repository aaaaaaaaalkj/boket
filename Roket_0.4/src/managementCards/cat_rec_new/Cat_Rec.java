package managementCards.cat_rec_new;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static managementCards.cards.Rank.Ace;
import static managementCards.cards.Rank.Eight;
import static managementCards.cards.Rank.Jack;
import static managementCards.cards.Rank.Seven;
import static managementCards.cards.Rank.Six;
import static managementCards.cards.Rank.Ten;
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

import org.eclipse.jdt.annotation.NonNull;

import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;

public final class Cat_Rec implements ICatRec {
	List<Card> all;
	List<Card> community;

	public Cat_Rec(Card first, Card second, List<Card> community_cards) {
		this(Arrays.asList(first, second), community_cards);
	}

	static long clock = 0;
	static long count = 0;

	public Cat_Rec(List<@NonNull Card> hand, List<@NonNull Card> community_cards) {

		all = new ArrayList<>();
		all.addAll(hand);
		all.addAll(community_cards);
		Collections.sort(all);
		Collections.reverse(all);

		community = new ArrayList<>(community_cards);

	}

	public ResultImpl check() {
		long l = System.currentTimeMillis();
		List<Rank> ranks = all.stream()
				.map(Card::getRank)
				.collect(toList());
		ResultImpl pairB = getPairBasedResult(ranks);
		ResultImpl flushB = getFlushOrStraightResult();

		l = System.currentTimeMillis() - l;
		clock += l;
		count++;
		if (count % 100000 == 0) {
			System.out.println("----------" + (clock));
		}

		return (flushB.compareTo(pairB) > 0) ? flushB : pairB;
	}

	private ResultImpl getPairBasedResult(List<Rank> ranks) {
		List<List<Rank>> choosenCards;
		if (has(ranks, FOUR)) {
			choosenCards = Arrays
					.asList(extract(ranks, FOUR), getTop(ranks, 1));
			return result(FOUR_OF_A_KIND, choosenCards);
		}
		if (num(ranks, THREE) > 1) {
			choosenCards = Arrays.asList(extract(ranks, THREE),
					extract(ranks, THREE));
			return result(FULL_HOUSE, choosenCards);
		}
		if ((has(ranks, THREE) && has(ranks, TWO))) {
			choosenCards = Arrays.asList(extract(ranks, THREE),
					extract(ranks, TWO));
			return result(FULL_HOUSE, choosenCards);
		}
		if (has(ranks, THREE)) {
			choosenCards = Arrays.asList(extract(ranks, THREE),
					getTop(ranks, 2));
			return result(THREE_OF_A_KIND, choosenCards);
		}
		if (num(ranks, TWO) > 1) {
			choosenCards = Arrays.asList(extract(ranks, TWO),
					extract(ranks, TWO), getTop(ranks, 1));
			return result(TWO_PAIR, choosenCards);
		}
		if (has(ranks, TWO)) {
			choosenCards = Arrays.asList(extract(ranks, TWO), getTop(ranks, 3));
			return result(PAIR, choosenCards);
		}
		if (has(ranks, ONE)) {
			choosenCards = Arrays.asList(getTop(ranks, 5));
			return result(HIGH_CARD, choosenCards);
		}
		return result(HIGH_CARD, Collections.emptyList());
	}

	// +++++++++++++++++++++++++

	private List<Rank> getTop(List<Rank> ranks, int num) {
		Collections.sort(ranks); // ascending
		Collections.reverse(ranks); // desc

		List<Rank> top = ranks.subList(0, Math.min(num, ranks.size()));
		List<Rank> res = new ArrayList<>(top);
		top.clear(); // remove elems from ranks

		return res;
	}

	private List<Rank> extract(List<Rank> ranks, Freq f) {
		List<Rank> list = Rank.VALUES.stream()
				.filter(c -> Collections.frequency(ranks, c) == f.value)
				.collect(Collectors.toList());
		Collections.sort(list); // ascending
		Rank rank = list.get(list.size() - 1); // get last elem
		List<Rank> res = Collections.nCopies(f.value, rank);
		ranks.removeAll(res);
		return res;
	}

	private long num(List<Rank> ranks, Freq f) {
		return Rank.VALUES.stream()
				.filter(c -> Collections.frequency(ranks, c) == f.value)
				.count();
	}

	private boolean has(List<Rank> ranks, Freq f) {
		return num(ranks, f) > 0;
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
				List<Rank> tieBreakers = new ArrayList<>(map.get(c)
						.subList(0, 5));
				// Collections.sort(tieBreakers);
				// Collections.reverse(tieBreakers);
				ResultImpl res = new ResultImpl(Cathegory.FLUSH, tieBreakers);

				// ResultImpl x = new ResultImpl(Cathegory.FLUSH, Arrays.asList(
				// Rank.Ace, Rank.Four, Rank.Four, Rank.Three, Rank.Two));
				//
				// if (x.equals(res)) {
				// System.out.println(all);
				// System.out.println("------- c: " + c + "  " + map.get(c));
				// }

				return res;
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
		Set<Rank> ranks = all.stream()
				.map(Card::getRank)
				.collect(toSet());

		boolean res = false;
		EnumSet<Rank> r2 = EnumSet.range(Two, Ten);
		r2.add(Ace);
		for (Rank r : r2) {
			boolean gutshot = true;
			int n = 0;
			n += ranks.contains(r.next(1)) ? 1 : 0;
			n += ranks.contains(r.next(2)) ? 1 : 0;
			n += ranks.contains(r.next(3)) ? 1 : 0;

			gutshot &= ranks.contains(r);
			gutshot &= ranks.contains(r.next(4));
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
		List<Rank> ranks = community.stream()
				.map(Card::getRank)
				.collect(toList());
		ResultImpl pairB = getPairBasedResult(ranks);
		Cathegory c = pairB.getCathegory();
		if (c == PAIR) {
			return PairBasedDanger.MODERATE;
		} else if (c == TWO_PAIR || c == THREE_OF_A_KIND) {
			return PairBasedDanger.HIGH;
		} else if (c == FULL_HOUSE || c == FOUR_OF_A_KIND) {
			return PairBasedDanger.CERTAIN_COMBO;
		} else {
			return PairBasedDanger.NONE;
		}
	}

	public StraightDanger checkStraightDanger() {
		Map<Long, List<Long>> map2 = Window
				.getDescValues()
				.stream()
				.map(
						window -> community.stream()
								.map(Card::getRank)
								.filter(window::contains)
								.distinct()
								.count()
				)
				.collect(groupingBy(x -> x));

		Map<Long, Integer> map = map2
				.entrySet()
				.stream()
				.collect(
						toMap(entry -> entry.getKey(), entry -> entry
								.getValue().size()));

		int num5 = map.get(5l) != null ? map.get(5l) : 0;
		int num4 = map.get(4l) != null ? map.get(4l) : 0;
		int num3 = map.get(3l) != null ? map.get(3l) : 0;
		int num2 = map.get(2l) != null ? map.get(2l) : 0;

		if (num5 > 0) {
			return StraightDanger.CERTAIN_STRAIGHT;
		} else if (num4 > 0) {
			return StraightDanger.VERY_HIGH;
		} else if (num3 > 0) {
			switch (num3) {
			case 4:
				return StraightDanger.VERY_HIGH;
			case 3:
				return StraightDanger.HIGH;
			case 2:
				return StraightDanger.SIGNIFICANT;
			case 1:
				return StraightDanger.MODERATE;
			default:
				throw new IllegalStateException("should never happen");
			}
		} else if (num2 > 2 && num2 < 5) {
			return StraightDanger.POSSIBLE_DRAW;
		} else if (num2 > 4) {
			return StraightDanger.PROBABLE_DRAW;
		} else {
			return StraightDanger.NONE;
		}
	}

	public FlushDanger checkFlushDanger() {
		if (community.isEmpty()) {
			return FlushDanger.NONE;
		}
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
