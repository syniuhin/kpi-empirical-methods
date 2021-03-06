package com.company.lab02;

import com.company.lab01.ValueGenerator;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Random;

/**
 * infm created it with love on 5/18/16. Enjoy ;)
 */
public class NativeGenerator implements ValueGenerator {

  private Random random;
  private int bound;
  private long seed;

  public NativeGenerator(int logbound, long seed) {
    this.seed = seed;
    random = new Random(seed);
    this.bound = 1 << logbound;
  }

  @Override
  public BigInteger generateNext(BigInteger value) {
    return BigInteger.valueOf(random.nextInt(bound));
  }

  @Override
  public int getPeriod() throws FileNotFoundException {
    return 0;
  }

  @Override
  public BigInteger getVal0() {
    return BigInteger.valueOf(seed);
  }

  @Override
  public BigInteger getRange() {
    return BigInteger.valueOf(bound);
  }
}
