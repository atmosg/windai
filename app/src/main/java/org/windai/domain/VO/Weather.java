package org.windai.domain.vo;

import java.util.Optional;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Weather {
  
  private final WeatherDescriptor descriptor;
  private final WeatherPhenomenon phenomenon;
  private final WeatherInensity intensity;
  
  public Optional<WeatherDescriptor> getDescriptor() {
    return Optional.ofNullable(descriptor);
  }

}
