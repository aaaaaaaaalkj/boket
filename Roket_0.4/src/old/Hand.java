package old;

import static old.StartingHand.o22;
import static old.StartingHand.o33;
import static old.StartingHand.o44;
import static old.StartingHand.o55;
import static old.StartingHand.o66;
import static old.StartingHand.o77;
import static old.StartingHand.o88;
import static old.StartingHand.o99;
import static old.StartingHand.oAA;
import static old.StartingHand.oAJ;
import static old.StartingHand.oAK;
import static old.StartingHand.oAQ;
import static old.StartingHand.oAT;
import static old.StartingHand.oJJ;
import static old.StartingHand.oJT;
import static old.StartingHand.oKJ;
import static old.StartingHand.oKK;
import static old.StartingHand.oKQ;
import static old.StartingHand.oKT;
import static old.StartingHand.oQJ;
import static old.StartingHand.oQQ;
import static old.StartingHand.oQT;
import static old.StartingHand.oTT;
import static old.StartingHand.s87;
import static old.StartingHand.s98;
import static old.StartingHand.sA2;
import static old.StartingHand.sA3;
import static old.StartingHand.sA4;
import static old.StartingHand.sA5;
import static old.StartingHand.sA6;
import static old.StartingHand.sA7;
import static old.StartingHand.sA8;
import static old.StartingHand.sA9;
import static old.StartingHand.sAJ;
import static old.StartingHand.sAK;
import static old.StartingHand.sAQ;
import static old.StartingHand.sAT;
import static old.StartingHand.sJT;
import static old.StartingHand.sKJ;
import static old.StartingHand.sKQ;
import static old.StartingHand.sKT;
import static old.StartingHand.sQJ;
import static old.StartingHand.sQT;
import static old.StartingHand.sT9;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import managementcards.cards.Card;
import old.strategy.PreflopBuket;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

public final class Hand {

	public final Card first;
	public final Card second;
	public final StartingHand h;

	private Hand(Card c1, Card c2) {
		this.first = Objects.requireNonNull(c1);
		this.second = Objects.requireNonNull(c2);
		String s = (first.getSuit() == second.getSuit() ? "s" : "o");
		if (first.getRank().ordinal() > second.getRank().ordinal()) {
			s += first.getRank().shortString();
			s += second.getRank().shortString();
		} else {
			s += second.getRank().shortString();
			s += first.getRank().shortString();
		}
		h = StartingHand.fromString(s);
	}

	public boolean isPocketPair() {
		return first.getRank() == second.getRank();
	}

	public boolean isWorthless(int numPlayers) {
		Card higher, lower;
		if (first.getRank().ordinal() > second.getRank().ordinal()) {
			higher = first;
			lower = second;
		} else {
			higher = second;
			lower = first;
		}

		if (isSuited())
			return Constants.probs[higher.getRank().ordinal()][lower.getRank()
					.ordinal()][numPlayers - 2] < 100.0 / numPlayers;
		else
			return Constants.probs[lower.getRank().ordinal()][higher.getRank()
					.ordinal()][numPlayers - 2] < 100.0 / numPlayers;
	}

	public boolean isSuited() {
		return first.getSuit() == second.getSuit();
	}

	public static Hand newInstance(String s) {
		int mid = s.indexOf(" ");
		Card c1 = Card.newInstance(Tools.substring(s, s.indexOf("[") + 1, mid));
		Card c2 = Card.newInstance(Tools.substring(s, mid + 1, s.indexOf("]")));
		return new Hand(c1, c2);
	}

	public static @Nullable Hand newInstance(@Nullable Card c1,
			@Nullable Card c2) {
		if (c1 == null || c2 == null) {
			return null;
		} else {
			return new Hand(c1, c2);
		}
	}

	public PreflopBuket getPreflopBuket() {
		StartingHand @NonNull [] veryStrongHands = { oAA, oKK, oQQ, sAK, oAK };
		StartingHand @NonNull [] strongHands = { oJJ, oTT, o99, sAQ, oAQ, sAJ };
		StartingHand @NonNull [] middleStrongHands = { oAJ, sAT, oAT, oKQ };
		StartingHand @NonNull [] strongSpeculativeHands = { o88, o77, o66, o55,
				o44, o33,
				o22, sKJ, sKT, sQJ, sQT, sJT, sT9 };
		StartingHand @NonNull [] mixedHands = { oKJ, oKT, oQJ, oQT, oJT, sA9,
				sA8, sA7,
				sA6, sA5, sA4, sA3, sA2, sKJ, s87, s98 };

		if (contains(veryStrongHands, h))
			return PreflopBuket.AA;

		if (contains(strongHands, h))
			return PreflopBuket.JJ;

		if (contains(middleStrongHands, h))
			return PreflopBuket.AJo;

		if (sKQ == h)
			return PreflopBuket.KQs;

		if (contains(strongSpeculativeHands, h))
			return PreflopBuket.EightEight;

		if (contains(mixedHands, h))
			return PreflopBuket.KJo;
		return PreflopBuket.worthless;
	}

