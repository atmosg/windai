package org.windai.common.specification;

import org.windai.domain.VO.Airport;

public class SoleRunwaySpec implements Specification<Airport> {

  public void check(Airport airport) {
    if (!isSatisfiedBy(airport))
      throw new IllegalArgumentException("Airport must have exactly one runway.");
  }

  @Override
  public boolean isSatisfiedBy(Airport airport) {
    return airport.getRunwayCount() == 1;
  }

  
  
}
