package org.windai.common.specification;

import org.windai.domain.VO.Airport;

public class MultiRunwaySpec implements Specification<Airport> {

  public void check(Airport airport) {
    if (!isSatisfiedBy(airport))
      throw new IllegalArgumentException("Airport must have at least two runways.");
  }

  @Override
  public boolean isSatisfiedBy(Airport airport) {
    return airport.getRunwayCount() >= 2;
  }
  
}
