package org.windai.domain.service;

import java.util.List;

import org.windai.domain.policy.crosswind.MinimumCrosswindPolicyType;
import org.windai.domain.policy.crosswind.MultiRunwayMinimumCrosswindPolicy;
import org.windai.domain.policy.crosswind.SingleRunwayMinimumCrosswindPolicy;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.vo.Runway;
import org.windai.domain.vo.Wind;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WindOperation {

  private double runwayLengthThreshold = 8000.0;
  private LengthUnit lengthUnit = LengthUnit.METERS;

  public int calculateMinimumCrosswind(Wind wind, List<Runway> runways, MinimumCrosswindPolicyType policyType) {
    switch (policyType) {
      case MULTI:
        return new MultiRunwayMinimumCrosswindPolicy(runwayLengthThreshold, LengthUnit.FEET).calculate(wind, runways);
      case SINGLE:
        return new SingleRunwayMinimumCrosswindPolicy().calculate(wind, runways);
      default:
        throw new IllegalArgumentException("Invalid policy type: " + policyType);
    }
  }

}
