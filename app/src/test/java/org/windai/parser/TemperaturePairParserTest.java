package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.windai.MetarTestData;
import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.TemperaturePairRegexParser;
import org.windai.domain.unit.TemperatureUnit;
import org.windai.domain.vo.Temperature;
import org.windai.domain.vo.TemperaturePair;

public class TemperaturePairParserTest {

  TemperaturePairRegexParser parser = new TemperaturePairRegexParser();
  List<String> data = new MetarTestData().getTestData();

  @Test
  void 양의_온도쌍을_가진_메타_파싱에_성공해야한다() {
    String rawText = data.get(0);

    parser.parse(rawText);
    TemperaturePair pair = parser.getTemperaturePair();

    Temperature expectedT = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(18)
        .build();

    Temperature expectedDewt = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(12)
        .build();

    TemperaturePair expected = TemperaturePair.builder()
        .temperature(expectedT)
        .dewPoint(expectedDewt)
        .build();

    assertEquals(expected, pair);
  }

  @Test
  void 양의_온도와_음의_이슬점을_갖는_메타_파싱에_성공해야한다() {
    String rawText = data.get(1);

    parser.parse(rawText);
    TemperaturePair pair = parser.getTemperaturePair();

    Temperature expectedT = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(10)
        .build();

    Temperature expectedDewt = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(-2)
        .build();

    TemperaturePair expected = TemperaturePair.builder()
        .temperature(expectedT)
        .dewPoint(expectedDewt)
        .build();

    assertEquals(expected, pair);
  }

  @Test
  void 음의_온도와_양의_이슬점을_갖는_메타_파싱에_성공해야한다() {
    String rawText = data.get(2);

    parser.parse(rawText);
    TemperaturePair pair = parser.getTemperaturePair();

    Temperature expectedT = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(-17)
        .build();

    Temperature expectedDewt = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(16)
        .build();

    TemperaturePair expected = TemperaturePair.builder()
        .temperature(expectedT)
        .dewPoint(expectedDewt)
        .build();

    assertEquals(expected, pair);
  }

  @Test
  void 음의_온도쌍을_갖는_메타_파싱에_성공해야한다() {
    String rawText = data.get(3);

    parser.parse(rawText);
    TemperaturePair pair = parser.getTemperaturePair();

    Temperature expectedT = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(-22)
        .build();

    Temperature expectedDewt = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(-14)
        .build();

    TemperaturePair expected = TemperaturePair.builder()
        .temperature(expectedT)
        .dewPoint(expectedDewt)
        .build();

    assertEquals(expected, pair);
  }

  @Test
  void 온도정보가_누락되면_예외가_발생한다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 /12 A2995 RMK AO2 SLP142 T01780122=";

    assertThrows(GenericPolicyException.class, () -> parser.parse(rawText));
  }

  @Test
  void 노점정보가_누락되면_예외가_발생한다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 16/ A2995 RMK AO2 SLP142 T01780122=";

    assertThrows(GenericPolicyException.class, () -> parser.parse(rawText));
  }

  @Test
  void 온도쌍_정보가_누락되면_예외가_발생한다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 A2995 RMK AO2 SLP142 T01780122=";

    assertThrows(GenericPolicyException.class, () -> parser.parse(rawText));
  }

}
