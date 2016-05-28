package com.company.lab01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * infm created it with love on 3/22/16. Enjoy ;)
 */
public class Task01 {
  private static final BigInteger DEC4 = BigInteger.valueOf(10000L);
  private static final BigInteger DEC5 = BigInteger.TEN.multiply(DEC4);
  private static final BigInteger DEC8 = DEC4.multiply(DEC4);
  private static final BigInteger DEC9 = DEC4.multiply(DEC5);
  private static final BigInteger DEC10 = BigInteger.TEN.multiply(DEC9);

  private static BigInteger K2(BigInteger x) {
    return x.divide(DEC8).mod(BigInteger.TEN);
  }

  private static BigInteger K3(BigInteger x) {
    return (x.compareTo(BigInteger.valueOf(5).multiply(DEC9)) == -1)
        ? x.add(BigInteger.valueOf(5).multiply(DEC9))
        : x;
  }

  private static BigInteger K4(BigInteger x) {
    return x.multiply(x).divide(DEC5).mod(DEC10);
  }

  private static BigInteger K5(BigInteger x) {
    return x.multiply(BigInteger.valueOf(1001001001L)).mod(DEC10);
  }

  private static BigInteger K6(BigInteger x) {
    return (x.compareTo(DEC8) == -1)
        ? x.add(BigInteger.valueOf(9814055677L))
        : DEC10.subtract(x);
  }

  private static BigInteger K7(BigInteger x) {
    return DEC5.multiply(x.mod(DEC5)).add(x.divide(DEC5));
  }

  private static BigInteger K8(BigInteger x) {
    return K5(x);
  }

  private static BigInteger K9(BigInteger x) {
    BigInteger dec = BigInteger.ONE;
    BigInteger res = BigInteger.ZERO;
    while (x.divide(dec).compareTo(BigInteger.ZERO) == 1) {
      BigInteger dig = x.divide(dec).mod(BigInteger.TEN);
      if (dig.compareTo(BigInteger.ZERO) == 1)
        res = res.add(dec.multiply(dig.subtract(BigInteger.ONE)));
      dec = dec.multiply(BigInteger.TEN);
    }
    return res;
  }

  private static BigInteger K10(BigInteger x) {
    return (x.compareTo(DEC5) == -1)
        ? x.multiply(x).add(DEC5).subtract(BigInteger.ONE)
        : x.subtract(DEC5).add(BigInteger.ONE);
  }

  private static BigInteger K11(BigInteger x) {
    if (x.compareTo(BigInteger.ZERO) == 0)
      throw new IllegalArgumentException("K11: x equals zero!");
    return (x.compareTo(DEC9) == -1) ? K11(x.multiply(BigInteger.TEN)) : x;
  }

  private static BigInteger K12(BigInteger x) {
    return x.multiply(x.subtract(BigInteger.ONE)).divide(DEC5).mod(DEC10);
  }

  public void solve(BigInteger x, PrintWriter pw) throws FileNotFoundException {
    ArrayList<Step> steps = new ArrayList<Step>();
    steps.add(Task01::K3);
    steps.add(Task01::K4);
    steps.add(Task01::K5);
    steps.add(Task01::K6);
    steps.add(Task01::K7);
    steps.add(Task01::K8);
    steps.add(Task01::K9);
    steps.add(Task01::K10);
    steps.add(Task01::K11);
    steps.add(Task01::K12);

    int y = x.divide(DEC9).intValue();
    runAlgo(x, steps, y, true, pw);
  }

  private void runAlgo(BigInteger x, ArrayList<Step> steps,
                       int y, boolean infinite, PrintWriter pw)
      throws FileNotFoundException {
    File logFile = new File("task01.log");
    PrintWriter logger = new PrintWriter(logFile);
    boolean loggerIsOpen = true;

    BigInteger initX = x;
    ArrayList<Set<BigInteger>> occ = new ArrayList<>();
    for (int i = 0; i < 10; ++i)
      occ.add(new HashSet<>());
    pw.println(String.format("K1\t%s", x.toString()));
    int numIter = 0;
    int numGenerated = 0;
    while (y > -1) {
      pw.println("Iteration #" + String.valueOf(numIter));
      int z = K2(x).intValue();
      for (int i = z; i < steps.size(); ++i) {
        x = steps.get(i).apply(x);

        if (numGenerated < 50) {
          numGenerated++;
          logger.println(x);
        } else if (loggerIsOpen) {
          logger.flush();
          logger.close();
          loggerIsOpen = false;
        }

        pw.println(String.format("K%d\t%s", i + 3, x.toString()));
        if (occ.get(i).contains(x)) {
          pw.println(String.format("CYCLE! Length: %d", numIter));
          return;
        }
        occ.get(i).add(x);
      }
      if (x.compareTo(initX) == 0) {
        logger.flush();
        logger.close();
        pw.println(String.format("CYCLE! Length: %d", numIter));
        return;
      }
      if (!infinite) {
        pw.println(y);
        --y;
      }
      numIter++;
    }
  }

  private interface Step {
    BigInteger apply(BigInteger x);
  }
}
