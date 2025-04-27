package org.windai.domain.vo;

import org.windai.domain.unit.LengthUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Runway {
  
  private final int heading;
  private final double length;
  private final LengthUnit lengthUnit;
  
  public Runway(int heading, double length, LengthUnit lengthUnit) {
    if (heading < 0 || heading > 36) {
      throw new IllegalArgumentException("Heading must be between 0 and 36.");
    }

    this.length = length;
    this.heading = heading;
    this.lengthUnit = lengthUnit;
  }

  public double getLengthIn(LengthUnit targetUnit) {
    return lengthUnit.convertTo(length, targetUnit);
  }

}
