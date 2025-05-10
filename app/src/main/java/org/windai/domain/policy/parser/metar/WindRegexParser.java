package org.windai.domain.policy.parser.metar;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.unit.SpeedUnit;
import org.windai.domain.vo.Wind;
import org.windai.domain.vo.WindDirection;

public class WindRegexParser extends RegexReportParser<Wind> {
    
  private static final String WIND_REGEX = WindRegexes.fullPattern();
  
  @Override
  public Wind parse(String rawText) {
    Matcher matcher = getMatcher(rawText, WIND_REGEX);

    if (!check(matcher)) {
      throw new GenericPolicyException("Wind not found in report:  " + rawText);
    }

    String windDirection = matcher.group(WindRegexes.DIRECTION.getGroupName());
    String windSpeed = matcher.group(WindRegexes.SPEED.getGroupName());
    String windGusts = matcher.group(WindRegexes.GUSTS.getGroupName());
    String windUnit = matcher.group(WindRegexes.UNIT.getGroupName());

    WindDirection direction = windDirection.equals("VRB") 
      ? WindDirection.variable() 
      : WindDirection.fixed(Integer.valueOf(windDirection));
      
    int windSpeedValue = Integer.parseInt(windSpeed);
    int windGustsValue = windGusts != null ? Integer.parseInt(windGusts) : 0;
    SpeedUnit speedUnit = windUnit.equals("KT") ? SpeedUnit.KT : SpeedUnit.MPS;

    return Wind.builder()
        .direction(direction)
        .speed(windSpeedValue)
        .gusts(windGustsValue)
        .speedUnit(speedUnit)
        .build();
  }

}
