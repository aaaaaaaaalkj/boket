package management.cards.evaluator;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import management.cards.catrecnew.CatRec;
import management.cards.catrecnew.ICatRecBase;
import management.cards.catrecnew.IResult;

import org.junit.Test;

public class JUnitTestEvaluator {
  private static final int NUM_SELECTION_CARDS = 7;
  private static final int NUM_TESTS = 10000;

  private static StaticCatRec staticCatRec = StaticCatRec.getInstance();

  private ICatRecBase createStatic(final List<Card> cards) {
    staticCatRec.setCards(cards);
    return staticCatRec;
  }

  @SuppressWarnings("null")
  private ICatRecBase createLive(final List<Card> cards) {
    return new CatRec(cards.subList(0, 2), cards.subList(2, cards.size()));
  }

  private List<Card> generate7Cards(final Random rnd) {
    List<Card> cards = new ArrayList<>();

    List<Card> all = new ArrayList<>(Card.getAllCards());

    for (int i = 0; i < NUM_SELECTION_CARDS; i++) {
      int index = rnd.nextInt(all.size());
      cards.add(all.remove(index));
    }
    return cards;
  }

  @Test
  public final void test() {
    List<Card> cards;
    IResult expected, got;
    Random rnd = new Random();

    for (int i = 0; i < NUM_TESTS; i++) {
      cards = generate7Cards(rnd);

      expected = createLive(cards).check();
      got = createStatic(cards).check();

      assertTrue(
          "expected " + expected
              + " but got " + got
              + " cards: " + cards,
          got.equals(expected));
    }

  }
}
