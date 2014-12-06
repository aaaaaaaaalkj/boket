package strategy.conditions.postflop;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import tools.Tools;

public enum FlushDanger {
  NONE, // rainbow
  MODERATE, // flush-draw possible (2 cards of same suit)
  SIGNIFICANT, // flush possible (3 cards of same suit)
  HIGH, // four cards of the same suit in community-cards
  CERTAIN_FLUSH; // five of the same suit in community-cards

  @SuppressWarnings("null")
  public static final List<FlushDanger> VALUES = Tools.asList(values());

  public static int getCount() {
    return VALUES.size();
  }

  public static FlushDanger get(final int index) {
    return VALUES.get(index);
  }



  public static FlushDanger fromLong(final long countMaxCardsOfSameSuit) {
    int index = Math.min((int) countMaxCardsOfSameSuit - 1,
        VALUES.size() - 1);
    return get(index);
  }



  @Override
  public String toString() {
    @SuppressWarnings("null")
    @NonNull
    String res = super.toString();
    if (this == NONE) {
      res = "NO";
    }
    return res;
  }
}
