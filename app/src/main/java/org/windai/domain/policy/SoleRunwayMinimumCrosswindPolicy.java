package org.windai.domain.policy;

import java.util.List;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.vo.Runway;
import org.windai.domain.vo.Wind;

public class SoleRunwayMinimumCrosswindPolicy implements MinimumCrosswindPolicy {

  @Override
  public int calculate(Wind wind, List<Runway> runways) {
    if (runways.size() != 1) {
      throw new GenericPolicyException("Only one runway is allowed for this policy.");
    }

    Runway runway = runways.get(0);
    Wind crosswind = wind.calculateCrosswind(runway.getHeading());
    return getMaxCrosswind(crosswind);
  }
  
}
