package org.windai.domain.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CloudType {

  TCU("Towering Cumulus"),
  CB("Cumulonimbus"),
  NONE("None");

  private final String description;

  public String getDescription() {
    return description;
  }

  public boolean hasCumulonimbus() {
    return this == CB;
  }
  
  public boolean hasToweringCumulus() {
    return this == TCU;
  }

}












