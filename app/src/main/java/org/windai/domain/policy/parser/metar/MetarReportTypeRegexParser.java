package org.windai.domain.policy.parser.metar;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.MetarReportType;

public class MetarReportTypeRegexParser extends RegexReportParser<MetarReportType> {

  private static final String REPORT_TYPE_REGEX = 
    Arrays.stream(MetarReportType.values())
      .map(Enum::name)
      .collect(Collectors.joining("|", "(", ")"));
    
  @Override
  public MetarReportType parse(String rawText) {
    Matcher matcher = getMatcher(rawText, REPORT_TYPE_REGEX);
    
    if (!check(matcher)) {
      return MetarReportType.METAR;
    }

    String reportType = matcher.group(1);

    return MetarReportType.valueOf(reportType);
  }
  
}
