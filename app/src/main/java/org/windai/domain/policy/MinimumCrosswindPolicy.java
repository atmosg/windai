package org.windai.domain.policy;

import java.util.List;

import org.windai.domain.VO.Runway;
import org.windai.domain.VO.Wind;

public interface MinimumCrosswindPolicy {

  int calculate(Wind wind, List<Runway> runways);

  default int getMaxCrosswind(Wind crosswind) {
    int speed = crosswind.getSpeed();
    int gusts = crosswind.getGusts();
    return gusts > speed ? gusts : speed;
  }
  
}
