package com.company.lab02;

import com.company.lab01.SingleValueGenerator;

import java.math.BigInteger;
import java.util.*;

/**
 * infm created it with love on 5/16/16. Enjoy ;)
 */
public class ConflictCriterion implements Criterion {

  private static final double EPS = 5e-10;
  public static final double[] T = new double[]{
      .01, .05, .25, 0.5, .75, .95, .99, 1.00 };

  private int logn;
  private int n;
  private int logm;
  private int m;

  private boolean[] met;

  private BigInteger bigM = null;

  public ConflictCriterion(int logn, int logm) {
    this.logn = logn;
    n = 1 << logn;
    this.logm = logm;
    m = 1 << logm;
  }

  public int getLogM() {
    return logm;
  }

  public double calculate(SingleValueGenerator generator) {
    met = new boolean[m];
    Arrays.fill(met, false);
    if (bigM == null)
      bigM = BigInteger.valueOf(m);

    int collisions = 0;
    BigInteger current = BigInteger.valueOf(System.currentTimeMillis());
    for (int i = 0; i < n; ++i) {
      current = generator.generateNext(current);
      int mt = current.mod(bigM).intValue();
      if (!met[mt])
        met[mt] = true;
      else
        collisions++;
    }
    System.out.println(String.format("Met %d collisions.", collisions));
    Map<Integer, Double> pMap = conflictNum();
    int minD = n;
    double p = 1;
    for (Map.Entry<Integer, Double> e : pMap.entrySet()) {
      int currD = e.getKey() - collisions;
      if (currD > 0 && currD < minD) {
        minD = currD;
        p = e.getValue();
      }
    }
    return p;
  }

  @Override
  public double calculate(List<BigInteger> sequence) {
    met = new boolean[m];
    Arrays.fill(met, false);
    if (bigM == null)
      bigM = BigInteger.valueOf(m);
    List<BigInteger> seqCopy = new ArrayList<>(sequence);

    int collisions = 0;
    for (int i = 0; i < n; ++i) {
      BigInteger current = seqCopy.get(i % seqCopy.size());
      int mt = current.mod(bigM).intValue();
      if (!met[mt])
        met[mt] = true;
      else
        collisions++;
    }
    return collisions;
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
      if (j < a.length)
        p += a[j];
      if (p - T[t] > EPS) {
        while (t < T.length && p - T[t] > EPS) {
          ++t;
        }
        if (t > 0)
          ps.put(n - j - 1, 1 - p);
      }
    }
    return ps;
  }
}
