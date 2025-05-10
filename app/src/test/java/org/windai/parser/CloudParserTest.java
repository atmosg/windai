package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.windai.MetarTestData;
import org.windai.domain.exception.GenericSpecificationExeception;
import org.windai.domain.policy.parser.metar.CloudGroupRegexParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudCoverage;
import org.windai.domain.vo.CloudGroup;
import org.windai.domain.vo.CloudType;

public class CloudParserTest {
  
  CloudGroupRegexParser parser = new CloudGroupRegexParser();
  List<String> data = new MetarTestData().getTestData();

  @Test
  void 고도가_없는_구름객체를_생성할때_고도를_넣으면_예외가_발생한다() {
    // given, when, then

    assertThrows(GenericSpecificationExeception.class, () -> 
      Cloud.builder()
        .coverage(CloudCoverage.CLR)
        .altitude(200)
        .type(CloudType.NONE)
        .build()
    );
  }

  @Test
  void 고도가_십만을_넘는_구름을_생성하려고_하면_예외가_발생한다() {
    // given, when, then

    assertThrows(GenericSpecificationExeception.class, () -> 
      Cloud.builder()
        .coverage(CloudCoverage.CLR)
        .altitude(1000)
        .type(CloudType.NONE)
        .build()
    );
  }

  @Test
  void 고도가_필수인_구름객체를_생성할때_고도를_누락하면_예외가_발생한다() {
    // given, when, then

    assertThrows(GenericSpecificationExeception.class, () -> 
      Cloud.builder()
        .coverage(CloudCoverage.BKN)
        .altitude(null)
        .type(CloudType.NONE)
        .build()
    );
  }

  @Test
  void 고도가_없는_구름정보의_경우_altitude가_null인_구름객체를_포함하는_구름군_객체를_반환한다() {
    String rawText = "KHYI 010056Z AUTO 20004MPS 10SM CLR 09/07 A2999 RMK AO2 SLP153 T00940072";

    // when
    parser.parse(rawText);
    CloudGroup cloudGroup = parser.getCloudGroup();

    // then
    Cloud cloud = Cloud.builder()
      .coverage(CloudCoverage.CLR)
      .altitude(null)
      .type(CloudType.NONE)
      .build();
    
    CloudGroup expected = CloudGroup.builder()
      .clouds(List.of(cloud))
      .build();

    assertAll(
      () -> assertEquals(expected, cloudGroup),
      () -> assertThrows(GenericSpecificationExeception.class, () -> 
        cloudGroup.getClouds().get(0).getAltitudeOrThrow()
      )
    );
  }

  @Test
  void 고도가_존재하는_단일구름이_존재하는_경우_구름군_객체의_구름리스트_길이가_1이다() {
    String rawText = "RKSI 010300Z 17008KT 4000 -RA SCT006 13/13 Q1007 NOSIG";

    // when
    parser.parse(rawText);
    CloudGroup cloudGroup = parser.getCloudGroup();

    // then
    Cloud expected1 = Cloud.builder()
      .coverage(CloudCoverage.SCT)
      .altitude(600)
      .type(CloudType.NONE)
      .build();

    CloudGroup expected = CloudGroup.builder()
      .clouds(List.of(expected1))
      .build();

    assertEquals(expected, cloudGroup);
  }


  @Test
  void 고도가_존재하는_구름군이_존재하는_경우_해당_구름군_객체를_반환한다() {
    String rawText = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070CB 13/13 Q1007 NOSIG";

    // when
    parser.parse(rawText);
    CloudGroup cloudGroup = parser.getCloudGroup();

    // then
    Cloud expected1 = Cloud.builder()
      .coverage(CloudCoverage.SCT)
      .altitude(600)
      .type(CloudType.NONE)
      .build();
    
    Cloud expected2 = Cloud.builder()
      .coverage(CloudCoverage.BKN)
      .altitude(2500)
      .type(CloudType.NONE)
      .build();
    
    
    Cloud expected3 = Cloud.builder()
      .coverage(CloudCoverage.OVC)
      .altitude(7000)
      .type(CloudType.CB)
      .build();

    CloudGroup expected = CloudGroup.builder()
      .clouds(List.of(expected1, expected2, expected3))
      .build();

    assertEquals(expected, cloudGroup);
  }

  @Test
  void 구름정보가_없는_메타_파싱성공() {
    String metar = "RKSI 010300Z 17008KT 4000 -RA 13/13 Q1007 NOSIG";

    parser.parse(metar);
    CloudGroup cloudGroup = parser.getCloudGroup();

    assertEquals(cloudGroup.size(), 0);
  }

  @Test
  void 단일구름과_임의의_공백을_가진_구름_파싱성공() {
    String rawText1 = "SCT006";
    String rawText1a = "SCT006 ";
    String rawText1b = " SCT006";
    String rawText1c = " SCT006 ";

    // when
    parser.parse(rawText1);
    CloudGroup cloudGroup1 = parser.getCloudGroup();

    parser.parse(rawText1a);
    CloudGroup cloudGroup1a = parser.getCloudGroup();
    
    parser.parse(rawText1b);
    CloudGroup cloudGroup1b = parser.getCloudGroup();
    
    parser.parse(rawText1c);
    CloudGroup cloudGroup1c = parser.getCloudGroup();

    // then
    Cloud expected1 = Cloud.builder()
      .coverage(CloudCoverage.SCT)
      .altitude(600)
      .type(CloudType.NONE)
      .build();
    
    CloudGroup expected = CloudGroup.builder()
      .clouds(List.of(expected1))
      .build();

    assertAll(
      () -> assertEquals(expected, cloudGroup1),
      () -> assertEquals(expected, cloudGroup1a),
      () -> assertEquals(expected, cloudGroup1b),
      () -> assertEquals(expected, cloudGroup1c)
    );
  }

  @Test
  void 임의의_공백과_구름군을_가진_구름_파싱성공() {
    String rawText1 = "SCT006 BKN025";
    String rawText1a = "SCT006 BKN025 ";
    String rawText1b = " SCT006  BKN025";

    // when
    parser.parse(rawText1);
    CloudGroup cloudGroup1 = parser.getCloudGroup();
    
    parser.parse(rawText1a);
    CloudGroup cloudGroup1a = parser.getCloudGroup();
    
    parser.parse(rawText1b);
    CloudGroup cloudGroup1b = parser.getCloudGroup();

    // then
    Cloud expected1 = Cloud.builder()
      .coverage(CloudCoverage.SCT)
      .altitude(600)
      .type(CloudType.NONE)
      .build();
      
    
    Cloud expected2 = Cloud.builder()
      .coverage(CloudCoverage.BKN)
      .altitude(2500)
      .type(CloudType.NONE)
      .build();
    
    CloudGroup expected = CloudGroup.builder()
      .clouds(List.of(expected1, expected2))
      .build();

    assertAll(
      () -> assertEquals(expected, cloudGroup1),
      () -> assertEquals(expected, cloudGroup1a),
      () -> assertEquals(expected, cloudGroup1b)
    );
  }

}
