package org.windai.domain.policy.parser.metar;

import org.windai.domain.exception.GenericPolicyException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemperaturePairRegexs {
  
  TEMPERATURE("temperature", getTemperatureRegex()),
  DEW_POINT("dewPoint", getDewpointRegex());

  private final String groupName;
  private final String regex;

  public static String fullPattern() {
    return String.format("(?:^|\\s)(%s)\\/(%s)(?=(?:\\s|$))",
      getTemperatureRegex(),
      getDewpointRegex()
    );
  }

  public String getGroupName() {
    return groupName;
  }

  public String getRegex() {
    return regex;
  }

  private static String getTemperatureRegex() {
    return "(?<temperature>M?\\d{2})";
  }

  private static String getDewpointRegex() {
    return "(?<dewPoint>M?\\d{2})";
  }

  public int toCelsius(String strValue) {
    switch (this) {
      case TEMPERATURE:
      case DEW_POINT:
        return strValue.startsWith("M")
          ? -Integer.parseInt(strValue.substring(1))
          : Integer.parseInt(strValue);
      default:
        throw new GenericPolicyException("Invalid temperature type: " + this);
    }
  }

}
