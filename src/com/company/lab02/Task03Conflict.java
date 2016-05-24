package com.company.lab02;

import com.company.lab01.ValueGenerator;

import java.io.FileNotFoundException;
import java.math.BigInteger;

/**
 * infm created it with love on 5/17/16. Enjoy ;)
 */
public class Task03Conflict implements ValueGenerator {
  private final BigInteger TWO_POW_E;

  private BigInteger x0;

  public Task03Conflict(BigInteger x0, int logm) {
    this.x0 = x0;
    TWO_POW_E = BigInteger.valueOf(2).pow(logm);
  }

  public int getPeriod() throws FileNotFoundException {
    return -1;
  }

  public BigInteger generateNext(BigInteger x) {
    return x.multiply(x.add(BigInteger.ONE)).mod(TWO_POW_E);
  }

  @Override
  public BigInteger getVal0() {
    return x0;
  }

  @Override
  public BigInteger getRange() {
    return TWO_POW_E;
  }
}
