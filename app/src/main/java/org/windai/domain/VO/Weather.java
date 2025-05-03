package org.windai.domain.vo;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Weather {
  
  private final WeatherDescriptor descriptor;
  private final List<WeatherPhenomenon> phenomena;
  private final WeatherInensity intensity;
  
  @Builder
  public Weather(WeatherInensity intensity, WeatherDescriptor descriptor, List<WeatherPhenomenon> phenomena) {
    this.intensity = intensity;
    this.descriptor = descriptor;
    this.phenomena = List.copyOf(phenomena);
  }

}
