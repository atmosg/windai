package org.windai.domain.policy.parser.metar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.Weather;
import org.windai.domain.vo.WeatherDescriptor;
import org.windai.domain.vo.WeatherInensity;
import org.windai.domain.vo.WeatherPhenomenon;

import lombok.Getter;

@Getter
public class WeatherRegexParser extends RegexReportParser {
  
  private static final String WEATHER_REGEX = WeatherRegexes.fullPattern();
  private static final String PHENOMENON_REGEX = WeatherRegexes.PHENOMENON.getRegex();

  private Weather weather;

  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, WEATHER_REGEX);

    if (!check(matcher)) return;
    
    String intensityMatch = matcher.group(1);
    String descriptorMatch = matcher.group(2);
    String phenomenonMatch = matcher.group(3);

    WeatherInensity intensity = intensityMatch != null 
      ? WeatherInensity.fromSymbol(intensityMatch) 
      : WeatherInensity.MODERATE;

    WeatherDescriptor descriptor = descriptorMatch != null
      ? WeatherDescriptor.valueOf(descriptorMatch)
      : null;
    
    List<WeatherPhenomenon> phenomena = parsePhenomena(phenomenonMatch, PHENOMENON_REGEX);

    if (descriptor == null && phenomena.isEmpty()) return;
    
    weather = Weather.builder()
      .intensity(intensity)
      .descriptor(descriptor)
      .phenomena(phenomena)
      .build();
      
  }

  private List<WeatherPhenomenon> parsePhenomena(String phenomenonMatch, String phenomenonRegex) {
    List<WeatherPhenomenon> phenomena = new ArrayList<>();
    
    Matcher matcher = getMatcher(phenomenonMatch, phenomenonRegex);
    
    while (matcher.find()) {
      String matched = matcher.group(0);
      WeatherPhenomenon phenomenon = WeatherPhenomenon.valueOf(matched);
      phenomena.add(phenomenon);
    }

    return phenomena;
  }
  
}
