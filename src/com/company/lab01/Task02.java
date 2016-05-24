package com.company.lab01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * infm created it with love on 4/12/16. Enjoy ;)
 */
public class Task02 implements SingleValueGenerator {
  private BigInteger m;
  private BigInteger a;
  private BigInteger c;
  private BigInteger x0;

  public Task02(int m, int a, int c, long x0) {
    this.m = BigInteger.valueOf(m);
    this.a = BigInteger.valueOf(a);
    this.c = BigInteger.valueOf(c);
    this.x0 = BigInteger.valueOf(x0);
  }

  public int getPotential() {
    return getPotential(m);
  }

  public int getPotential(BigInteger mod) {
    BigInteger bExp = a.subtract(BigInteger.ONE);
    BigInteger b = bExp;
    for (int s = 1; s < 51; ++s) {
      if (bExp.mod(mod).compareTo(BigInteger.ZERO) == 0) {
        return s;
      }
      bExp = bExp.multiply(b);
    }
    return -1;
  }

  public int getPeriod() throws FileNotFoundException {
    return getPeriod(m);
  }

  @Override
  public BigInteger getVal0() {
    return x0;
  }

  @Override
  public BigInteger getRange() {
    return m;
  }

  public int getPeriod(BigInteger mod) throws FileNotFoundException {
    File logFile = new File(String.format("task02_m%04d.log", mod));
    PrintWriter logger = new PrintWriter(logFile);
    boolean loggerIsOpen = true;
    int numGenerated = 0;

    BigInteger x = x0.mod(m);
    HashMap<BigInteger, Integer> occurred = new HashMap<>();
    for (int i = 0; i <= m.intValue(); ++i) {
      x = generateNext(x, m, mod);

      if (numGenerated < 50) {
        numGenerated++;
        logger.println(x);
      } else if (loggerIsOpen) {
        logger.flush();
        logger.close();
        loggerIsOpen = false;
      }

      if (occurred.containsKey(x)) {
        logger.flush();
        logger.close();
        return i - occurred.get(x);
      }
      occurred.put(x, i);
    }
    return -1;
  }

  public List<BigInteger> generateSequence() {
    List<BigInteger> list = new ArrayList<>();
    BigInteger x = x0.mod(m);
    HashMap<BigInteger, Integer> occurred = new HashMap<>();
    for (int i = 0; i <= m.intValue(); ++i) {
      x = generateNext(x, m, m);

      if (occurred.containsKey(x)) {
        list = list.subList(occurred.get(x), i);
        break;
      }
      occurred.put(x, i);
      list.add(x);
    }
    return list;
  }

  public BigInteger generateNext(BigInteger value) {
    return a.multiply(value).add(c).mod(m);
  }

  private BigInteger generateNext(BigInteger x, BigInteger mod0,
                                  BigInteger mod1) {
    return a.multiply(x).add(c).mod(mod0).mod(mod1);
  }
}
