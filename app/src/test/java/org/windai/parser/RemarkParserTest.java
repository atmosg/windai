package org.windai.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.windai.domain.policy.parser.metar.RemarkRegexParser;

public class RemarkParserTest {
  
  RemarkRegexParser parser = new RemarkRegexParser();

  @Test
  void 리마크가_존재하는_메타의_파싱에_성공해야한다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 18/12 A2995 RMK AO2 SLP142 T01780122=";

    parser.parse(rawText);
    String actual = parser.getRemarks();
    String expected = "AO2 SLP142 T01780122=";

    assertEquals(expected, actual);
  }

  @Test
  void 리마크가_존재하지_않는_메타는_공백문자열이_파싱된다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 18/12 A2995";

    parser.parse(rawText);
    String actual = parser.getRemarks();
    String expected = "";

    assertEquals(expected, actual);
  }

}
