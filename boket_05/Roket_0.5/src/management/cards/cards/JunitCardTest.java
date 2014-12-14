package management.cards.cards;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public final class JunitCardTest {
  private static final int NUM_CARDS = 52;

  private JunitCardTest() {
    // do not instantiate
  }

  @Test
  public static void test() {
    List<Card> cards = Card.getAllCards();
    assertTrue(cards.size() == NUM_CARDS);

    for (Card c : cards) {
      Card c2 = Card.instance(c.getRank(), c.getSuit());
      assertTrue(c == c2);
    }
  }

}
