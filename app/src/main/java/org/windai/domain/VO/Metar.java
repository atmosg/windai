package org.windai.domain.vo;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Metar {

  private final String rawText;
  
  private final String stationId;
  private final MetarReportType reportType;
  private final ZonedDateTime observationTime;
  
  private final Wind wind;
  private final Visibility visibility;
  private final List<Weather> weather;
  private final List<Cloud> cloud;
  private final TemperaturePair temperaturePair;
  private final Pressure altimeter;

  private final String remarks;

}
