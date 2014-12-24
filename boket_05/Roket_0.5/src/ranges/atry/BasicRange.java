package ranges.atry;

import java.util.Random;

import ranges.ElementRange;

public interface BasicRange {
  ElementRange getRandom(Random rnd, CountPlayers countPlayers,
      ScoreInterval interval);

}
