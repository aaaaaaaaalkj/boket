package ranges;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.google.common.base.Supplier;

public final class ImmutableRange implements Range {
  private final Set<ElementRange> elements;

  private ImmutableRange(final EnumSet<ElementRange> elements) {
    this.elements = elements;
  }

  @Nullable
  private static ImmutableRange fullRange;
  @Nullable
  private static ImmutableRange pairs;
  @Nullable
  private static ImmutableRange suited;
  @Nullable
  private static ImmutableRange offSuit;

  public static ImmutableRange fulRange() {
    fullRange = create(fullRange, () -> create(r -> true));
    return fullRange;
  }

  public static ImmutableRange pairs() {
    pairs = create(pairs, () -> create(ElementRange::isPair));
    return pairs;
  }

  public static ImmutableRange offSuit() {
    offSuit = create(offSuit, () -> create(ElementRange::isOffsuit));
    return offSuit;
  }

  public static ImmutableRange suited() {
    suited = create(suited, () -> create(ElementRange::isSuited));
    return suited;
  }

  public boolean contains(final ElementRange r) {
    return elements.contains(r);
  }

  private static ImmutableRange create(@Nullable final ImmutableRange defaultVar,
      final Supplier<EnumSet<ElementRange>> supplier) {
    ImmutableRange res;
    if (null != defaultVar) {
      res = defaultVar;
    } else {
      res = new ImmutableRange(supplier.get());
    }
    return res;
  }

  private static EnumSet<ElementRange> create(final Predicate<ElementRange> pred) {
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
