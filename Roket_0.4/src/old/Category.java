package old;

import static managementcards.cards.Rank.Ace;
import static managementcards.cards.Rank.Four;
import static managementcards.cards.Rank.Jack;
import static managementcards.cards.Rank.King;
import static managementcards.cards.Rank.Queen;
import static old.Super_category.PAIR;

import java.util.ArrayList;
import java.util.List;

import managementcards.cards.Rank;

public class Category {
  private Super_category super_category;
  private List<Rank> list;

  public Category(final Super_category cat, final Rank c1, final Rank c2, final Rank c3,
      final Rank c4, final Rank c5) {
    super_category = cat;
    list = new ArrayList<>();
    list.add(c1);
    list.add(c2);
    list.add(c3);
    list.add(c4);
    list.add(c5);
  }

  public final Super_category getSuper_category() {
    return super_category;
  }

  public static final Category PAIR_OF_ACES = new Category(PAIR, Ace, Ace,
      King, Jack, Four);

  public static final Category PAIR_OF_KINGS = new Category(PAIR, King, King,
      Queen, Jack, Four);

}
