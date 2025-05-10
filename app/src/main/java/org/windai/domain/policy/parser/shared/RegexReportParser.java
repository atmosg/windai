package org.windai.domain.policy.parser.shared;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexReportParser implements ReportParser {

  protected Matcher getMatcher(String rawText, String regex) {
    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    return pattern.matcher(rawText);
  }

  protected boolean check(Matcher matcher) {
    return matcher.find();
  }

  protected boolean isMatchedAsRemarks(String rawText, String regex) {
    Matcher matcher = getMatcher(rawText, regex);

    if (!check(matcher)) return false;
    int targetIndex = matcher.start();
    
    String remarksRegex = "RMK|REMARKS|REMARK|RMK:";
    Matcher remarkMatcher = getMatcher(rawText, remarksRegex);
    
    if (!remarkMatcher.find()) return false;
    int remarkIndex = remarkMatcher.start();
    
    return remarkIndex < targetIndex;
  }

}
