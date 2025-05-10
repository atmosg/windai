package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.windai.domain.policy.parser.metar.ObservationTimeRegexParser;

public class ObservationTimeParserTest {

  private ObservationTimeRegexParser parser = new ObservationTimeRegexParser(YearMonth.of(2025, 5));

  @Test
  void 관측시간_파싱_성공() {
    String rawText = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";
    
    parser.parse(rawText);
    ZonedDateTime actual = parser.getObservationTime();

    ZonedDateTime expected = ZonedDateTime.of(
      LocalDateTime.of(2025,5,1,3,0),
      ZoneId.of("UTC")
    );

    assertEquals(expected, actual);
  }

}
