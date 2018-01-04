package ranges.atry;

import java.util.List;
import java.util.Random;

import ranges.ElementRange;

public interface BasicRange {
  List<ElementRange> getRandom(Random rnd, CountPlayers countPlayers);

}
