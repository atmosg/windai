package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.windai.MetarTestData;
import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.VisibilityRegexParser;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.vo.Visibility;

public class VisibilityParserTest {
  
  VisibilityRegexParser parser = new VisibilityRegexParser();
  MetarTestData test = new MetarTestData();
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

    // when
    parser.parse(rawText);
    Visibility visibility = parser.getVisibility();    

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
    parser.parse(rawText);
    Visibility visibility = parser.getVisibility();    
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
    parser.parse(rawText);
    Visibility visibility = parser.getVisibility();
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
    parser.parse(rawText);
    Visibility visibility = parser.getVisibility();
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
    parser.parse(rawText);
    Visibility visibility = parser.getVisibility();
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
    String rawText2 = "CAVOK";
    String rawText3 = "P6SM";
    String rawText4 = "3/4SM";
    String rawText5 = "1 3/4SM";

    // expected
    int expected1 = 1200;
    int expected2 = 9999;
    int expected3 = 9999;
    int expected4 = 1207;
    int expected5 = 2816;

    // then
    int actual1 = (int) test.generateWithSpaces(rawText1).stream()
      .map(text -> {
        parser.parse(text);
        return parser.getVisibility();
      })
      .map(Visibility::getVisibility)
      .filter(vis -> vis == expected1)
      .count();

    int actual2 = (int) test.generateWithSpaces(rawText2).stream()
      .map(text -> {
        parser.parse(text);
        return parser.getVisibility();
      })
      .map(Visibility::getVisibility)
      .filter(vis -> vis == expected2)
      .count();
    
    int actual3 = (int) test.generateWithSpaces(rawText3).stream()
      .map(text -> {
        parser.parse(text);
        return parser.getVisibility();
      })
      .map(Visibility::getVisibility)
      .filter(vis -> vis == expected3)
      .count();
    
    int actual4 = (int) test.generateWithSpaces(rawText4).stream()
      .map(text -> {
        parser.parse(text);
        return parser.getVisibility();
      })
      .map(Visibility::getVisibility)
      .filter(vis -> vis == expected4)
      .count();
    
    int actual5 = (int) test.generateWithSpaces(rawText5).stream()
      .map(text -> {
        parser.parse(text);
        return parser.getVisibility();
      })
      .map(Visibility::getVisibility)
      .filter(vis -> vis == expected5)
      .count();
      
    
    assertAll(
      () -> assertEquals(4, actual1),
      () -> assertEquals(4, actual2),
      () -> assertEquals(4, actual3),
      () -> assertEquals(4, actual4),
      () -> assertEquals(4, actual5)
    );
  }

}
