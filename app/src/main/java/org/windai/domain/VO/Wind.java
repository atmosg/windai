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
    int crosswind = (int) Math.round(speed * Math.abs(Math.sin(radians)));
    int crosswindGusts = (int) Math.round(gusts * Math.abs(Math.sin(radians)));

    return Wind.builder()
        .speed(crosswind)
        .direction(this.direction)
        .gusts(crosswindGusts)
        .speedUnit(this.speedUnit)
        .build();
  }

}
