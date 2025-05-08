package org.windai.domain.specification;

import org.windai.domain.exception.GenericSpecificationExeception;
import org.windai.domain.specification.shared.AbstractSpecification;
import org.windai.domain.vo.Cloud;

public class CloudCoverageSpec extends AbstractSpecification<Cloud> {
  
  @Override
  public boolean isSatisfiedBy(Cloud cloud) {
    boolean requires = cloud.getCoverage().requiresAltitude();
    boolean hasAltitude = cloud.getAltitudeOptional().isPresent();

    return requires ? hasAltitude : !hasAltitude;
  }

  @Override
  public void check(Cloud cloud) throws GenericSpecificationExeception {
    if (!isSatisfiedBy(cloud)) {
      handleViolation(cloud);
    }
  }

  private void handleViolation(Cloud cloud) {
    boolean requires = cloud.getCoverage().requiresAltitude();
    boolean hasAltitude = cloud.getAltitudeOptional().isPresent();
    
    if (requires && !hasAltitude) {
      throw new GenericSpecificationExeception(cloud.getCoverage() + " requires altitude.");
    }

    if (!requires && hasAltitude) {
      throw new GenericSpecificationExeception(cloud.getCoverage() + " has no fixed altitude.");
    }
  }

}
