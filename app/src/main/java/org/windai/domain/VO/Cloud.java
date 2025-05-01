package org.windai.domain.vo;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Cloud {
  
  private final Integer altitude;
  private final CloudCoverage coverage;
  private final CloudType type;

  private Cloud(Integer altitude, CloudCoverage coverage, CloudType type) {
    this.altitude = altitude;
    this.coverage = coverage;
    this.type = type;
  }

  public static Cloud withoutAltitude(CloudCoverage coverage, CloudType type) {
    if (coverage.requiresAltitude()) {
      throw new IllegalStateException(coverage + " requires altitude.");
    }
    return new Cloud(null, coverage, type);
  }

  public static Cloud withAltitude(Integer altitude, CloudCoverage coverage, CloudType type) {
    if (!coverage.requiresAltitude()) {
      throw new IllegalStateException(coverage + " has no fixed altitude.");
    }
    return new Cloud(altitude, coverage, type);
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
