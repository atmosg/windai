package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.windai.MetarTestData;
import org.windai.domain.policy.parser.metar.AltimeterRegexParser;
import org.windai.domain.unit.PressureUnit;
import org.windai.domain.vo.Pressure;

public class AltimeterParserTest {
  
  AltimeterRegexParser parser = new AltimeterRegexParser();
  List<String> data = new MetarTestData().getTestData();

  @Test
  void 헥토파스칼_단위의_기압정보를_정상적으로_파싱한다() {
    String rawText = data.get(7);
    parser.parse(rawText);
    Pressure actual = parser.getAltimeter();

    Pressure expected = Pressure.builder()
      .value(1009)
      .pressureUnit(PressureUnit.HPA)
      .build();

    assertEquals(expected, actual);
  }

  @Test
  void 수은_단위의_기압정보를_헥토파스칼_단위로_정상_파싱한다() {
    String rawText = data.get(1);
    parser.parse(rawText);
    Pressure actual = parser.getAltimeter();

    Pressure expected = Pressure.builder()
      .value(1017)
      .pressureUnit(PressureUnit.HPA)
      .build();

    assertEquals(expected, actual);
  }

}
