package org.windai.domain.vo;

import org.windai.domain.specification.RunwayHeadingSpec;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class RunwayEnd {
  
  private final Integer heading;
  private final RunwaySide side;
  private final boolean available;

  private static final RunwayHeadingSpec headingSpec = new RunwayHeadingSpec();

  @Builder
  public RunwayEnd(Integer heading, RunwaySide side, boolean available) {
    headingSpec.check(heading);

    this.heading = heading;
    this.side = side;
    this.available = available;
  }

  public String getDesignator() {
    return String.format("%02d%s", heading, side);
  }

  public String getNumberOnly() {
    return String.format("%02d", heading);
  }

  public boolean isAvailable() {
    return available;
  }

}
