package ranges.atry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class RangeCol<K, V> {
  private final Map<K, List<V>> lists;

  private final Map<K, ? extends Function<V, Double>> scoreFunctions;

  // private final double centerOfMass;

  public RangeCol(Iterable<V> source,
      Map<K, ? extends Function<V, Double>> scoreFunctions) {
    this.scoreFunctions = scoreFunctions;
    this.lists = new HashMap<>();

    for (K count : scoreFunctions.keySet()) {
      List<V> list = new ArrayList<>();
      lists.put(count, list);

      for (V item : source) {
        list.add(item);
      }
      Function<V, Double> func = scoreFunctions.get(count);
      list.sort((a, b) -> {
        double diff = func.apply(a) - func.apply(b);
        return diff > 0 ? 1 : diff < 0 ? -1 : 0;
      });
    }

  }

  @SuppressWarnings("null")
  public V getRandom(Random rnd, K count, ScoreInterval interval) {
    if (!lists.keySet().contains(count)) {
      throw new IllegalArgumentException(count + " players is not defined");
    }

    double minScore = interval.getMinValue();

    List<V> list = lists.get(count);
    Function<V, Double> scoreF = scoreFunctions.get(count);

    // linear estimate of the index for score
    int index = (int) (minScore * (list.size() - 1));

    // search the zone of negative indexes
    while (index > 0 && scoreF.apply(list.get(index)) > minScore) {
      index--;
    }
    // go up until the first positive index is found
    while (index < list.size() - 1 && scoreF.apply(list.get(index)) < minScore) {
      index++;
    }
    // choose a randomly selected item from positive zone
    V t = list.get(index + rnd.nextInt(list.size() - index));

    return t;
  }

}
