package ranges.simulation;

import io.screen.RawSituation;

public interface Simulation {

  SimSituation prepareSituation(RawSituation raw);

  double /* odds */simulate(SimSituation situation);
}
