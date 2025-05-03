package org.windai.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Cloud {
  
  private final CloudCoverage coverage;
  private final Integer altitude;
  private final CloudType type;

  private Cloud(CloudCoverage coverage, Integer altitude, CloudType type) {
    this.coverage = coverage;
    this.altitude = altitude;
    this.type = type;
  }

  public static Cloud withoutAltitude(CloudCoverage coverage, CloudType type) {
    if (coverage.requiresAltitude()) {
      throw new IllegalStateException(coverage + " requires altitude.");
    }
    return new Cloud(coverage, null, type);
  }

  public static Cloud withAltitude(CloudCoverage coverage, Integer altitude, CloudType type) {
    if (!coverage.requiresAltitude()) {
      throw new IllegalStateException(coverage + " has no fixed altitude.");
    }
    return new Cloud(coverage, altitude, type);
  }

  public int getAltitudeOrThrow() {
    if (!coverage.requiresAltitude()) {
      throw new IllegalStateException(coverage + " has no fixed altitude.");
    }
    return altitude;
  }

  public CloudCoverage getCoverage() {
    return coverage;
  }

  public CloudType getType() {
    return type;
  }

  public boolean hasCloudType() {
    return type != CloudType.NONE;
  }

}
