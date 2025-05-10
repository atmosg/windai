package org.windai.domain.policy.parser.metar;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudCoverage;
import org.windai.domain.vo.CloudType;

import lombok.Getter;

@Getter
public class CloudRegexParser extends RegexReportParser {

  private static final String CLOUD_REGEX = CloudRegexes.fullPattern();

  private Cloud cloud;

  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, CLOUD_REGEX);
    
    if (!check(matcher)) return;
    
    String coverageMatch = matcher.group(CloudRegexes.COVERAGE.getGroupName());
    String altitudeMatch = matcher.group(CloudRegexes.ALTITUDE.getGroupName());
    String typeMatch = matcher.group(CloudRegexes.TYPE.getGroupName());

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

      cloud = Cloud.builder()
        .coverage(coverage)
        .altitude(altitude)
        .type(type)
        .build();
    } else {
      cloud = Cloud.builder()
        .coverage(coverage)
        .type(type)
        .build();
    }
    
  }
  
}
