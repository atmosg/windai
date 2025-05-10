package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.windai.MetarTestData;
import org.windai.domain.policy.parser.metar.WeatherGroupRegexParser;
import org.windai.domain.policy.parser.metar.WeatherRegexParser;
import org.windai.domain.vo.Weather;
import org.windai.domain.vo.WeatherGroup;
import org.windai.domain.vo.WeatherInensity;
import org.windai.domain.vo.WeatherPhenomenon;

public class WeatherParserTest {
  
  List<String> metars = new MetarTestData().getTestData();
  WeatherRegexParser parser = new WeatherRegexParser();
  WeatherGroupRegexParser groupParser = new WeatherGroupRegexParser();

  @Test
  void 관측된_기상현상이_없는_경우_NULL을_반환한다() {
    // given
    String rawText = metars.get(0);
    System.out.println(rawText);
    
    // when
    parser.parse(rawText);
    Weather weather = parser.getWeather();
    System.out.println(weather);

    // then
    assertEquals(null, weather);
  }

  @Test
  void 단일_기상현상만_관측된_경우_phenomena의_길이가_1인_Weather객체를_반환한다() {
    // given
    String rawText = metars.get(7);
    System.out.println(rawText);
    
    // when
    parser.parse(rawText);
    Weather weather = parser.getWeather();
    System.out.println(weather);

    // then
    Weather expected = Weather.builder()
      .intensity(WeatherInensity.LIGHT)
      .descriptor(null)
      .phenomena(List.of(WeatherPhenomenon.RA))
      .build();


    assertAll(
      () -> assertEquals(expected, weather),
      () -> assertEquals(1, weather.getPhenomena().size())
    );
  }

  // SNRA, -TSSNRA, PLSNRA, ...
  @Test
  void 복합_기상현상이_존재하는_경우_phenomena의_길이가_1이상인_Weather객체를_반환한다() {
    // given
    String rawText = metars.get(15);
    System.out.println(rawText);
    
    // when
    parser.parse(rawText);
    Weather weather = parser.getWeather();
    System.out.println(weather);

    // then
    Weather expected = Weather.builder()
      .intensity(WeatherInensity.MODERATE)
      .descriptor(null)
      .phenomena(List.of(WeatherPhenomenon.RA, WeatherPhenomenon.SN))
      .build();


    assertAll(
      () -> assertEquals(expected, weather),
      () -> assertEquals(2, weather.getPhenomena().size())
    );
  }

  // "METAR RKTU 030300Z 04002KT 1200 -TSSNRA -PLSN BR BKN010 OVC030 08/08 Q1008 RMK CIG010 SLP085 8/7// 9/8//="
  @Test
  void 복합_기상현상이_여러개_존재하는_경우_WeatherGroup의_size가_1이상이고_WeatherList의_사이즈도_1이상이다() {
    String rawText = metars.get(22);
    System.out.println(rawText);

    // when
    groupParser.parse(rawText);
    WeatherGroup weatherGroup = groupParser.getWeatherGroup();
    System.out.println(weatherGroup);

    // then
    Weather expected1 = Weather.builder()
      .intensity(WeatherInensity.LIGHT)
      .descriptor(null)
      .phenomena(List.of(WeatherPhenomenon.TS, WeatherPhenomenon.SN, WeatherPhenomenon.RA))
      .build();

    Weather expected2 = Weather.builder()
      .intensity(WeatherInensity.LIGHT)
      .descriptor(null)
      .phenomena(List.of(WeatherPhenomenon.PL, WeatherPhenomenon.SN))
      .build();

    Weather expected3 = Weather.builder()
      .intensity(WeatherInensity.MODERATE)
      .descriptor(null)
      .phenomena(List.of(WeatherPhenomenon.BR))
      .build();

    WeatherGroup expected = WeatherGroup.builder()
      .weatherList(List.of(expected1, expected2, expected3))
      .build();

    assertEquals(expected, weatherGroup);
  }
  
}
