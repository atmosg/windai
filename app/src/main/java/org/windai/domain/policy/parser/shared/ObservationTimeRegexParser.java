package org.windai.domain.policy.parser.shared;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObservationTimeRegexParser extends RegexReportParser<ZonedDateTime> {

  private static final String TIME_REGEX = "(\\d{2})(\\d{2})(\\d{2})Z";
  
  private final YearMonth yearMonth;

  @Override
  public ZonedDateTime parse(String rawText) {
    Matcher matcher = getMatcher(rawText, TIME_REGEX);
    
    if (!check(matcher)) {
      throw new GenericPolicyException("Observation time not found in report:  " + rawText);
    }

    int day = Integer.parseInt(matcher.group(1));
    int hour = Integer.parseInt(matcher.group(2));
    int minute = Integer.parseInt(matcher.group(3));

    LocalDateTime local = LocalDateTime.of(
      yearMonth.getYear(),
      yearMonth.getMonthValue(),
      day,
      hour,
      minute
    );

    return ZonedDateTime.of(local, ZoneId.of("UTC"));    
  }
  
}
