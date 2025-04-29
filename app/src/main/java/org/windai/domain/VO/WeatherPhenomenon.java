package org.windai.domain.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WeatherPhenomenon {
  DZ("Drizzle"),
  RA("Rain"),
  SN("Snow"),
  SG("Snow Grains"),
  IC("Ice Crystals"),
  PL("Ice Pellets"),
  GR("Hail"),
  GS("Small Hail/Snow Pellets"),
  UP("Unknown Precipitation"),
  BR("Mist"),
  FG("Fog"),
  FU("Smoke"),
  VA("Volcanic Ash"),
  DU("Widespread Dust"),
  SA("Sand"),
  HZ("Haze"),
  PY("Spray"),
  PO("Dust/Sand Whirls"),
  SQ("Squall"),
  FC("Funnel Cloud / Tornado"),
  SS("Sandstorm"),
  DS("Duststorm"),
  WS("Wind Shear");

  private final String phenomenon;

  public String getPhenomenon() {
    return phenomenon;
  }
  
}
