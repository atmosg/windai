package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.CloudGroupRegexParser;
import org.windai.domain.policy.parser.metar.MetarReportTypeRegexParser;
import org.windai.domain.policy.parser.metar.VisibilityRegexParser;
import org.windai.domain.policy.parser.metar.WindRegexParser;
import org.windai.domain.policy.parser.shared.ObservationTimeRegexParser;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.unit.SpeedUnit;
import org.windai.domain.vo.CloudCoverage;
import org.windai.domain.vo.CloudGroup;
import org.windai.domain.vo.CloudType;
import org.windai.domain.vo.MetarReportType;
import org.windai.domain.vo.Visibility;
import org.windai.domain.vo.Wind;
import org.windai.domain.vo.WindDirection;

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

  @Test
  void 바람_파싱성공() {
    String rksi = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";
    String rkjk = "SPECI RKJK 010647Z VRB11KT 1 3/4SM -RA BR OVC004 15/14 A2969 RMK AO2A VIS 1 3/4V3 RAB0555E25RAB42E43DZB43E46RAB46 CIG 004V006 CIG 003 RWY18 SLPNO $=";
    String rkjy = "METAR RKJY 010600Z 18017G28KT 150V220 3200 +TSRA BR FEW010CB BKN025 OVC060 16/16 Q1007 NOSIG RMK TS NW MOV NE OCNL LTGIC=";
    String khyi = "KHYI 010056Z AUTO 20004MPS 10SM CLR 09/07 A2999 RMK AO2 SLP153 T00940072";

    WindRegexParser parser = new WindRegexParser();
    assertAll(
      () -> assertEquals(parser.parse(rksi), Wind.builder()
        .direction(WindDirection.fixed(170))
        .speed(8)
        .gusts(0)
        .speedUnit(SpeedUnit.KT)
        .build()
      ),
      () -> assertEquals(parser.parse(rkjk), Wind.builder()
        .direction(WindDirection.variable())
        .speed(11)
        .gusts(0)
        .speedUnit(SpeedUnit.KT)
        .build()
      ),
      () -> assertEquals(parser.parse(rkjy), Wind.builder()
        .direction(WindDirection.fixed(180))
        .speed(17)
        .gusts(28)
        .speedUnit(SpeedUnit.KT)
        .build()
      ),
      () -> assertEquals(parser.parse(khyi), Wind.builder()
        .direction(WindDirection.fixed(200))
        .speed(4)
        .gusts(0)
        .speedUnit(SpeedUnit.MPS)
        .build()
      )
    );

    assertThrows(IllegalStateException.class, () -> {
      parser.parse(rkjk).getDirection().getDegreeOrThrow();
    });
    
  }

  @Test
  void 바람_파싱실패_바람정보_누락됨() {
    String metar = "RKSI 010300Z 4000 -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";
    
    WindRegexParser parser = new WindRegexParser();
    
    assertThrows(GenericPolicyException.class, () -> {
      parser.parse(metar);
    });
  }

  @Test
  void 시정_파싱실패_시정정보_누락됨() {
    String metar = "RKSI 010300Z 17008KT -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";
    
    VisibilityRegexParser parser = new VisibilityRegexParser();
    
    assertThrows(GenericPolicyException.class, () -> {
      parser.parse(metar);
    });
  }

  @Test
  void 시정_파싱성공() {
    String rksi = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";
    String rkjk = "SPECI RKJK 010647Z VRB11KT 1 3/4SM -RA BR OVC004 15/14 A2969 RMK AO2A VIS 1 3/4V3 RAB0555E25RAB42E43DZB43E46RAB46 CIG 004V006 CIG 003 RWY18 SLPNO $=";
    String rkjy = "METAR RKJY 010600Z 18017G28KT 150V220 3200 +TSRA BR FEW010CB BKN025 OVC060 16/16 Q1007 NOSIG RMK TS NW MOV NE OCNL LTGIC=";
    String khyi = "KHYI 010056Z AUTO 20004MPS 10SM CLR 09/07 A2999 RMK AO2 SLP153 T00940072";
    String metar1 = "RRRR CAVOK ";
    String metar2 = "RRRR P6SM ";
    
    VisibilityRegexParser parser = new VisibilityRegexParser();

    assertAll(
      () -> assertEquals(parser.parse(rksi), Visibility.builder()
        .visibility(4000)
        .lengthUnit(LengthUnit.METERS)
        .build()
      ),
      () -> assertEquals(parser.parse(rkjk), Visibility.builder()
        .visibility(2816)
        .lengthUnit(LengthUnit.METERS)
        .build()
      ),
      () -> assertEquals(parser.parse(rkjy), Visibility.builder()
        .visibility(3200)
        .lengthUnit(LengthUnit.METERS)
        .build()
      ),
      () -> assertEquals(parser.parse(khyi), Visibility.builder()
        .visibility(16093)
        .lengthUnit(LengthUnit.METERS)
        .build()
      ),
      () -> assertEquals(parser.parse(metar1), Visibility.builder()
        .visibility(9999)
        .lengthUnit(LengthUnit.METERS)
        .build()
      ),
      () -> assertEquals(parser.parse(metar2), Visibility.builder()
        .visibility(9999)
        .lengthUnit(LengthUnit.METERS)
        .build()
      )
    );
  }

  @Test
  void 고도가_없는_구름정보_파싱성공() {
    String rawText = "KHYI 010056Z AUTO 20004MPS 10SM CLR 09/07 A2999 RMK AO2 SLP153 T00940072";

    CloudGroupRegexParser parser = new CloudGroupRegexParser();
    CloudGroup cloudGroup = parser.parse(rawText);

    assertAll(
      () -> assertEquals(cloudGroup.size(), 1),
      () -> assertEquals(cloudGroup.getClouds().get(0).getCoverage(), CloudCoverage.CLR),
      () -> assertThrows(IllegalStateException.class, () -> 
        cloudGroup.getClouds().get(0).getAltitudeOrThrow()
      )
    );
  }

  @Test
  void 고도가_존재하는_구름정보_파싱성공() {
    String metar = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070CB 13/13 Q1007 NOSIG";

    CloudGroupRegexParser parser = new CloudGroupRegexParser();
    CloudGroup cloudGroup = parser.parse(metar);

    assertAll(
      () -> assertEquals(cloudGroup.size(), 3),
      () -> assertEquals(cloudGroup.getClouds().get(0).getCoverage(), CloudCoverage.SCT),      
      () -> assertEquals(cloudGroup.getClouds().get(0).getType(), CloudType.NONE),
      () -> assertEquals(cloudGroup.getClouds().get(0).getAltitudeOrThrow(), 600),
      () -> assertEquals(cloudGroup.getClouds().get(1).getCoverage(), CloudCoverage.BKN),      
      () -> assertEquals(cloudGroup.getClouds().get(1).getType(), CloudType.NONE),
      () -> assertEquals(cloudGroup.getClouds().get(1).getAltitudeOrThrow(), 2500),
      () -> assertEquals(cloudGroup.getClouds().get(2).getCoverage(), CloudCoverage.OVC),      
      () -> assertEquals(cloudGroup.getClouds().get(2).getType(), CloudType.CB),
      () -> assertEquals(cloudGroup.getClouds().get(2).getAltitudeOrThrow(), 7000)
    );
  }

  @Test
  void 구름정보가_없는_메타_파싱성공() {
    String metar = "RKSI 010300Z 17008KT 4000 -RA 13/13 Q1007 NOSIG";

    CloudGroupRegexParser parser = new CloudGroupRegexParser();
    CloudGroup cloudGroup = parser.parse(metar);

    assertEquals(cloudGroup.size(), 0);
  }

}
