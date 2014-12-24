package ranges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import management.cards.cards.Card;
import ranges.action.dta.MyDataStructure;

public class FlexRange implements Range {
  private final MyDataStructure<ElementRange> data;
  private final Map<String, Integer> markerMap;

  public FlexRange(Iterable<ElementRange> source) {
    List<ElementRange> list = new ArrayList<>();
    for (ElementRange e : source) {
      list.add(e);
    }
    data = new MyDataStructure<ElementRange>(list);
    markerMap = new HashMap<>();
  }

  @SuppressWarnings("null")
  public void setMarker(String marker) {
    markerMap.put(marker, data.currentSize());
  }

  public void blockCards(Card card) {
    Predicate<ElementRange> predicate = e ->
        e.getFirstCard() != card && e.getSecondCard() != card;

    data.nextPredicate(predicate);
  }

  public void blockHand(ElementRange hand) {
    Predicate<ElementRange> predicate = e ->
        e.getFirstCard() != hand.getFirstCard() &&
            e.getFirstCard() != hand.getSecondCard() &&
            e.getSecondCard() != hand.getFirstCard() &&
            e.getSecondCard() != hand.getSecondCard();
    data.nextPredicate(predicate);
  }

  public void blockCards(List<Card> cards) {
    Predicate<ElementRange> predicate = e -> {
      boolean res = true;
      for (Card c : cards) {
        res &= e.getFirstCard() != c;
        res &= e.getSecondCard() != c;
      }
      return res;
    };
    data.nextPredicate(predicate);
  }

  public void resetTo(String marker) {
    if (!markerMap.containsKey(marker)) {
      throw new IllegalArgumentException("Marker " + marker + " is unknown");
    }
    data.reset(markerMap.get(marker));
  }

  @Override
  public boolean contains(ElementRange r) {
    return data.contains(r);
  }

  @Override
  public int size() {
    return data.currentSize();
  }

  @Override
  public ElementRange getRandom(Random rnd, double score) {
    return data.getRandom(rnd);
  }

  @Override
  public Iterator<ElementRange> iterator() {
    return data.iterator();
  }

}
