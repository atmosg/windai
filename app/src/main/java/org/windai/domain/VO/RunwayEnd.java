package org.windai.domain.vo;

import org.windai.domain.specification.RunwayHeadingSpec;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class RunwayEnd {
  
  private final int heading;
  private final RunwaySide side;
  private final boolean available;

  private static final RunwayHeadingSpec HEADING_SPEC = new RunwayHeadingSpec();

  public RunwayEnd(int heading, RunwaySide side, boolean available) {
    HEADING_SPEC.check(heading);

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
