package org.windai.domain.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Cloud {
  
  private final int altitude;
  private final CloudCoverage coverage;
  private final CloudType type;

  public boolean hasCloudType() {
    return type != CloudType.NONE;
  }

}
