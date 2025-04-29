package org.windai.domain.vo;

import org.windai.domain.unit.SpeedUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Wind {
  
  private final WindDirection direction;
  private final int speed;
  private final int gusts;
  private final SpeedUnit speedUnit;

  public Wind calculateCrosswind(int runwayHeading) {
    double radians = Math.toRadians(direction.getDegreeOrThrow() - 10*runwayHeading);
    double crosswind = Math.round(speed * Math.abs(Math.sin(radians)));
    double crosswindGusts = Math.round(gusts * Math.abs(Math.sin(radians)));

    return Wind.builder()
        .speed((int) crosswind)
        .direction(this.direction)
        .gusts((int) crosswindGusts)
        .speedUnit(this.speedUnit)
        .build();
  }

}
