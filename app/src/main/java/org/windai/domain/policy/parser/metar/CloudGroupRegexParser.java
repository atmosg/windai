package org.windai.domain.policy.parser.metar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudCoverage;
import org.windai.domain.vo.CloudGroup;
import org.windai.domain.vo.CloudType;

public class CloudGroupRegexParser extends RegexReportParser<CloudGroup> {

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
  public CloudGroup parse(String rawText) {
    Matcher matcher = getMatcher(rawText, CLOUD_REGEX);

    List<Cloud> clouds = new ArrayList<>();
    CloudRegexParser cloudParser = new CloudRegexParser();

    while (matcher.find()) {
      String matchedCloudText = matcher.group(0);
      Cloud cloud = cloudParser.parse(matchedCloudText);
      clouds.add(cloud);
    }

    return CloudGroup.builder()
        .clouds(clouds)
        .build();

  }
  
}
