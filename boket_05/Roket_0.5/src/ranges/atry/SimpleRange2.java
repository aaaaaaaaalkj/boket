package ranges.atry;

import io.file.PreflopScoreDefinition;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ranges.ElementRange;

public class SimpleRange2 implements BasicRange {
  private final Map<CountPlayers, RangeCol> items;

  @SuppressWarnings("null")
  private SimpleRange2(
      Iterable<ElementRange> source,
      Map<CountPlayers, ScoreFunc> scores,
      List<ScoreInterval> intervals) {
    items = new EnumMap<>(CountPlayers.class);

    for (CountPlayers count : scores.keySet()) {
      items.put(count, new RangeCol(source, scores.get(count), intervals));
    }
  }

  @SuppressWarnings("null")
  @Override
  public List<ElementRange> getRandom(
      Random rnd,
      CountPlayers countPlayers) {
    return items.get(countPlayers).getRandom(rnd)
        .subList(0, countPlayers.getCount());
  }

  public static SimpleRange2 preflop(PreflopScoreDefinition def,
      List<ScoreInterval> scores) {
    SimpleRange2.Builder builder = new SimpleRange2.Builder(ElementRange.VALUES);

    for (CountPlayers countPlayers : CountPlayers.VALUES) {
      builder.addScoreFunc(countPlayers, elementRange ->
          def.getScore(elementRange.grouped(), countPlayers));
    }
    builder.addScoreInterval(scores);
    return builder.build();
  }

  public static class Builder {
    Iterable<ElementRange> source;
    Map<CountPlayers, ScoreFunc> scores;
    List<ScoreInterval> intervals;

    @SuppressWarnings("null")
    public Builder(Iterable<ElementRange> source) {
      this.source = source;
      this.scores = new EnumMap<>(CountPlayers.class);
      this.intervals = new ArrayList<>();
    }

    public Builder addScoreFunc(CountPlayers count, ScoreFunc func) {
      scores.put(count, func);
      return this;
    }

    public Builder addScoreInterval(ScoreInterval interval) {
      intervals.add(interval);
      return this;
    }

    public Builder addScoreInterval(List<ScoreInterval> intervalsParam) {
      this.intervals.addAll(intervalsParam);
      return this;
    }

    public SimpleRange2 build() {
      if (scores.isEmpty()) {
        throw new IllegalStateException(
            "no scores defined for the SimpleRange2");
      }
      if (scores.size() != intervals.size()) {
        throw new IllegalStateException(
            "scores.size() must match intervals.size()");
      }
      return new SimpleRange2(source, scores, intervals);
    }
  }

}
