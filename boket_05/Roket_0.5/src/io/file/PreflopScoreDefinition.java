package io.file;

import ranges.atry.CountPlayers;
import ranges.preflop.GroupedRange;

public interface PreflopScoreDefinition {
  Double getScore(GroupedRange range, CountPlayers countPlayers);
}
