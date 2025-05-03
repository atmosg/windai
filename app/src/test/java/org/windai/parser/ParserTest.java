package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.windai.domain.policy.parser.metar.MetarReportTypeRegexParser;
import org.windai.domain.policy.parser.shared.ObservationTimeRegexParser;
import org.windai.domain.vo.MetarReportType;

public class ParserTest {
  
  @Test
  void 관측시간_파싱_성공() {
    String metar = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";
    
    ZonedDateTime result = new ObservationTimeRegexParser(YearMonth.of(2025, 5))
      .parse(metar);

    assertEquals(result, ZonedDateTime.of(
      LocalDateTime.of(2025,5,1,3,0),
      ZoneId.of("UTC")
    ));
  }

  @Test
  void 리포트타입_파싱_성공() {
    String rksi = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";
    String rkjk = "SPECI RKJK 010647Z 27011KT 1 3/4SM -RA BR OVC004 15/14 A2969 RMK AO2A VIS 1 3/4V3 RAB0555E25RAB42E43DZB43E46RAB46 CIG 004V006 CIG 003 RWY18 SLPNO $=";
    String rkjy = "METAR RKJY 010600Z 18017G28KT 150V220 3200 +TSRA BR FEW010CB BKN025 OVC060 16/16 Q1007 NOSIG RMK TS NW MOV NE OCNL LTGIC=";
    String khyi = "KHYI 010056Z AUTO 20004KT 10SM CLR 09/07 A2999 RMK AO2 SLP153 T00940072";
    
    MetarReportTypeRegexParser parser = new MetarReportTypeRegexParser();
      
    assertAll(
      () -> assertEquals(parser.parse(rksi), MetarReportType.METAR),
      () -> assertEquals(parser.parse(rkjk), MetarReportType.SPECI),
      () -> assertEquals(parser.parse(rkjy), MetarReportType.METAR),
      () -> assertEquals(parser.parse(khyi), MetarReportType.AUTO)
    );
  }
}
