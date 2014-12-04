package old;

import static managementcards.cards.Rank.Ace;
import static managementcards.cards.Rank.Four;
import static managementcards.cards.Rank.Jack;
import static managementcards.cards.Rank.King;
import static managementcards.cards.Rank.Queen;
import static old.SuperCategory.PAIR;

import java.util.ArrayList;
import java.util.List;

import managementcards.cards.Rank;

public class Category {
  private SuperCategory superCategory;
  private List<Rank> list;

  public Category(final SuperCategory cat, final Rank c1, final Rank c2, final Rank c3,
      final Rank c4, final Rank c5) {
    superCategory = cat;
    list = new ArrayList<>();
    list.add(c1);
    list.add(c2);
    list.add(c3);
    list.add(c4);
    list.add(c5);
  }

  public final SuperCategory getSuperCategory() {
    return superCategory;
  }

  public static final Category PAIR_OF_ACES = new Category(PAIR, Ace, Ace,
      King, Jack, Four);

  public static final Category PAIR_OF_KINGS = new Category(PAIR, King, King,
      Queen, Jack, Four);

}
