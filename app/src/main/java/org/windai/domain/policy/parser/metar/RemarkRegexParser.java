package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.policy.parser.shared.RegexReportParser;

import lombok.Getter;

@Getter
public class RemarkRegexParser extends RegexReportParser {

  private static final String REMARK_REGEX = RemarkRegexes.fullPattern();

  private String remarks = "";

  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, REMARK_REGEX);

    if (!check(matcher)) return;

    for (RemarkRegexes type: RemarkRegexes.values()) {
      String match = matcher.group(type.getGroupName());

      if (match == null || match.isEmpty()) {
        continue;
      }

      remarks = match.trim();
    }
  }
  
}
