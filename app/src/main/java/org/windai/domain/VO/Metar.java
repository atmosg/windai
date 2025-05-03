package org.windai.domain.vo;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Metar {

  private final String rawText;
  
  // required fields (ICAO Annex 3)
  private final String stationId;
  private final MetarReportType reportType;
  private final ZonedDateTime observationTime;
  private final Wind wind;
  private final Visibility visibility;
  private final TemperaturePair temperaturePair;
  private final Pressure altimeter;
  
  // optional fields (ICAO Annex 3)
  private final WeatherGroup weather;
  private final CloudGroup cloud;
  private final String remarks;


}
