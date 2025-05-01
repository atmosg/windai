package org.windai.domain.policy.crosswind;

import java.util.List;
import java.util.stream.Stream;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.vo.Runway;
import org.windai.domain.vo.RunwayEnd;
import org.windai.domain.vo.Wind;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiRunwayMinimumCrosswindPolicy implements MinimumCrosswindPolicy {

  private final double runwayLengthThreshold;
  private final LengthUnit lengthUnit;

  @Override
  public int calculate(Wind wind, List<Runway> runways) {
    if (runways.size() < 2) {
      throw new GenericPolicyException("At least two runway is required.");
    }

    boolean allBelowThreshold = isEveryRunwayBelowThreshold(runways);
    
    return runways.stream()
    .filter(runway -> allBelowThreshold || isRunwayLongerThan(runway))
    .flatMap(runway -> Stream.of(runway.getEndA(), runway.getEndB()))
    .filter(RunwayEnd::isAvailable)
    .mapToInt(end -> getMaxCrosswind(wind.calculateCrosswind(end.getHeading())))
    .min()
    .orElseThrow(() -> new GenericPolicyException("No available runway ends for crosswind calculation."));
  }

  private boolean isRunwayLongerThan(Runway runway) {
    return runway.getLengthIn(lengthUnit) > runwayLengthThreshold;
  }

  private boolean isEveryRunwayBelowThreshold(List<Runway> runways) {
    for (Runway runway: runways) {
      if (isRunwayLongerThan(runway)) return false;
    }
    return true;
  }

}
