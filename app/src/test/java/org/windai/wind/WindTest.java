package org.windai.wind;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.windai.domain.policy.crosswind.MinimumCrosswindPolicyType;
import org.windai.domain.service.WindOperation;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.unit.SpeedUnit;
import org.windai.domain.vo.Runway;
import org.windai.domain.vo.RunwayEnd;
import org.windai.domain.vo.RunwaySide;
import org.windai.domain.vo.Wind;
import org.windai.domain.vo.WindDirection;

public class WindTest {

  @Test
  void 길이단위변환_성공() {
    RunwayEnd runwayEndA = RunwayEnd.builder()
      .heading(32)
      .side(RunwaySide.NONE)
      .available(true)
      .build();

    RunwayEnd runwayEndB = RunwayEnd.builder()
      .heading(14)
      .side(RunwaySide.NONE)
      .available(true)
      .build();

    Runway runway = Runway.builder()
      .endA(runwayEndA)
      .endB(runwayEndB)
      .length(4000)
      .lengthUnit(LengthUnit.METERS)
      .build();

    double lengthInFeet = runway.getLengthIn(LengthUnit.FEET);
    double lengthInMeters = runway.getLengthIn(LengthUnit.METERS);

    assertAll(
      () -> assertEquals(lengthInFeet, runway.getLength() * 3.28084),
      () -> assertEquals(lengthInMeters, runway.getLength() * 1.0)
    );
  }

  @Test
  void 속도단위변환_성공() {
    Wind wind = Wind.builder()
      .direction(WindDirection.fixed(270))
      .speed(10)
      .gusts(25)
      .speedUnit(SpeedUnit.KT)
      .build();

    int speed = wind.getSpeed();
    double kt = wind.getSpeedUnit().convertTo(speed, SpeedUnit.KT);
    double mps = wind.getSpeedUnit().convertTo(speed, SpeedUnit.MPS);
    
    assertAll(
      () -> assertEquals(kt, wind.getSpeed() * 1.0),
      () -> assertEquals(mps, wind.getSpeed() / 1.94384)
    );
  }

  @Test
  void 고정풍향_측풍계산_성공() {
    Wind wind = Wind.builder()
      .direction(WindDirection.fixed(270))
      .speed(10)
      .gusts(25)
      .speedUnit(SpeedUnit.KT)
      .build();

    Wind crossWind = wind.calculateCrosswind(36);

    assertAll(
      () -> assertEquals(crossWind.getSpeed(), 10),
      () -> assertEquals(crossWind.getGusts(), 25)
    );    
  }

  @Test
  void 가변풍향_측풍계산_실패() {
    Wind wind = Wind.builder()
      .direction(WindDirection.variable())
      .speed(10)
      .gusts(25)
      .speedUnit(SpeedUnit.KT)
      .build();

    assertThrows(IllegalStateException.class, () -> wind.calculateCrosswind(36));
  }
  
  @Test
  void 단일활주로_측풍최소치계산_성공() {
    // Given
    RunwayEnd runwayEndA = RunwayEnd.builder()
    .heading(32)
    .side(RunwaySide.NONE)
    .available(true)
    .build();

  RunwayEnd runwayEndB = RunwayEnd.builder()
    .heading(14)
    .side(RunwaySide.NONE)
    .available(true)
    .build();

  Runway runway = Runway.builder()
    .endA(runwayEndA)
    .endB(runwayEndB)
    .length(4000)
    .lengthUnit(LengthUnit.METERS)
    .build();

    Wind wind = Wind.builder()
    .direction(WindDirection.fixed(250))
    .speed(10)
    .gusts(0)
    .speedUnit(SpeedUnit.KT)
    .build();

    // When
    int minimumCrosswind = new WindOperation()
        .minimumCrosswind(wind, List.of(runway), MinimumCrosswindPolicyType.SINGLE);

    // Then
    assertAll(
      () -> assertEquals(9, minimumCrosswind)
    );

  }

  // @Test
  // void 복수활주로_측풍최소치계산_성공() {
  //   // Given
  //   Runway runway1 = Runway.builder()
  //     .heading(32)
  //     .length(4000)
  //     .lengthUnit(LengthUnit.METER)
  //     .build();

  //   Runway runway2 = Runway.builder()
  //     .heading(33)
  //     .length(4000)
  //     .lengthUnit(LengthUnit.METER)
  //     .build();

  //   Wind wind = Wind.builder()
  //     .direction(250)
  //     .speed(10)
  //     .gusts(25)
  //     .build();

  //   // When
  //   int minimumCrosswind = new WindOperation()
  //       .calculateMinimumCrosswind(wind, List.of(runway1, runway2), MinimumCrosswindPolicyType.MULTI);

  //   // Then
  //   assertAll(
  //     () -> assertEquals(wind.calculateCrosswind(runway1.getHeading()).getSpeed(), 9),
  //     () -> assertEquals(wind.calculateCrosswind(runway1.getHeading()).getGusts(), 23),
  //     () -> assertEquals(wind.calculateCrosswind(runway2.getHeading()).getSpeed(), 10),
  //     () -> assertEquals(wind.calculateCrosswind(runway2.getHeading()).getGusts(), 25),
  //     () -> assertEquals(minimumCrosswind, 23)
  //   );

  // }

  // @Test
  // void 복수활주로_측풍최소치계산_실패_단일활주로입력() {
  //   // Given
  //   Runway runway = Runway.builder()
  //     .heading(32)
  //     .length(4000)
  //     .lengthUnit(LengthUnit.METER)
  //     .build();

  //   Wind wind = Wind.builder()
  //     .direction(250)
  //     .speed(10)
  //     .gusts(0)
  //     .build();

  //   // When
  //   // Then
  //   assertThrows(IllegalArgumentException.class, () -> {
  //     new WindOperation()
  //       .calculateMinimumCrosswind(wind, List.of(runway), MinimumCrosswindPolicyType.MULTI);
  //   });

  // }


}
