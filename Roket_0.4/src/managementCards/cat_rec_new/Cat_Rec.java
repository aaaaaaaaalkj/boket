package managementCards.cat_rec_new;

import static managementCards.cards.Rank.Ace;
import static managementCards.cards.Rank.Eight;
import static managementCards.cards.Rank.Jack;
import static managementCards.cards.Rank.Seven;
import static managementCards.cards.Rank.Six;
import static managementCards.cards.Rank.Two;
import static managementCards.cat_rec_new.Cathegory.Four_Of_A_Kind;
import static managementCards.cat_rec_new.Cathegory.Full_House;
import static managementCards.cat_rec_new.Cathegory.High_Card;
import static managementCards.cat_rec_new.Cathegory.Pair;
import static managementCards.cat_rec_new.Cathegory.Three_Of_A_Kind;
import static managementCards.cat_rec_new.Cathegory.Two_Pair;
import static managementCards.cat_rec_new.Freq.FOUR;
import static managementCards.cat_rec_new.Freq.ONE;
import static managementCards.cat_rec_new.Freq.THREE;
import static managementCards.cat_rec_new.Freq.TWO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;

public class Cat_Rec {
	CardFeatures cards;

	private Cat_Rec(Collection<Card> cards) {
		this.cards = new CardFeatures(cards);
	}

	public static IResult checkPairBased(Collection<Card> cards) {
		Cat_Rec catReq = new Cat_Rec(cards);
		return catReq.getPairBasedResult();
	}

	public static IResult check(Collection<Card> cards) {
		Cat_Rec catReq = new Cat_Rec(cards);

		IResult pairB = catReq.getPairBasedResult();
		IResult flushB = catReq.getFlushResult();

		return (flushB.compareTo(pairB) > 0) ? flushB : pairB;
	}

	public ResultImpl getPairBasedResult() {
		if (cards.has(FOUR)) {
			return result(Four_Of_A_Kind, cards.extract(FOUR), cards.getTop());
		}
		if (cards.num(THREE) > 1) {
			return result(Full_House, cards.extract(THREE),
					cards.extract(THREE));
		}
		if ((cards.has(THREE) && cards.has(TWO))) {
			return result(Full_House, cards.extract(THREE), cards.extract(TWO));
		}
		if (cards.has(THREE)) {
			return result(Three_Of_A_Kind, cards.extract(THREE),
					cards.getTop(2));
		}
		if (cards.num(TWO) > 1) {
			return result(Two_Pair, cards.extract(TWO), cards.extract(TWO),
					cards.getTop());
		}
		if (cards.has(TWO)) {
			return result(Pair, cards.extract(TWO), cards.getTop(3));
		}
		if (cards.has(ONE)) {
			return result(High_Card, cards.getTop(5));
		}
		return result(High_Card);
	}

	@SafeVarargs
	private final ResultImpl result(Cathegory cat, List<Rank>... tieBreakers) {
		List<Rank> fixedTieBreakers = new ArrayList<>();
		for (List<Rank> list : tieBreakers)
			fixedTieBreakers.addAll(list);

		if (fixedTieBreakers.size() > 5)
			fixedTieBreakers = fixedTieBreakers.subList(0, 5);
		return new ResultImpl(cat, fixedTieBreakers);
	}

	public IResult getFlushResult() {
		// StraightFlush
		for (Suit c : Suit.VALUES) {
			for (Window w : Window.getDescValues()) {
				if (w.applies(cards.flush.get(c))) {
					return new ResultImpl(Cathegory.Straight_Flush,
							w.getRanks());
				}
			}
		}
		// Flush
		for (Suit c : Suit.values()) {
			if (cards.flush.get(c).size() >= 5) {
				return new ResultImpl(Cathegory.Flush, cards.flush.get(c)
						.subList(0, 5));
			}
		}
		// Straight
		for (Window w : Window.getDescValues()) {
			if (w.applies(cards.ranks)) {
				return new ResultImpl(Cathegory.Straight, w.getRanks());
			}
		}
		// Nothing
		return new ResultImpl(Cathegory.High_Card); // worst possible result
	}

	public static boolean checkOESD(Collection<Card> cards) {
		Set<Rank> ranks = new HashSet<>();
		for (Card c : cards) {
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

	public static boolean checkGutshot(Collection<Card> cards) {
		Set<Rank> ranks = new HashSet<>();
		for (Card c : cards) {
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

	public static boolean checkDoubleGutshot(Collection<Card> cards) {
		Set<Rank> ranks = new HashSet<>();
		for (Card c : cards) {
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

	public static boolean checkFlushDraw(Collection<Card> cards) {
		EnumMap<Suit, Integer> map = new EnumMap<>(Suit.class);
		for (Card c : cards) {
			if (map.get(c.getSuit()) == null)
				map.put(c.getSuit(), 0);
			map.put(c.getSuit(), map.get(c.getSuit()) + 1);
		}
		for (Integer i : map.values()) {
			if (i == 4)
				return true;
			if (i > 4)
				return false;
		}
		return false;
	}

}
