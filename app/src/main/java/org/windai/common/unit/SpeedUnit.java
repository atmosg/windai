package org.windai.common.unit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpeedUnit {
  MPS(1.0),
  KT(1.94384),
  KPH(3.6);

  private final double toMpsFactor;

  public double convertTo(double value, SpeedUnit targetUnit) {
    if (this == targetUnit) return value;

    double valueInMps = value / toMpsFactor;
    return valueInMps * targetUnit.toMpsFactor;
  }

}
