package org.windai.domain.vo;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class WindDirection {
  
  private final WindDirectionType type;
  private final Integer degree;

  private WindDirection(WindDirectionType type, Integer degree) {
    this.type = type;
    this.degree = degree;
  }

  public static WindDirection fixed(Integer degree) {
    return new WindDirection(WindDirectionType.FIXED, degree);
  }

  public static WindDirection variable() {
    return new WindDirection(WindDirectionType.VARIABLE, null);
  }

  public boolean isVariable() {
    return type == WindDirectionType.VARIABLE;
  }

  public int getDegreeOrThrow() {
    if (isVariable()) {
      throw new IllegalStateException("Variable wind has no fixed direction.");
    }
    return degree;
  }

}
