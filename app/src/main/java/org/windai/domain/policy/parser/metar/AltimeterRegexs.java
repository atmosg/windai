package org.windai.domain.policy.parser.metar;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.unit.PressureUnit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AltimeterRegexs {
  
  HECTO_PASCAL("hPa", getHectoPascalRegex()),
  INCH_OF_MERCURY("inHg", getInchOfMercuryRegex());

  private final String groupName;
  private final String regex;

  public String getRegex() {
    return regex;
  }

  public String getGroupName() {
    return groupName;
  }

  public static String fullPattern() {
    return String.format("(?:^|\\s)(%s)(?=(?:\\s|$))",
      Arrays.stream(AltimeterRegexs.values())
        .map(AltimeterRegexs::getRegex)
        .collect(Collectors.joining("|"))
      );
  }

  private static String getHectoPascalRegex() {
    return "Q(?<hPa>\\d{4})";
  }

  private static String getInchOfMercuryRegex() {
    return "A(?<inHg>\\d{4})";
  }

  public int toHectoPascal(String strValue) {
    switch (this) {
      case HECTO_PASCAL:
        return Integer.parseInt(strValue);
      case INCH_OF_MERCURY: {
        return (int) Math.round(PressureUnit.INHG.convertTo(Double.parseDouble(strValue)/100, PressureUnit.HPA));
      }
      default:
        throw new GenericPolicyException("Invalid altimeter type: " + this);
    }
  }

}
