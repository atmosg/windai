package org.windai.domain.policy.parser.metar;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.windai.domain.vo.MetarReportType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MetarReportTypeRegexes {
  
  TYPE("type", getTypeRegex());

  private final String groupName;
  private final String regex;

  public static String fullPattern() {
    return String.format("(%s)", getTypeRegex());
  }

  public String getGroupName() {
    return groupName;
  }

  public String getRegex() {
    return regex;
  }


  private static String getTypeRegex() {
    return Arrays.stream(MetarReportType.values())
      .map(Enum::name)
      .collect(Collectors.joining("|", "(?<type>", ")"));
  }

}
