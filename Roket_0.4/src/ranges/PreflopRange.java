package ranges;

import static ranges.GroupedRange.*;

import java.util.ArrayList;
import java.util.List;

public class PreflopRange implements Range {
	List<GroupedPlusRange> subRanges;

	public PreflopRange() {
		subRanges = new ArrayList<>();
	}

	public PreflopRange(GroupedRange r) {
		this();
		add(r);
	}

	public static PreflopRange BASE_64 = new PreflopRange()
			.add(A2o, K2o, Q3o, J6o, T7o, _97o, _87o, _22)
			.add(_54, _65, _75, _85, _93, T2, J2, Q2, K2, A2);

	public static PreflopRange ACES = new PreflopRange(AA);
	public static PreflopRange KINGS = new PreflopRange(KK);
	public static PreflopRange QUEENS = new PreflopRange(QQ);
	public static PreflopRange JACKS = new PreflopRange(JJ);
	public static PreflopRange TENS = new PreflopRange(TT);
	public static PreflopRange NINES = new PreflopRange(_99);
	public static PreflopRange BASE_4 = new PreflopRange().add(_88, AQ);
	public static PreflopRange BASE_6 = new PreflopRange()
			.add(_88, AT, KQ, AKo);
	public static PreflopRange BASE_10 = new PreflopRange()
			.add(_77, A9, KT, QJ, AJo, KQo);
	public static PreflopRange BASE_15 = new PreflopRange()
			.add(_66, A5, K9, QT, JT, ATo, KJo);
	public static PreflopRange BASE_20 = new PreflopRange()
			.add(_55, A3, K7, Q9, JT, A8o, KTo, QJo);
	public static PreflopRange BASE_30 = new PreflopRange()
			.add(_44, A2, K4, Q7, J8, T8, A5o, K9o, Q9o, JTo);
	public static PreflopRange BASE_40 = new PreflopRange()
			.add(_33, A2, K2, Q5, J7, T7, _98, A2o, K7o, Q8o, J9o, T9o);
	public static PreflopRange BASE_50 = new PreflopRange()
			.add(_22, A2, K2, Q2, J3, T6, _96, _86, _76,
					A2o, K4o, Q7o, J8o, T8o);
	public static PreflopRange BASE_60 = new PreflopRange()
			.add(_22, A2, K2, Q2, J2, T3, _95, _85, _75, _65,
					A2o, K2o, Q4o, J6o, T7o, _98o);
	public static PreflopRange BASE_70 = new PreflopRange()
			.add(_22, A2, K2, Q2, J2, T2, _92, _84, _74, _64, _53,
					A2o, K2o, Q2o, J4o, T6o, _96o, _87o);

	public static PreflopRange BASE_80 = new PreflopRange()
			.add(_22, A2, K2, Q2, J2, T2, _92, _82, _72, _62, _52, _43,
					A2o, K2o, Q2o, J2o, T4o, _95o, _85o, _76o);
	public static PreflopRange BASE_90 = new PreflopRange()
			.add(_22, A2, K2, Q2, J2, T2, _92, _82, _72, _62, _52, _42, _32,
					A2o, K2o, Q2o, J2o, T2o, _93o, _84o, _74o, _64o, _54o);
	public static PreflopRange BASE_100 = new PreflopRange()
			.add(_22, A2, K2, Q2, J2, T2, _92, _82, _72, _62, _52, _42, _32,
					A2o, K2o, Q2o, J2o, T2o, _92o, _82o, _72o, _62o, _52o,
					_42o, _32o);

	public SimpleRange ungroup() {
		SimpleRange res = new SimpleRange();

		for (GroupedPlusRange r : this.subRanges) {
			res.addAll(r.ungroup());
		}
		return res;
	}

	@Override
	public boolean contains(ElementRange element) {
		for (GroupedPlusRange range : subRanges) {
			if (range.contains(element)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {
		int size = 0;
		for (GroupedPlusRange range : subRanges) {
			size += range.size();
		}
		return size;
	}

	public PreflopRange add(GroupedPlusRange range) {
		this.subRanges.add(range);
		return this;
	}

	public PreflopRange add(GroupedRange range) {
		this.subRanges.add(new GroupedPlusRange(range));
		return this;
	}

	public PreflopRange add(GroupedRange... range) {
		for (GroupedRange r : range) {
			if (r != null) {
				this.subRanges.add(new GroupedPlusRange(r));
			} else {
				throw new IllegalArgumentException(
						"No null's allowed as arguments");
			}
		}
		return this;
	}

}
