package ranges;

import static management.cards.cards.Rank.Ace;
import static management.cards.cards.Rank.Eight;
import static management.cards.cards.Rank.Five;
import static management.cards.cards.Rank.Four;
import static management.cards.cards.Rank.Jack;
import static management.cards.cards.Rank.King;
import static management.cards.cards.Rank.Nine;
import static management.cards.cards.Rank.Queen;
import static management.cards.cards.Rank.Seven;
import static management.cards.cards.Rank.Six;
import static management.cards.cards.Rank.Ten;
import static management.cards.cards.Rank.Three;
import static management.cards.cards.Rank.Two;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import management.cards.cards.Rank;
import management.cards.cards.Suit;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public enum GroupedRange implements Range {
	AA(Ace, Ace, false),
	AK(Ace, King, true),
	AKo(Ace, King, false),
	AQ(Ace, Queen, true),
	AQo(Ace, Queen, false),
	AJ(Ace, Jack, true),
	AJo(Ace, Jack, false),
	AT(Ace, Ten, true),
	ATo(Ace, Ten, false),
	A9(Ace, Nine, true),
	A9o(Ace, Nine, false),
	A8(Ace, Eight, true),
	A8o(Ace, Eight, false),
	A7(Ace, Seven, true),
	A7o(Ace, Seven, false),
	A6(Ace, Six, true),
	A6o(Ace, Six, false),
	A5(Ace, Five, true),
	A5o(Ace, Five, false),
	A4(Ace, Four, true),
	A4o(Ace, Four, false),
	A3(Ace, Three, true),
	A3o(Ace, Three, false),
	A2(Ace, Two, true),
	A2o(Ace, Two, false),
	KK(King, King, false),
	KQ(King, Queen, true),
	KQo(King, Queen, false),
	KJ(King, Jack, true),
	KJo(King, Jack, false),
	KT(King, Ten, true),
	KTo(King, Ten, false),
	K9(King, Nine, true),
	K9o(King, Nine, false),
	K8(King, Eight, true),
	K8o(King, Eight, false),
	K7(King, Seven, true),
	K7o(King, Seven, false),
	K6(King, Six, true),
	K6o(King, Six, false),
	K5(King, Five, true),
	K5o(King, Five, false),
	K4(King, Four, true),
	K4o(King, Four, false),
	K3(King, Three, true),
	K3o(King, Three, false),
	K2(King, Two, true),
	K2o(King, Two, false),
	QQ(Queen, Queen, false),
	QJ(Queen, Jack, true),
	QJo(Queen, Jack, false),
	QT(Queen, Ten, true),
	QTo(Queen, Ten, false),
	Q9(Queen, Nine, true),
	Q9o(Queen, Nine, false),
	Q8(Queen, Eight, true),
	Q8o(Queen, Eight, false),
	Q7(Queen, Seven, true),
	Q7o(Queen, Seven, false),
	Q6(Queen, Six, true),
	Q6o(Queen, Six, false),
	Q5(Queen, Five, true),
	Q5o(Queen, Five, false),
	Q4(Queen, Four, true),
	Q4o(Queen, Four, false),
	Q3(Queen, Three, true),
	Q3o(Queen, Three, false),
	Q2(Queen, Two, true),
	Q2o(Queen, Two, false),
	JJ(Jack, Jack, false),
	JT(Jack, Ten, true),
	JTo(Jack, Ten, false),
	J9(Jack, Nine, true),
	J9o(Jack, Nine, false),
	J8(Jack, Eight, true),
	J8o(Jack, Eight, false),
	J7(Jack, Seven, true),
	J7o(Jack, Seven, false),
	J6(Jack, Six, true),
	J6o(Jack, Six, false),
	J5(Jack, Five, true),
	J5o(Jack, Five, false),
	J4(Jack, Four, true),
	J4o(Jack, Four, false),
	J3(Jack, Three, true),
	J3o(Jack, Three, false),
	J2(Jack, Two, true),
	J2o(Jack, Two, false),
	TT(Ten, Ten, false),
	T9(Ten, Nine, true),
	T9o(Ten, Nine, false),
	T8(Ten, Eight, true),
	T8o(Ten, Eight, false),
	T7(Ten, Seven, true),
	T7o(Ten, Seven, false),
	T6(Ten, Six, true),
	T6o(Ten, Six, false),
	T5(Ten, Five, true),
	T5o(Ten, Five, false),
	T4(Ten, Four, true),
	T4o(Ten, Four, false),
	T3(Ten, Three, true),
	T3o(Ten, Three, false),
	T2(Ten, Two, true),
	T2o(Ten, Two, false),
	_99(Nine, Nine, false),
	_98(Nine, Eight, true),
	_98o(Nine, Eight, false),
	_97(Nine, Seven, true),
	_97o(Nine, Seven, false),
	_96(Nine, Six, true),
	_96o(Nine, Six, false),
	_95(Nine, Five, true),
	_95o(Nine, Five, false),
	_94(Nine, Four, true),
	_94o(Nine, Four, false),
	_93(Nine, Three, true),
	_93o(Nine, Three, false),
	_92(Nine, Two, true),
	_92o(Nine, Two, false),
	_88(Eight, Eight, false),
	_87(Eight, Seven, true),
	_87o(Eight, Seven, false),
	_86(Eight, Six, true),
	_86o(Eight, Six, false),
	_85(Eight, Five, true),
	_85o(Eight, Five, false),
	_84(Eight, Four, true),
	_84o(Eight, Four, false),
	_83(Eight, Three, true),
	_83o(Eight, Three, false),
	_82(Eight, Two, true),
	_82o(Eight, Two, false),
	_77(Seven, Seven, false),
	_76(Seven, Six, true),
	_76o(Seven, Six, false),
	_75(Seven, Five, true),
	_75o(Seven, Five, false),
	_74(Seven, Four, true),
	_74o(Seven, Four, false),
	_73(Seven, Three, true),
	_73o(Seven, Three, false),
	_72(Seven, Two, true),
	_72o(Seven, Two, false),
	_66(Six, Six, false),
	_65(Six, Five, true),
	_65o(Six, Five, false),
	_64(Six, Four, true),
	_64o(Six, Four, false),
	_63(Six, Three, true),
	_63o(Six, Three, false),
	_62(Six, Two, true),
	_62o(Six, Two, false),
	_55(Five, Five, false),
	_54(Five, Four, true),
	_54o(Five, Four, false),
	_53(Five, Three, true),
	_53o(Five, Three, false),
	_52(Five, Two, true),
	_52o(Five, Two, false),
	_44(Four, Four, false),
	_43(Four, Three, true),
	_43o(Four, Three, false),
	_42(Four, Two, true),
	_42o(Four, Two, false),
	_33(Three, Three, false),
	_32(Three, Two, true),
	_32o(Three, Two, false),
	_22(Two, Two, false);

	private final Rank r1, r2;
	private final boolean suited;

	private GroupedRange(final Rank r1, final Rank r2, final boolean suited) {
		this.r1 = r1;
		this.r2 = r2;
		this.suited = suited;
	}

	private static class Finder {
		private Rank r1;
		private Rank r2;
		private boolean suited;

		public Finder(final Rank r1, final Rank r2, final boolean suited) {
			this.r1 = r1;
			this.r2 = r2;
			this.suited = suited;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			final int prime1231 = 1231;
			final int prime1237 = 1237;

			int result = 1;
			result = prime * result + r1.hashCode();
			result = prime * result + r2.hashCode();
			result = prime * result + (suited ? prime1231 : prime1237);
			return result;
		}

		@Override
		public boolean equals(@Nullable final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Finder other = (Finder) obj;
			if (r1 != other.r1) {
				return false;
			}
			if (r2 != other.r2) {
				return false;
			}
			if (suited != other.suited) {
				return false;
			}
			return true;
		}

	}

	@SuppressWarnings("null")
	public static final List<@NonNull GroupedRange> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));

	private static final Map<Finder, GroupedRange> MAP = new HashMap<>();
	static {
		for (GroupedRange r : GroupedRange.VALUES) {
			MAP.put(new Finder(r.r1, r.r2, r.suited), r);
			MAP.put(new Finder(r.r2, r.r1, r.suited), r);
		}
	}

	public static GroupedRange find(final Rank r1, final Rank r2,
			final boolean suited) {
		return MAP.get(new Finder(r1, r2, suited));
	}

	public SimpleRange ungroup() {
		SimpleRange res = new SimpleRange();
		ungroup2(res, r1, r2, suited);
		return res;
	}

	public static void ungroup2(final SimpleRange ranges, final Rank r1,
			final Rank r2,
			final boolean suited) {
		for (int i = 0; i < Suit.VALUES.size(); i++) {
			for (int j = 0; j < Suit.VALUES.size(); j++) {
				if (i == j == suited) {
					Suit s1 = Suit.VALUES.get(i);
					Suit s2 = Suit.VALUES.get(j);
					ranges.add(ElementRange.find(r1, s1, r2, s2));
				}
			}
		}
	}

	public boolean isSuited() {
		return suited;
	}

	public boolean isPair() {
		return r1 == r2;
	}

	@Override
	public boolean contains(final ElementRange r) {
		if ((r1 == r.getRank1() && r2 == r.getRank2())
				|| (r1 == r.getRank2() && r2 == r.getRank1())) {
			return r.isSuited() == this.isSuited();
		} else {
			return false;
		}
	}

	private static final int NUMBER_OF_EQUAL_PAIRS = 6;
	private static final int NUMBER_OF_EQUAL_SUITED = 4;
	private static final int NUMBER_OF_EQUAL_OFFSUITS = 12;

	@Override
	public int size() {
		return isSuited() ? NUMBER_OF_EQUAL_SUITED
				: isPair() ? NUMBER_OF_EQUAL_PAIRS
						: NUMBER_OF_EQUAL_OFFSUITS;
	}

	public Rank getRank1() {
		return r1;
	}

	public Rank getRank2() {
		return r2;
	}

}
