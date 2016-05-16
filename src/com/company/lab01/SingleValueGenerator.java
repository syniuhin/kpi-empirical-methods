package com.company.lab01;

import java.io.FileNotFoundException;
import java.math.BigInteger;

/**
 * infm created it with love on 4/12/16. Enjoy ;)
 */
public interface SingleValueGenerator {
  BigInteger generateNext(BigInteger value);
  int getPeriod() throws FileNotFoundException;

  BigInteger getVal0();
}
