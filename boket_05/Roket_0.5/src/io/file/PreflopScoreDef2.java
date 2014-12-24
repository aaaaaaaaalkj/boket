package io.file;

import java.util.EnumMap;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import ranges.atry.CountPlayers;
import ranges.preflop.GroupedRange;

public class PreflopScoreDef2 implements PreflopScoreDefinition {
  private final EnumMap<GroupedRange, EnumMap<CountPlayers, Double>> map;

  private PreflopScoreDef2(
      EnumMap<GroupedRange, EnumMap<CountPlayers, Double>> map) {
    this.map = map;
  }

  public static class Builder {
    EnumMap<GroupedRange, EnumMap<CountPlayers, Double>> map;

    @SuppressWarnings("null")
    Builder() {
      map = new EnumMap<>(GroupedRange.class);
    }

    @SuppressWarnings("null")
    public void add(GroupedRange range, List<Double> scores) {
      assert scores.size() == 9; // 2 to 10 players
      map.put(range, new EnumMap<>(CountPlayers.class));
      for (int index = 0; index < scores.size(); index++) {
        CountPlayers key = CountPlayers.fromIndex(index);
        map.get(range).put(key, scores.get(index));
      }
    }

    public PreflopScoreDef2 build() {
      return new PreflopScoreDef2(map);
    }
  }

  @Override
  @Nullable
  public Double getScore(GroupedRange range, CountPlayers countPlayers) {
    return map.get(range).get(countPlayers);
  }

}
