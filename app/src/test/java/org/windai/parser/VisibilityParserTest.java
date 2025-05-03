package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.VisibilityRegexParser;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.vo.Visibility;

public class VisibilityParserTest {
  
  VisibilityRegexParser parser = new VisibilityRegexParser();
  List<String> data = new MetarTestData().getTestData();

  @Test
  void 시정이_관측되지_않은_경우_예외가_발생한다() {
    // given
    String rawText = "KSFO 030953Z 29008KT FEW025 SCT250 18/12 A2995 RMK AO2 SLP142 T01780122=";

    // when
    // then
    assertThrows(GenericPolicyException.class, () -> {
      parser.parse(rawText);
    });
  }

  @Test
  void 마일시정이_관측된_경우_미터로_변환된_Visibility객체를_반환한다() {
    // given
    String rawText = data.get(0);
    System.out.println(rawText);

    // when
    Visibility visibility = parser.parse(rawText);
    System.out.println(visibility);

    // then
    Visibility expected = Visibility.builder()
      .visibility(16093)
      .lengthUnit(LengthUnit.METERS)
      .build();
  
    assertEquals(expected, visibility);
  }

  @Test
  void P6SM이_관측된_경우_시정값이_9999미터이다() {
    // given
    String rawText = "KSFO 030953Z 29008KT P6SM FEW025 SCT250 18/12 A2995 RMK AO2 SLP142 T01780122=";

    // when
    Visibility visibility = parser.parse(rawText);
    System.out.println(visibility);

    // then
    Visibility expected = Visibility.builder()
      .visibility(9999)
      .lengthUnit(LengthUnit.METERS)
      .build();
  
    assertEquals(expected, visibility);
  }

  @Test
  void CAVOK이_관측된_경우_시정값이_9999미터이다() {
    // given
    String rawText = "METAR RKPK 030300Z 21008KT CAVOK 16/12 Q1007 RMK CIG070 SLP076 8/72/ 9/35/=";

    // when
    Visibility visibility = parser.parse(rawText);
    System.out.println(visibility);

    // then
    Visibility expected = Visibility.builder()
      .visibility(9999)
      .lengthUnit(LengthUnit.METERS)
      .build();
  
    assertEquals(expected, visibility);
  }

  @Test
  void 분수형태의_마일시정이_관측된_경우_미터로_변환된_시정객체를_반환한다() {
    // given
    String rawText = "SPECI RKJK 010647Z VRB11KT 1 3/4SM -RA BR OVC004 15/14 A2969 RMK AO2A VIS 1 3/4V3 RAB0555E25RAB42E43DZB43E46RAB46 CIG 004V006 CIG 003 RWY18 SLPNO $=";

    // when
    Visibility visibility = parser.parse(rawText);
    System.out.println(visibility);

    // then
    Visibility expected = Visibility.builder()
      .visibility(2816)
      .lengthUnit(LengthUnit.METERS)
      .build();
  
    assertEquals(expected, visibility);
  }

  @Test
  void 대분수형태의_마일시정이_관측된_경우_미터로_변환된_시정객체를_반환한다() {
    // given
    String rawText = "SPECI RKJK 010647Z VRB11KT 3/4SM -RA BR OVC004 15/14 A2969 RMK AO2A VIS 1 3/4V3 RAB0555E25RAB42E43DZB43E46RAB46 CIG 004V006 CIG 003 RWY18 SLPNO $=";

    // when
    Visibility visibility = parser.parse(rawText);
    System.out.println(visibility);

    // then
    Visibility expected = Visibility.builder()
      .visibility(1207)
      .lengthUnit(LengthUnit.METERS)
      .build();
  
    assertEquals(expected, visibility);
  }

  @Test
  void 임의의_시정과_공백을_가진_시정_파싱성공() {
    // given
    String rawText1 = "1200";
    String rawText1a = "1200 ";
    String rawText1b = " 1200";
    String rawText1c = " 1200 ";
    
    String rawText2 = "CAVOK";
    String rawText2a = "CAVOK ";
    String rawText2b = " CAVOK";
    String rawText2c = " CAVOK ";
    
    String rawText3 = "P6SM";
    String rawText3a = "P6SM ";
    String rawText3b = " P6SM";
    String rawText3c = " P6SM ";
    
    String rawText4 = "3/4SM";
    String rawText4a = "3/4SM ";
    String rawText4b = " 3/4SM";
    String rawText4c = " 3/4SM ";
    
    String rawText5 = "1 3/4SM";
    String rawText5a = "1 3/4SM ";
    String rawText5b = " 1 3/4SM";
    String rawText5c = " 1 3/4SM ";

    // when
    Visibility visibility1 = parser.parse(rawText1);
    Visibility visibility1a = parser.parse(rawText1a);
    Visibility visibility1b = parser.parse(rawText1b);
    Visibility visibility1c = parser.parse(rawText1c);

    Visibility visibility2 = parser.parse(rawText2);
    Visibility visibility2a = parser.parse(rawText2a);
    Visibility visibility2b = parser.parse(rawText2b);
    Visibility visibility2c = parser.parse(rawText2c);

    Visibility visibility3 = parser.parse(rawText3);
    Visibility visibility3a = parser.parse(rawText3a);
    Visibility visibility3b = parser.parse(rawText3b);
    Visibility visibility3c = parser.parse(rawText3c);

    Visibility visibility4 = parser.parse(rawText4);
    Visibility visibility4a = parser.parse(rawText4a);
    Visibility visibility4b = parser.parse(rawText4b);
    Visibility visibility4c = parser.parse(rawText4c);

    Visibility visibility5 = parser.parse(rawText5);
    Visibility visibility5a = parser.parse(rawText5a);
    Visibility visibility5b = parser.parse(rawText5b);
    Visibility visibility5c = parser.parse(rawText5c);
    

    // then
    assertAll(
      () -> assertEquals(1200, visibility1.getVisibility()),
      () -> assertEquals(1200, visibility1a.getVisibility()),
      () -> assertEquals(1200, visibility1b.getVisibility()),
      () -> assertEquals(1200, visibility1c.getVisibility()),
      () -> assertEquals(9999, visibility2.getVisibility()),
      () -> assertEquals(9999, visibility2a.getVisibility()),
      () -> assertEquals(9999, visibility2b.getVisibility()),
      () -> assertEquals(9999, visibility2c.getVisibility()),
      () -> assertEquals(9999, visibility3.getVisibility()),
      () -> assertEquals(9999, visibility3a.getVisibility()),
      () -> assertEquals(9999, visibility3b.getVisibility()),
      () -> assertEquals(9999, visibility3c.getVisibility()),
      () -> assertEquals(1207, visibility4.getVisibility()),
      () -> assertEquals(1207, visibility4a.getVisibility()),
      () -> assertEquals(1207, visibility4b.getVisibility()),
      () -> assertEquals(1207, visibility4c.getVisibility()),
      () -> assertEquals(2816, visibility5.getVisibility()),
      () -> assertEquals(2816, visibility5a.getVisibility()),
      () -> assertEquals(2816, visibility5b.getVisibility()),
      () -> assertEquals(2816, visibility5c.getVisibility())
    );
  }

}