	private boolean contains(StartingHand[] hands, StartingHand sH) {
		for (StartingHand h : hands)
			if (h == sH)
				return true;
		return false;
	}

	public boolean pairOfJacksOrBetter() {
		return StartingHand.pairOfJacksOrBetter.contains(h);
	}

	public boolean pairOfNinesOrBetter() {
		return StartingHand.pairOfNinesOrBetter.contains(h);
	}

	public boolean pairOfTensOrBetter() {
		return StartingHand.pairOfTensOrBetter.contains(h);
	}

	public boolean pairOfSevensOrBetter() {
		return StartingHand.pairOfSevensOrBetter.contains(h);
	}

	public boolean suitedConnector() {
		return StartingHand.suitedConnectors.contains(h);
	}

	public boolean middleAce() {
		return StartingHand.middleAces.contains(h);
	}

	public boolean lowSuitedAce() {
		return StartingHand.lowSuitedAces.contains(h);
	}

	public boolean highPair() {
		return StartingHand.highPairs.contains(h);
	}

	public boolean middlePair() {
		return StartingHand.middlePairs.contains(h);
	}

	public boolean lowPair() {
		return StartingHand.lowPairs.contains(h);
	}

	public boolean suitedFace() {
		return StartingHand.suitedFaces.contains(h);
	}

	public boolean offsuiteFace() {
		return StartingHand.offsuiteFaces.contains(h);
	}

	public boolean isAK() {
		return h == StartingHand.oAK || h == StartingHand.sAK;
	}

	public boolean isAQ() {
		return h == StartingHand.oAQ || h == StartingHand.sAQ;
	}

	public boolean is(StartingHand startingHand) {
		return startingHand == h;
	}

	@Override
	public String toString() {
		return "[" + first.shortString() + ", " + second.shortString() + "]";
	}

	public void print() {
		System.out.println(this);
	}

	public void printShort() {
		print();
	}

	// bucket = 0==AA, 1==KK, 2==QQ, 3==JJ, 4==TT,
	// 5==99-22,
	// 6==AKs,7==AKo,8==suitedMiddleAces,9==offsuiteMiddleAces,10==suitedLowAces,11==offsuitelowAces
	// 12==suitedFaces, 13==offsuiteFaces, 14=suitedConnectors, 15==suited,
	// 16==offsuite;
	public int bucket() {
		if (h == StartingHand.oAA)
			return 0;
		if (h == StartingHand.oKK)
			return 1;
		if (h == StartingHand.oQQ)
			return 2;
		if (h == StartingHand.oJJ)
			return 3;
		if (h == StartingHand.oTT)
			return 4;
		if (StartingHand.lowPairs.contains(h))
			return 5;
		if (h == StartingHand.sAK)
			return 6;
		if (h == StartingHand.oAK)
			return 7;
		if (StartingHand.suitedMiddleAces.contains(h))
			return 8;
		if (StartingHand.offSuiteMiddleAces.contains(h))
			return 9;
		if (StartingHand.lowSuitedAces.contains(h))
			return 10;
		if (StartingHand.lowOffSuiteAces.contains(h))
			return 11;
		if (StartingHand.suitedFaces.contains(h))
			return 12;
		if (StartingHand.offsuiteFaces.contains(h))
			return 13;
		if (StartingHand.suitedConnectors.contains(h))
			return 14;
		if (isSuited())
			return 15;
		return 16;

	}

	public java.util.Set<Card> cards() {
		java.util.Set<Card> set = new HashSet<Card>();
		set.add(first);
		set.add(second);
		return set;

	}

	public boolean isConnector() {
		return Math.abs(first.getRank().ordinal() - second.getRank().ordinal()) == 1;
	}

	public int getDifference() {
		return Math.abs(first.getRank().ordinal() - second.getRank().ordinal());
	}

	@SuppressWarnings("null")
	public Stream<Card> stream() {
		return Stream.of(first, second);
	}

	@SuppressWarnings("null")
	public List<@NonNull Card> getCards() {
		return Arrays.asList(first, second);
	}
}
