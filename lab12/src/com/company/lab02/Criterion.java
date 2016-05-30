package com.company.lab02;

import com.company.lab01.ValueGenerator;

/**
 * infm created it with love on 5/16/16. Enjoy ;)
 */
public interface Criterion {

  /**
   * @param generator: Generator to use.
   * @return: Result of a criterion, either some value (e.g. chi-squared) or
   * quantile.
   */
  double calculate(ValueGenerator generator);

}
