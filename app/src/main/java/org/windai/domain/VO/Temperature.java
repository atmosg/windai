package org.windai.domain.vo;

import org.windai.domain.unit.TemperatureUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Temperature {
  
  private final int value;
  private final TemperatureUnit unit;

}
