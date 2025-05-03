package org.windai.domain.vo;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class WeatherGroup {
  
  private final List<Weather> weatherList;

  @Builder
  public WeatherGroup(List<Weather> weatherList) {
    this.weatherList = List.copyOf(weatherList);
  }

  int size() {
    return weatherList.size();
  }

}
