package org.windai.domain.specification;

import java.util.Optional;

import org.windai.domain.exception.GenericSpecificationExeception;
import org.windai.domain.specification.shared.AbstractSpecification;
import org.windai.domain.vo.Cloud;

public class CloudAltitudeSpec extends AbstractSpecification<Cloud> {

  @Override
  public boolean isSatisfiedBy(Cloud cloud) {
    Optional<Integer> altitude = cloud.getAltitudeOptional();
    if (altitude.isPresent()) {
      return altitude.get() < 100_000;
    }
    return true;
  }

  @Override
  public void check(Cloud t) throws GenericSpecificationExeception {
    if (!isSatisfiedBy(t)) {
      throw new GenericSpecificationExeception("Cloud altitude can't be graeater than 100,000ft.");
    }
  }
  
}
