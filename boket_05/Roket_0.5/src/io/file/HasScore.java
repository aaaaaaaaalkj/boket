package io.file;

import ranges.ElementRange;

public interface HasScore {
  double getScore(ElementRange range, int countPlayers);
}
