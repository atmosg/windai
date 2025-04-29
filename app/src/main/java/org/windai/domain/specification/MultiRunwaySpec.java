package org.windai.domain.specification;

import org.windai.domain.entity.Airport;
import org.windai.domain.exception.GenericSpecificationExeception;
import org.windai.domain.specification.shared.AbstractSpecification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiRunwaySpec extends AbstractSpecification<Airport> {

  public void check(Airport airport) throws GenericSpecificationExeception {
    if (!isSatisfiedBy(airport))
      throw new GenericSpecificationExeception("Airport must have at least two runways.");
  }

  @Override
  public boolean isSatisfiedBy(Airport airport) {
    return airport.getRunwayCount() >= 2;
  }
  
}
