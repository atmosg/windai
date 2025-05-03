package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudCoverage;
import org.windai.domain.vo.CloudType;

public class CloudRegexParser extends RegexReportParser<Cloud> {

  private static final String CLOUD_REGEX = CloudRegexes.fullPattern();

  @Override
  public Cloud parse(String rawText) {
    Matcher matcher = getMatcher(rawText, CLOUD_REGEX);
    
    if (!check(matcher)) {
      return null;
    }

    String coverageMatch = matcher.group(1);
    String altitudeMatch = matcher.group(2);
    String typeMatch = matcher.group(3);

    CloudCoverage coverage = CloudCoverage.valueOf(coverageMatch);
    CloudType type = typeMatch != null 
      ? CloudType.valueOf(typeMatch)
      : CloudType.NONE;

    Integer altitude = altitudeMatch != null 
      ? Integer.parseInt(altitudeMatch)*100 
      : null;

    if (coverage.requiresAltitude()) {
      if (altitude == null) {
        throw new GenericPolicyException("Altitude not found in report: " + rawText);
      }

      return Cloud.builder()
        .coverage(coverage)
        .altitude(altitude)
        .type(type)
        .build();
    } 
    
    return Cloud.builder()
      .coverage(coverage)
      .type(type)
      .build();
  }
  
}
