package org.windai.domain.VO;

import org.windai.common.unit.SpeedUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Wind {
  
  private final int speed;
  private final int direction;
  private final int gusts;
  private final SpeedUnit speedUnit;

  public Wind calculateCrosswind(int runwayHeading) {
    double radians = Math.toRadians(direction - 10*runwayHeading);
    double crosswind = Math.round(speed * Math.abs(Math.sin(radians)));
    double crosswindGusts = Math.round(gusts * Math.abs(Math.sin(radians)));

    return Wind.builder()
        .direction(this.direction)
        .speed((int) crosswind)
        .gusts((int) crosswindGusts)
        .speedUnit(this.speedUnit)
        .build();
  }

}
