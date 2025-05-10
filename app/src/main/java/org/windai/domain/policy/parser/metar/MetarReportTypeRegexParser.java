package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.MetarReportType;

import lombok.Getter;

@Getter
public class MetarReportTypeRegexParser extends RegexReportParser {

  private static final String REPORT_TYPE_REGEX = MetarReportTypeRegexes.fullPattern();
   
  private MetarReportType reportType = MetarReportType.METAR;
    
  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, REPORT_TYPE_REGEX);
    
    if (!check(matcher)) return;
    
    String type = matcher.group(1);

    reportType = MetarReportType.valueOf(type);
  }
  
}
