package org.windai.domain.unit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemperatureUnit {
  
  CELSIUS(1.0),
  FAHRENHEIT(1.8);

  private final double toCelsiusFactor;

  public double convertTo(double value, TemperatureUnit targetUnit) {
    if (this == targetUnit) return value;

    double valueInCelsius = (value - 32) / toCelsiusFactor;
    return valueInCelsius * targetUnit.toCelsiusFactor + 32;
  }

}
