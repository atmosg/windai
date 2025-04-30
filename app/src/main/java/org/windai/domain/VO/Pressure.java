package org.windai.domain.vo;

import org.windai.domain.unit.PressureUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Pressure {
  
  private final int value;
  private final PressureUnit pressureUnit;
  
}
