package org.windai.domain.policy.parser.metar;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RemarkRegexs {
  
  RMK("rmk", getRemarkRegex());

  private final String groupName;
  private final String regex;

  public String getGroupName() {
    return groupName;
  }

  public String getRegex() {
    return regex;
  }

  public static String fullPattern() {
    return String.format("(?:^|\\s)(%s)(?=(?:\\s|$))", getRemarkRegex());
  }

  private static String getRemarkRegex() {
    return "RMK(?<rmk>[\\s\\S]+)";
  }

}
