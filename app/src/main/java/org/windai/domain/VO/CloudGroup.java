package org.windai.domain.vo;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CloudGroup {
  
  private final List<Cloud> clouds;

  @Builder
  public CloudGroup(List<Cloud> clouds) {
    this.clouds = List.copyOf(clouds);
  }

  public int size() {
    return clouds.size();
  }

}