package ranges.atry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ranges.ElementRange;

public class RangeCol {
  private final List<ElementRange> list;
  private final List<IndexInterval> intervals;

  public RangeCol(
      Iterable<ElementRange> source,
      ScoreFunc scoreFunction,
      List<ScoreInterval> intervals) {
    this.intervals = new ArrayList<>();
    this.list = new ArrayList<>();

    for (ElementRange item : source) {
      list.add(item);
    }
    list.sort((a, b) -> {
      double diff = scoreFunction.apply(a) - scoreFunction.apply(b);
      return diff > 0 ? 1 : diff < 0 ? -1 : 0;
    });
    for (int i = 0; i < intervals.size(); i++) {
      this.intervals.add(computeInterval(intervals.get(i), scoreFunction));
    }
  }

  /**
   * constructs an IndexInterval from a scoreInterval
   * 
   * @param score
   * @return
   */
  private IndexInterval computeInterval(ScoreInterval score,
      ScoreFunc scoreFunction) {
    double minScore = score.getMinValue();
    double maxScore = score.getMaxValue();

    int endIndex = 0;
    while (endIndex < list.size() - 1
        && scoreFunction.apply(list.get(endIndex)) < minScore) {
      endIndex++;
    }

    int startIndex = list.size() - 1;
    while (startIndex > 0
        && scoreFunction.apply(list.get(startIndex)) > maxScore) {
      startIndex--;
    }

    IndexInterval res = new IndexInterval(startIndex, endIndex);

    return res;

  }

  public List<ElementRange> getRandom(Random rnd) {
    List<ElementRange> res = new ArrayList<>();

    for (int player = 0; player < intervals.size(); player++) {
      IndexInterval interval = intervals.get(player);
      int startIndex = interval.getStartIndex();
      int endIndex = interval.getEndIndex();

      assert startIndex >= 0;
      assert endIndex < list.size();

      // choose a randomly selected item
      ElementRange t = list
          .get(startIndex + rnd.nextInt(endIndex - startIndex));

      res.add(t);
    }
    return res;
  }
}
