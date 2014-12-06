package management.cards.catrecnew;

import java.util.List;

import tools.Tools;

public enum Cathegory {
  HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;

  @SuppressWarnings("null")
  public static final List<Cathegory> VALUES = Tools.asList(values());

  public static Cathegory getCathegory(final int index) {
    return VALUES.get(index);
  }

  public static int getCount() {
    return VALUES.size();
  }



  public void print() {
    System.out.println(this);
  }
}
