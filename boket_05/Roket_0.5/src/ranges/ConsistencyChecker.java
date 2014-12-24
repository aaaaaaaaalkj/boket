package ranges;

import java.util.List;

import management.cards.cards.Card;

public class ConsistencyChecker {

  private ConsistencyChecker() {
  }

  public static boolean isConsistent(ElementRange hand,
      List<ElementRange> hands,
      List<Card> communityCards) {
    boolean res = true;
    for (Card c : communityCards) {
      res &= hand.getCards().get(0) != c;
      res &= hand.getCards().get(1) != c;
    }
    for (int player = 0; player < hands.size(); player++) {
      res &= hands.get(player).getCards().get(0) != hand.getCards().get(0);
      res &= hands.get(player).getCards().get(0) != hand.getCards().get(1);
      res &= hands.get(player).getCards().get(1) != hand.getCards().get(0);
      res &= hands.get(player).getCards().get(1) != hand.getCards().get(1);

    }
    return res;
  }

  public static boolean areConsistent(List<ElementRange> hands,
      List<Card> communityCards) {
    boolean res = true;

    for (int player = 0; player < hands.size(); player++) {
      for (Card c : communityCards) {
        res &= hands.get(player).getCards().get(0) != c;
        res &= hands.get(player).getCards().get(1) != c;
      }
      for (int player2 = player + 1; player2 < hands.size(); player2++) {

        res &= hands.get(player).getCards().get(0) !=
            hands.get(player2).getCards().get(0);

        res &= hands.get(player).getCards().get(0) !=
            hands.get(player2).getCards().get(1);

        res &= hands.get(player).getCards().get(1) !=
            hands.get(player2).getCards().get(0);

        res &= hands.get(player).getCards().get(1) !=
            hands.get(player2).getCards().get(1);
      }

    }
    return res;
  }
}
