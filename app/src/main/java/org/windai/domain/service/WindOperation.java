package org.windai.domain.service;

import java.util.List;

import org.windai.common.unit.LengthUnit;
import org.windai.domain.VO.Runway;
import org.windai.domain.VO.Wind;
import org.windai.domain.policy.MinimumCrosswindPolicyType;
import org.windai.domain.policy.MultiRunwayMinimumCrosswindPolicy;
import org.windai.domain.policy.SoleRunwayMinimumCrosswindPolicy;

import lombok.Getter;

@Getter
public class WindOperation {
  
  public int calculateMinimumCrosswind(Wind wind, List<Runway> runways, MinimumCrosswindPolicyType policyType) {
    switch (policyType) {
      case MULTI:
        return new MultiRunwayMinimumCrosswindPolicy(8000.0, LengthUnit.FEET).calculate(wind, runways);
      case SOLE:
        return new SoleRunwayMinimumCrosswindPolicy().calculate(wind, runways);
      default:
        throw new IllegalArgumentException("Invalid policy type: " + policyType);
    }
  }

}
