package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.vo.Visibility;

import lombok.Getter;

@Getter
public class VisibilityRegexParser extends RegexReportParser {

  private static final String VISIBILITY_REGEX = VisibilityRegexes.fullPattern();

  private Visibility visibility;

  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, VISIBILITY_REGEX);

    if (!check(matcher)) {
      throw new GenericPolicyException("Visibility not found in report: " + rawText);
    }

    int vis = -1;
    for (VisibilityRegexes type : VisibilityRegexes.values()) {
      String match = matcher.group(type.getGroupName());

      if (match == null || match.isEmpty())
        continue;

      vis = type.toMeters(match);
      break;
    }

    if (vis < 0) {
      throw new GenericPolicyException("Visibility not found in report: " + rawText);
    }

    visibility = Visibility.builder()
        .visibility(vis)
        .lengthUnit(LengthUnit.METERS)
        .build();
  }

}
