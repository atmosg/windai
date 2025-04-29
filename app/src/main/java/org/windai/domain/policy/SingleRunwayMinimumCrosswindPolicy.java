package org.windai.domain.policy;

import java.util.List;
import java.util.stream.Stream;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.vo.Runway;
import org.windai.domain.vo.RunwayEnd;
import org.windai.domain.vo.Wind;

public class SingleRunwayMinimumCrosswindPolicy implements MinimumCrosswindPolicy {

  @Override
  public int calculate(Wind wind, List<Runway> runways) {
    if (runways.size() != 1) {
      throw new GenericPolicyException("Only one runway is allowed for this policy.");
    }

    return runways.stream()
      .flatMap(runway -> Stream.of(runway.getEndA(), runway.getEndB()))
      .filter(RunwayEnd::isAvailable)
      .mapToInt(end -> getMaxCrosswind(wind.calculateCrosswind(end.getHeading())))
      .min()
      .orElseThrow(() -> new GenericPolicyException("No available runway ends for crosswind calculation."));
  }
  
}
