package com.company.lab01;

import com.company.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * infm created it with love on 4/12/16. Enjoy ;)
 */
public class Task04 implements ValueGenerator {
  private BigInteger p;
  private BigInteger a;
  private BigInteger c;
  private BigInteger x0;

  public Task04(BigInteger p, BigInteger a, BigInteger c, BigInteger x0) {
    this.p = p;
    this.a = a;
    this.c = c;
    this.x0 = x0;
  }

  public int getPeriod() throws FileNotFoundException {
    File logFile = new File("task04.log");
    PrintWriter logger = new PrintWriter(logFile);
    boolean loggerIsOpen = true;
    int numGenerated = 0;

    BigInteger x = x0;
    HashMap<BigInteger, Integer> occurred = new HashMap<>();
    for (int i = 0; i < Integer.MAX_VALUE / 4; ++i) {
      x = generateNext(x);

      if (numGenerated < 50) {
        numGenerated++;
        logger.println(x);
      } else if (loggerIsOpen) {
        logger.flush();
        logger.close();
        loggerIsOpen = false;
      }

      if (occurred.containsKey(x)) {
        // System.out.println(String.format("Cycle starts at: %s", x.toString()));
        logger.flush();
        logger.close();
        return i - occurred.get(x);
      }
      occurred.put(x, i);
    }
    return -1;
  }

  @Override
  public BigInteger getVal0() {
    return x0;
  }

  @Override
  public BigInteger getRange() {
    return p;
  }

  public BigInteger generateNext(BigInteger x) {
    BigInteger xInverse;
    if (x.compareTo(BigInteger.ZERO) != 0) {
      BigInteger x1 = (new Euclid()).extended(x, p).first;
      xInverse = x1.mod(p).add(x1).mod(p);
    } else {
      xInverse = BigInteger.ZERO;
    }
    return a.multiply(xInverse).add(c).mod(p);
  }

  private class Euclid {
    Pair<BigInteger, BigInteger> extended(BigInteger a, BigInteger b) {
      if (a.compareTo(BigInteger.ZERO) == 0) {
        if (b.compareTo(BigInteger.ONE) != 0)
          throw new IllegalArgumentException("Equation has no solutions!");
        return new Pair<>(BigInteger.ZERO, BigInteger.ONE);
      }
      Pair<BigInteger, BigInteger> p = extended(b.mod(a), a);
      BigInteger x1 = p.first;
      BigInteger y1 = p.second;
      BigInteger x = y1.subtract(b.divide(a).multiply(x1));
      return new Pair<>(x, x1);
    }
  }

}
