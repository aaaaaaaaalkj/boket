package io.file;

import org.eclipse.jdt.annotation.Nullable;

import ranges.atry.CountPlayers;
import ranges.preflop.GroupedRange;

public interface PreflopScoreDefinition {
  @Nullable
  Double getScore(GroupedRange range, CountPlayers countPlayers);
}
