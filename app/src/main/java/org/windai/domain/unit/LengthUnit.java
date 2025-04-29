package org.windai.domain.unit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LengthUnit {
  METER(1.0),
  FEET(3.28084),
  MILE(0.000621371);

  private final double toMeterFactor;

  public double convertTo(double length, LengthUnit targetUnit) {
    if (this == targetUnit) return length;
    double lengthInMeters = length / this.toMeterFactor;
    
    return lengthInMeters * targetUnit.toMeterFactor;
  }
}
