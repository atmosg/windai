package org.windai.domain.policy.parser.metar;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudCoverage;
import org.windai.domain.vo.CloudType;

public class CloudRegexParser extends RegexReportParser<Cloud> {

  private static final String CLOUD_COVERAGE_REGEX = 
    Arrays.stream(CloudCoverage.values())
      .map(Enum::name)
      .collect(Collectors.joining("|"));
  
  private static final String CLOUD_TYPE_REGEX = 
    Arrays.stream(CloudType.values())
      .map(Enum::name)
      .collect(Collectors.joining("|"));
  
  private static final String CLOUD_ALTITUDE_REGEX = "\\d{2,3}";

  private static final String CLOUD_REGEX = 
    String.format("(%s)(%s)?(%s)?", CLOUD_COVERAGE_REGEX, CLOUD_ALTITUDE_REGEX, CLOUD_TYPE_REGEX);

  @Override
  public Cloud parse(String rawText) {
    Matcher matcher = getMatcher(rawText, CLOUD_REGEX);
    
    if (!check(matcher)) {
      throw new GenericPolicyException("Cloud not found in report: " + rawText);
    }

    String coverageMatch = matcher.group(1);
    String altitudeMatch = matcher.group(2);
    String typeMatch = matcher.group(3);

    CloudCoverage coverage = CloudCoverage.valueOf(coverageMatch);
    CloudType type = typeMatch != null ? CloudType.valueOf(typeMatch) : CloudType.NONE;
    Integer altitude = altitudeMatch != null ? Integer.parseInt(altitudeMatch)*100 : null;

    if (coverage.requiresAltitude()) {
      if (altitude == null) {
        throw new GenericPolicyException("Altitude not found in report: " + rawText);
      }

      return Cloud.withAltitude(altitude, coverage, type);
    } else {
      return Cloud.withoutAltitude(coverage, type);
    }
  }
}
