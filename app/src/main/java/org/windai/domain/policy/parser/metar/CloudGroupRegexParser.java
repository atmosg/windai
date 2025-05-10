package org.windai.domain.policy.parser.metar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudGroup;

import lombok.Getter;

@Getter
public class CloudGroupRegexParser extends RegexReportParser {

  private static final String CLOUD_REGEX = CloudRegexes.fullPattern();

  private CloudGroup cloudGroup;

  @Override
  public void parse(String rawText) {
    Matcher matcher = getMatcher(rawText, CLOUD_REGEX);
    CloudRegexParser cloudParser = new CloudRegexParser();

    List<Cloud> clouds = new ArrayList<>();
    while (matcher.find()) {
      String matchedCloudText = matcher.group(0);
      cloudParser.parse(matchedCloudText);
      
      if (cloudParser.getCloud() != null) {
        clouds.add(cloudParser.getCloud());
      }
    }

    cloudGroup = CloudGroup.builder()
        .clouds(clouds)
        .build();

  }

}
