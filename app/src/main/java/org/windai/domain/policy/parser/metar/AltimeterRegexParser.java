package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.unit.PressureUnit;
import org.windai.domain.vo.Pressure;

public class AltimeterRegexParser extends RegexReportParser<Pressure> {

  private static final String ALTIMETER_REGEX = AltimeterRegexs.fullPattern();

  @Override
  public Pressure parse(String rawText) {
    Matcher matcher = getMatcher(rawText, ALTIMETER_REGEX);
    if (!check(matcher)) {
      throw new GenericPolicyException("Altimeter not found in report: " + rawText);
    }

    int altimeter = -1;
    for (AltimeterRegexs type: AltimeterRegexs.values()) {
      String match = matcher.group(type.getGroupName());
      
      if (match == null || match.isEmpty())
        continue;

      altimeter = type.toHectoPascal(match);
      break;
    }

    if (altimeter < 0) {
      throw new GenericPolicyException("Altimeter not found in report: " + rawText);
    }
            
    return Pressure.builder()
        .value(altimeter)
        .pressureUnit(PressureUnit.HPA)
        .build();
    
  }
  
}
