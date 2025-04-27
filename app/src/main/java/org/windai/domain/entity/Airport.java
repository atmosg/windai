package org.windai.domain.entity;

import java.util.List;

import org.windai.domain.vo.Runway;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Airport {
  
  private String icao;
  private String iata;
  private List<Runway> runways;

  public Airport(String icao, String iata, List<Runway> runways) {
    if (icao.length() != 4) throw new IllegalArgumentException("ICAO code must be 4 characters long.");
    if (iata.length() != 3) throw new IllegalArgumentException("IATA code must be 3 characters long.");

    this.icao = icao.toLowerCase();
    this.iata = iata.toLowerCase();
    this.runways = runways;
  }
  
  public int getRunwayCount() {
    return runways.size();
  }

}
