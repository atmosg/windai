package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.vo.Visibility;

public class VisibilityRegexParser extends RegexReportParser<Visibility> {

  private static final String VISIBILITY_REGEX = VisibilityRegexs.fullPattern();

  @Override
  public Visibility parse(String rawText) {
    Matcher matcher = getMatcher(rawText, VISIBILITY_REGEX);

    if (!check(matcher)) {
      throw new GenericPolicyException("Visibility not found in report: " + rawText);
    }

    int visibility = -1;
    for (VisibilityRegexs type : VisibilityRegexs.values()) {
      String match = matcher.group(type.getGroupName());

      if (match == null || match.isEmpty())
        continue;

      visibility = type.toMeters(match);
      break;
    }

    if (visibility < 0) {
      throw new GenericPolicyException("Visibility not found in report: " + rawText);
    }

    return Visibility.builder()
        .visibility(visibility)
        .lengthUnit(LengthUnit.METERS)
        .build();
  }

}
