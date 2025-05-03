package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.WindRegexParser;
import org.windai.domain.unit.SpeedUnit;
import org.windai.domain.vo.Wind;
import org.windai.domain.vo.WindDirection;

public class WindParserTest {
  
  WindRegexParser parser = new WindRegexParser();
  
  @Test
  void 바람_파싱성공() {
    String rksi = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";
    String rkjk = "SPECI RKJK 010647Z VRB11KT 1 3/4SM -RA BR OVC004 15/14 A2969 RMK AO2A VIS 1 3/4V3 RAB0555E25RAB42E43DZB43E46RAB46 CIG 004V006 CIG 003 RWY18 SLPNO $=";
    String rkjy = "METAR RKJY 010600Z 18017G28KT 150V220 3200 +TSRA BR FEW010CB BKN025 OVC060 16/16 Q1007 NOSIG RMK TS NW MOV NE OCNL LTGIC=";
    String khyi = "KHYI 010056Z AUTO 20004MPS 10SM CLR 09/07 A2999 RMK AO2 SLP153 T00940072";

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

    assertThrows(GenericPolicyException.class, () -> {
      parser.parse(metar);
    });
  }
}
