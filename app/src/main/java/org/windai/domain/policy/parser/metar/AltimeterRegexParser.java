package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.unit.PressureUnit;
import org.windai.domain.vo.Pressure;

import lombok.Getter;

@Getter
public class AltimeterRegexParser extends RegexReportParser {

  private static final String ALTIMETER_REGEX = AltimeterRegexs.fullPattern();

  private Pressure altimeter;

  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, ALTIMETER_REGEX);
    if (!check(matcher)) {
      throw new GenericPolicyException("Altimeter not found in report: " + rawText);
    }

    int pascal = -1;
    for (AltimeterRegexs type : AltimeterRegexs.values()) {
      String match = matcher.group(type.getGroupName());

      if (match == null || match.isEmpty())
        continue;

      pascal = type.toHectoPascal(match);
      break;
    }

    if (pascal < 0) {
      throw new GenericPolicyException("Altimeter not found in report: " + rawText);
    }

    altimeter = Pressure.builder()
        .value(pascal)
        .pressureUnit(PressureUnit.HPA)
        .build();

  }

}
