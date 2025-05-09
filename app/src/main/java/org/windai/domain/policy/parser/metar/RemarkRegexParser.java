package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.policy.parser.shared.RegexReportParser;

public class RemarkRegexParser extends RegexReportParser<String> {

  private static final String REMARK_REGEX = RemarkRegexs.fullPattern();

  @Override
  public String parse(String rawText) {
    Matcher matcher = getMatcher(rawText, REMARK_REGEX);

    if (!check(matcher)) {
      return "";
    }

    for (RemarkRegexs type: RemarkRegexs.values()) {
      String match = matcher.group(type.getGroupName());

      if (match == null || match.isEmpty()) {
        continue;
      }

      return match.trim();
    }

    return "";
  }
  
}
