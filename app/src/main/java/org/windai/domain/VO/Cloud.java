package org.windai.domain.vo;

import java.util.Optional;

import org.windai.domain.exception.GenericSpecificationExeception;
import org.windai.domain.specification.CloudAltitudeSpec;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Cloud {
  
  @Getter
  private final CloudCoverage coverage;

  private final Integer altitude;

  @Getter
  private final CloudType type;

  private static final CloudAltitudeSpec altitudeSpec = new CloudAltitudeSpec();

  @Builder
  public Cloud(CloudCoverage coverage, Integer altitude, CloudType type) {
    this.coverage = coverage;
    this.altitude = altitude;
    this.type = type;

    altitudeSpec.check(this);
  }

  public Integer getAltitudeOrThrow() {
    if (!coverage.requiresAltitude()) {
      throw new GenericSpecificationExeception(coverage + " has no fixed altitude.");
    }
    return altitude;
  }

  public Optional<Integer> getAltitudeOptional() {
    return Optional.ofNullable(altitude);
  }

  public boolean hasCloudType() {
    return type != CloudType.NONE;
  }

}
