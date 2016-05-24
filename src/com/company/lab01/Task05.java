package com.company.lab01;

import com.company.util.Pair;

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
public class Task05 implements ValueGenerator {

  private ValueGenerator genX;
  private ValueGenerator genY;

  private BigInteger x;
  private BigInteger y;

  private BigInteger m;

  public Task05(ValueGenerator genX, ValueGenerator genY,
                BigInteger m) {
    this.genX = genX;
    this.genY = genY;
    this.m = m;
    reset();
  }

  private void reset() {
    x = genX.getVal0();
    y = genY.getVal0();
  }

  @Override
  public BigInteger generateNext(BigInteger value) {
    x = genX.generateNext(x);
    y = genY.generateNext(y);
    return x.subtract(y).mod(m);
  }

  public int getPeriod() throws FileNotFoundException {
    File logFile = new File("task05.log");
    PrintWriter logger = new PrintWriter(logFile);
    boolean loggerIsOpen = true;
    int numGenerated = 0;

    BigInteger z;
    HashMap<Pair<BigInteger, BigInteger>, Integer> occurred = new HashMap<>();
    for (int i = 0; i < Integer.MAX_VALUE / 4; ++i) {
      z = generateNext(BigInteger.ONE);

      if (numGenerated < 50) {
        numGenerated++;
        logger.println(z);
      } else if (loggerIsOpen) {
        logger.flush();
        logger.close();
        loggerIsOpen = false;
      }

      Pair<BigInteger, BigInteger> params = new Pair<>(x, y);
      if (occurred.containsKey(params)) {
        logger.flush();
        logger.close();
        return i - occurred.get(params);
      }
      occurred.put(params, i);
    }
    return -1;
  }

  @Override
  public BigInteger getVal0() {
    return null;
  }

  @Override
  public BigInteger getRange() {
    return m;
  }

  public List<BigInteger> generateSequence() {
    BigInteger z;
    HashMap<Pair<BigInteger, BigInteger>, Integer> occurred = new HashMap<>();
    List<BigInteger> list = new ArrayList<>();
    for (int i = 0; i < Integer.MAX_VALUE / 4; ++i) {
      z = generateNext(BigInteger.ONE);

      Pair<BigInteger, BigInteger> params = new Pair<>(x, y);
      if (occurred.containsKey(params)) {
        list = list.subList(occurred.get(params), i);
        break;
      }
      occurred.put(params, i);
      list.add(z);
    }
    return list;
  }
}
