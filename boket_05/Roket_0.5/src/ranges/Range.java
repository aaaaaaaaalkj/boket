package ranges;

import java.util.Iterator;
import java.util.Random;

public interface Range extends Iterable<ElementRange> {
  boolean contains(ElementRange r);

  int size();

  ElementRange getRandom(Random rnd, double score);

  @Override
  Iterator<ElementRange> iterator();

}
