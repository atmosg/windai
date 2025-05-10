package org.windai.domain.policy.parser.metar;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ObservationTimeRegexParser extends RegexReportParser {

  private static final String TIME_REGEX = ObservationTimeRegexes.fullPattern();
  private final YearMonth yearMonth;
  
  private ZonedDateTime observationTime;

  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, TIME_REGEX);
    
    if (!check(matcher)) {
      throw new GenericPolicyException("Observation time not found in report:  " + rawText);
    }

    int day = Integer.parseInt(matcher.group(ObservationTimeRegexes.DAY.getGroupName()));
    int hour = Integer.parseInt(matcher.group(ObservationTimeRegexes.HOUR.getGroupName()));
    int minute = Integer.parseInt(matcher.group(ObservationTimeRegexes.MINUTE.getGroupName()));

    LocalDateTime local = LocalDateTime.of(
      yearMonth.getYear(),
      yearMonth.getMonthValue(),
      day,
      hour,
      minute
    );

    observationTime = ZonedDateTime.of(local, ZoneId.of("UTC"));    
  }
  
}
