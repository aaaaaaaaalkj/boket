package ranges;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.google.common.base.Supplier;

public class ImmutableRange implements Range {
	private final Set<ElementRange> elements;

	private ImmutableRange(EnumSet<ElementRange> elements) {
		this.elements = elements;
	}

	private static @Nullable ImmutableRange FULL_RANGE;
	private static @Nullable ImmutableRange PAIRS;
	private static @Nullable ImmutableRange SUITED;
	private static @Nullable ImmutableRange OFF_SUIT;

	public static ImmutableRange fulRange() {
		FULL_RANGE = create(FULL_RANGE, () -> create(r -> true));
		return FULL_RANGE;
	}

	public static ImmutableRange pairs() {
		PAIRS = create(PAIRS, () -> create(ElementRange::isPair));
		return PAIRS;
	}

	public static ImmutableRange offSuit() {
		OFF_SUIT = create(OFF_SUIT, () -> create(ElementRange::isOffsuit));
		return OFF_SUIT;
	}

	public static ImmutableRange suited() {
		SUITED = create(SUITED, () -> create(ElementRange::isSuited));
		return SUITED;
	}

	public boolean contains(ElementRange r) {
		return elements.contains(r);
	}

	private static ImmutableRange create(@Nullable ImmutableRange defaultVar,
			Supplier<EnumSet<ElementRange>> supplier) {
		ImmutableRange res;
		if (null != defaultVar) {
			res = defaultVar;
		} else {
			res = new ImmutableRange(supplier.get());
		}
		return res;
	}

	private static EnumSet<ElementRange> create(Predicate<ElementRange> pred) {
		@SuppressWarnings("null")
		@NonNull
		EnumSet<ElementRange> set = EnumSet.noneOf(ElementRange.class);

		for (ElementRange r : ElementRange.VALUES) {
			if (pred.test(r)) {
				set.add(r);
			}
		}
		return set;
	}

	@Override
	public int size() {
		return elements.size();
	}

}
