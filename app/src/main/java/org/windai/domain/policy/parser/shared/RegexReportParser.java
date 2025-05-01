package org.windai.domain.policy.parser.shared;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexReportParser<T> implements ReportParser<T> {

  protected Matcher getMatcher(String rawText, String regex) {
    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    return pattern.matcher(rawText);
  }

  protected boolean check(Matcher matcher) {
    return matcher.find() && matcher.groupCount() > 0;
  }

}
