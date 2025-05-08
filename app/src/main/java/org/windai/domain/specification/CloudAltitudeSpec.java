package org.windai.domain.specification;

import org.windai.domain.exception.GenericSpecificationExeception;
import org.windai.domain.specification.shared.AbstractSpecification;
import org.windai.domain.vo.Cloud;

public class CloudAltitudeSpec extends AbstractSpecification<Cloud> {

  @Override
  public boolean isSatisfiedBy(Cloud cloud) {
    Integer altitude = cloud.getAltitudeOptional().get();
    return altitude < 100_000;
  }

  @Override
  public void check(Cloud t) throws GenericSpecificationExeception {
    if (!isSatisfiedBy(t)) {
      throw new GenericSpecificationExeception("Cloud altitude can't be graeater than 100,000ft.");
    }
  }
  
}
