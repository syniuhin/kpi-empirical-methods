package com.company.lab02;

import com.company.lab01.ValueGenerator;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * infm created it with love on 5/16/16. Enjoy ;)
 */
public class CollisionCriterion implements Criterion {

  public static final double[] T = new double[]{
      .01, .025, .05, .1, .25, .5, .75, .9, .95, .975, .99, 1.00 };
  private static final double EPS = 5e-10;
  private int logn;
  private int n;
  private int logm;
  private int m;

  private boolean[] met;

  public CollisionCriterion(int logn, int logm) {
    this.logn = logn;
    n = 1 << logn;
    this.logm = logm;
    m = 1 << logm;
  }

  public int getLogM() {
    return logm;
  }

  public double calculate(ValueGenerator generator) {
    met = new boolean[m];
    Arrays.fill(met, false);
    int collisions = 0;
    BigInteger current = generator.getVal0();
    for (int i = 0; i < n; ++i) {
      // Generate decimal word
      int mt = 0;
      for (int j = 0; j < logm; ++j) {
        current = generator.generateNext(current);
        long b = Math.floorDiv(
            2L * current.longValue(), generator.getRange().longValue());
        mt += b << j;
      }
      if (!met[mt]) {
        met[mt] = true;
      } else {
        collisions++;
      }
    }
    System.out.println(String.format("Met %d collisions.", collisions));
    Map<Integer, Double> pMap = conflictNum();
    int minD = -n;
    double p = 1;
    for (Map.Entry<Integer, Double> e : pMap.entrySet()) {
      int currD = e.getKey() - collisions;
      if (currD < 0 && currD > minD) {
        minD = currD;
        p = e.getValue();
      }
    }
    return p;
  }

  public Map<Integer, Double> conflictNum() {
    double[] a = new double[n + 1];

    for (int i = 0; i <= n; ++i) {
      a[i] = 0;
    }
    a[1] = 1;

    int j0 = 1;
    int j1 = 1;

    for (int step = 0; step < n - 1; ++step) {
      j1 = j1 + 1;
      for (int j = j1; j >= j0; --j) {
        double dj = (double) j;
        a[j] = dj / m * a[j] + ((1.0 + 1.0 / m) - (dj / m)) * a[j - 1];
        if (a[j] < EPS) {
          a[j] = 0;
          if (j == j1) {
            --j1;
          } else if (j == j0) {
            ++j0;
          }
        }
      }
    }

    double p = 0;
    TreeMap<Integer, Double> ps = new TreeMap<>();
    int t = 0;
    int j = j0 - 1;
    while (t < T.length && 1 - p > EPS && j <= n) {
      j++;
      if (j < a.length) {
        p += a[j];
      }
      if (p - T[t] > EPS) {
        while (t < T.length && p - T[t] > EPS) {
          ++t;
        }
        if (t > 0) {
          ps.put(n - j - 1, 1 - p);
        }
      }
    }
    return ps;
  }
}
