package org.windai.domain.vo;

import org.windai.domain.unit.LengthUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Runway {
  
  private final RunwayEnd endA;
  private final RunwayEnd endB;
  private final double length;
  private final LengthUnit lengthUnit;

  public double getLengthIn(LengthUnit targetUnit) {
    return lengthUnit.convertTo(length, targetUnit);
  }

  public String getName() {
    return endA.getDesignator() + "/" + endB.getDesignator();
  }

  public String getNameWithoutSide() {
    return endA.getNumberOnly() + "/" + endB.getNumberOnly();
  }

}
