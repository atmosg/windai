package org.windai.domain.policy.parser.metar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudGroup;

public class CloudGroupRegexParser extends RegexReportParser<CloudGroup> {

  private static final String CLOUD_REGEX = CloudRegexes.fullPattern();

  @Override
  public CloudGroup parse(String rawText) {
    Matcher matcher = getMatcher(rawText, CLOUD_REGEX);
    CloudRegexParser cloudParser = new CloudRegexParser();

    List<Cloud> clouds = new ArrayList<>();
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
