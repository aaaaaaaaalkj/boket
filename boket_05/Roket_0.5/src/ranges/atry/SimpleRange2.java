package ranges.atry;

import io.file.PreflopScoreDefinition;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import ranges.ElementRange;

public class SimpleRange2 implements BasicRange {
  private final RangeCol<CountPlayers, ElementRange> items;

  private SimpleRange2(
      Iterable<ElementRange> source,
      Map<CountPlayers, ScoreFunc> scores) {
    items = new RangeCol<CountPlayers, ElementRange>(source, scores);
  }

  @Override
  public ElementRange getRandom(
      Random rnd,
      CountPlayers countPlayers,
      ScoreInterval score) {
    return items.getRandom(rnd, countPlayers, score);
  }

  public static SimpleRange2 preflop(PreflopScoreDefinition def) {
    SimpleRange2.Builder builder = new SimpleRange2.Builder(ElementRange.VALUES);

    for (CountPlayers countPlayers : CountPlayers.VALUES) {
      builder.addScoreFunc(countPlayers, elementRange ->
          def.getScore(elementRange.grouped(), countPlayers));
    }
    return builder.build();
  }

  public static class Builder {
    Iterable<ElementRange> source;
    Map<CountPlayers, ScoreFunc> scores;

    @SuppressWarnings("null")
    public Builder(Iterable<ElementRange> source) {
      this.source = source;
      this.scores = new EnumMap<>(CountPlayers.class);
    }

    public Builder addScoreFunc(CountPlayers count, ScoreFunc func) {
      scores.put(count, func);
      return this;
    }

    public SimpleRange2 build() {
      if (scores.isEmpty()) {
        throw new IllegalStateException(
            "no scores defined for the SimpleRange2");
      }
      return new SimpleRange2(source, scores);
    }
  }

}
