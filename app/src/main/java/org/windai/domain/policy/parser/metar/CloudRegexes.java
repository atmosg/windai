package org.windai.domain.policy.parser.metar;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.windai.domain.vo.CloudCoverage;
import org.windai.domain.vo.CloudType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CloudRegexes {
  
  COVERAGE(getCoverageRegex()),
  TYPE(getTypeRegex()),
  ALTITUDE(getAltitudeRegex());  

  private final String regex;

  public String getRegex() {
    return regex;
  }
  
  public static String fullPattern() {
    return String.format("(%s)(%s)?(%s)?", 
      getCoverageRegex(),
      getAltitudeRegex(),
      getTypeRegex()
    );
  }

  private static String getCoverageRegex() {
    return Arrays.stream(CloudCoverage.values())
      .map(Enum::name)
      .collect(Collectors.joining("|"));
    }
    
  private static String getTypeRegex() {
    return Arrays.stream(CloudType.values())
    .map(Enum::name)
    .collect(Collectors.joining("|"));
  }

  private static String getAltitudeRegex() {
    return "\\d{2,3}";
  }



}
