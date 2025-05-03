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
public class Visibility {

  private final int visibility;
  private final LengthUnit lengthUnit;
  
}
