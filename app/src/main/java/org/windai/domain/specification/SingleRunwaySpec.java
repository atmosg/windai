package org.windai.domain.specification;

import org.windai.domain.entity.Airport;
import org.windai.domain.exception.GenericSpecificationExeception;
import org.windai.domain.specification.shared.AbstractSpecification;

public class SingleRunwaySpec extends AbstractSpecification<Airport> {

  public void check(Airport airport) throws GenericSpecificationExeception {
    if (!isSatisfiedBy(airport))
      throw new GenericSpecificationExeception("Airport must have exactly one runway.");
  }

  @Override
  public boolean isSatisfiedBy(Airport airport) {
    return airport.getRunwayCount() == 1;
  }

  
  
}
