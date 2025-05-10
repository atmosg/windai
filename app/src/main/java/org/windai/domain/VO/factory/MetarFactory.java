package org.windai.domain.vo.factory;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.windai.domain.policy.parser.metar.AltimeterRegexParser;
import org.windai.domain.policy.parser.metar.CloudGroupRegexParser;
import org.windai.domain.policy.parser.metar.MetarReportTypeRegexParser;
import org.windai.domain.policy.parser.metar.ObservationTimeRegexParser;
import org.windai.domain.policy.parser.metar.RemarkRegexParser;
import org.windai.domain.policy.parser.metar.TemperaturePairRegexParser;
import org.windai.domain.policy.parser.metar.VisibilityRegexParser;
import org.windai.domain.policy.parser.metar.WeatherGroupRegexParser;
import org.windai.domain.policy.parser.metar.WindRegexParser;
import org.windai.domain.policy.parser.shared.RegexReportParser;
import org.windai.domain.vo.CloudGroup;
import org.windai.domain.vo.Metar;
import org.windai.domain.vo.MetarReportType;
import org.windai.domain.vo.Pressure;
import org.windai.domain.vo.TemperaturePair;
import org.windai.domain.vo.Visibility;
import org.windai.domain.vo.WeatherGroup;
import org.windai.domain.vo.Wind;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MetarFactory {



}
