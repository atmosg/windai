package org.windai.domain.policy.parser.metar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.Weather;
import org.windai.domain.vo.WeatherGroup;

import lombok.Getter;

@Getter
public class WeatherGroupRegexParser extends RegexReportParser {

  private static final String WEATHER_REGEX = WeatherRegexes.fullPattern();

  private WeatherGroup weatherGroup;

  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, WEATHER_REGEX);
    
    WeatherRegexParser weatherParser = new WeatherRegexParser();
    
    List<Weather> weatherList = new ArrayList<>();
    while (matcher.find()) {
      String matchedWeatherText = matcher.group(0);
      weatherParser.parse(matchedWeatherText);

      if (weatherParser.getWeather() != null) {
        weatherList.add(weatherParser.getWeather());
      }
    }

    weatherGroup = WeatherGroup.builder()
        .weatherList(weatherList)
        .build();
  }
  
}
