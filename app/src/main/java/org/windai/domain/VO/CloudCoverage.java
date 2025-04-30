package org.windai.domain.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CloudCoverage {

  SKC("Sky clear"),
  CLR("Clear"),
  FEW("Few"),
  SCT("Scattered"),
  BKN("Broken"),
  OVC("Overcast"),
  NSC("No significant cloud"),
  NCD("No cloud detected"),
  VV("Vertical visibility");

  private final String description;

  public String getDescription() {
    return description;
  }

  public boolean requiresAltitude() {
    return switch (this) {
      case FEW, SCT, BKN, OVC, VV -> true;
      default -> false;
    };
  }

}
