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

  private static StaticCatRec staticCatRec = StaticCatRec.getInstance();

  private ICatRecBase createStatic(final List<Card> cards) {
    staticCatRec.setCards(cards);
    return staticCatRec;
  }

  @SuppressWarnings("null")
  private ICatRecBase createLive(List<Card> cards) {
    return new CatRec(cards.subList(0, 2), cards.subList(2, cards.size()));
  }

  private List<Card> generate7Cards(Random rnd) {
    List<Card> cards = new ArrayList<>();

    List<Card> all = new ArrayList<>(Card.getAllCards());

    for (int i = 0; i < 7; i++) {
      int index = rnd.nextInt(all.size());
      cards.add(all.remove(index));
    }
    return cards;
  }

  @Test
  public void test() {
    List<Card> cards;
    IResult expected, got;
    Random rnd = new Random();

    for (int i = 0; i < 10000; i++) {
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
