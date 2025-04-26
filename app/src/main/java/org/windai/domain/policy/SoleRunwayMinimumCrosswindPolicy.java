package org.windai.domain.policy;

import java.util.List;

import org.windai.domain.VO.Runway;
import org.windai.domain.VO.Wind;

public class SoleRunwayMinimumCrosswindPolicy implements MinimumCrosswindPolicy {

  @Override
  public int calculate(Wind wind, List<Runway> runways) {
    if (runways.size() != 1) {
      throw new IllegalArgumentException("Only one runway is allowed for this policy.");
    }

    Runway runway = runways.get(0);
    Wind crosswind = wind.calculateCrosswind(runway.getHeading());
    return getMaxCrosswind(crosswind);
  }
  
}
