package org.windai.domain.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WeatherDescriptor {
  BC("Patches"),
  BL("Blowing"),
  DR("Drifting"),
  DL("Distant lightning"),
  FZ("Freezing"),
  MI("Shallow"),
  PR("Partial"),
  SH("Showers"),
  TS("Thunderstorm"),
  VC("in the Vicinity");

  private final String description;

  public String getDescription() {
    return description;
  }

}