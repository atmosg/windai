package org.windai.domain.policy.crosswind;

import java.util.List;

import org.windai.domain.vo.Runway;
import org.windai.domain.vo.Wind;

public interface MinimumCrosswindPolicy {

  int calculate(Wind wind, List<Runway> runways);

  default int getMaxCrosswind(Wind crosswind) {
    int speed = crosswind.getSpeed();
    int gusts = crosswind.getGusts();
    return gusts > speed ? gusts : speed;
  }
  
}
