package org.windai.domain.policy;

import java.util.List;

import org.windai.domain.unit.LengthUnit;
import org.windai.domain.vo.Runway;
import org.windai.domain.vo.Wind;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiRunwayMinimumCrosswindPolicy implements MinimumCrosswindPolicy {

  private final double runwayLengthThreshold;
  private final LengthUnit lengthUnit;

  @Override
  public int calculate(Wind wind, List<Runway> runways) {
    if (runways.size() < 2) {
      throw new IllegalArgumentException("At least two runway is required.");
    }

    boolean flag = isEveryRunwayBelowThreshold(runways);
    
    int minCrosswind = Integer.MAX_VALUE;
    
    for (int i=0; i<runways.size(); i++) {
      Runway runway = runways.get(i);
      if (!flag && !isRunwayLongerThan(runway)) continue;
      
      Wind crosswind = wind.calculateCrosswind(runway.getHeading());
      int maxCrosswind = getMaxCrosswind(crosswind);
      minCrosswind = Math.min(minCrosswind, maxCrosswind);
    }
    
    return minCrosswind;
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
