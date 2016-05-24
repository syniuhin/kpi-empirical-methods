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
public class Task03 implements ValueGenerator {
  private static final BigInteger TWO_POW_E = BigInteger.valueOf(2).pow(24);

  private BigInteger x0;

  public Task03(BigInteger x0) {
    this.x0 = x0;
  }

  public int getPeriod() throws FileNotFoundException {
    File logFile = new File("task03.log");
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

  public BigInteger generateNext(BigInteger x) {
    return x.multiply(x.add(BigInteger.ONE)).mod(TWO_POW_E);
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
  public BigInteger getVal0() {
    return x0;
  }

  @Override
  public BigInteger getRange() {
    return TWO_POW_E;
  }
}
