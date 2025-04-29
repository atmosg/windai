package org.windai.domain.vo;

import org.windai.domain.unit.LengthUnit;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Visibility {

  private final int visibility;
  private final LengthUnit lengthUnit;
  
}
