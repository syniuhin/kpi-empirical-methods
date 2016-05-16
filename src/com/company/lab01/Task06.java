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
public class Task06 implements SingleValueGenerator {
  private BigInteger m;
  private BigInteger x0;
  private BigInteger a;
  private BigInteger b;

  public Task06(BigInteger m, BigInteger a, BigInteger b, BigInteger x0) {
    this.m = m;
    this.a = a;
    this.b = b;
    this.x0 = x0;
  }

  @Override
  public BigInteger generateNext(BigInteger value) {
    return value.pow(a.multiply(value).add(b).bitLength()).mod(m);
  }

  public List<BigInteger> generateSequence() {
    BigInteger x = x0;
    HashMap<BigInteger, Integer> occurred = new HashMap<>();
    List<BigInteger> list = new ArrayList<>();
    for (int i = 0; i < Integer.MAX_VALUE / 4; ++i) {
      x = generateNext(x);

      if (occurred.containsKey(x)) {
        list = list.subList(occurred.get(x), i);
        break;
      }
      occurred.put(x, i);
      list.add(x);
    }
    return list;
  }

  @Override
  public int getPeriod() throws FileNotFoundException {
    File logFile = new File("task06.log");
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
}
