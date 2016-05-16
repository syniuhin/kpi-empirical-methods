package com.company.lab02;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.swap;

/**
 * infm created it with love on 5/16/16. Enjoy ;)
 */
public class PermutationCriteria implements Criteria {

  private static HashMap<Integer, Integer> frequency = new HashMap<>();
  private int t;
  private int tFact;
  private int n;

  public PermutationCriteria(int t, int n) {
    this.t = t;
    tFact = 1;
    for (int i = 2; i <= t; ++i) {
      tFact *= i;
    }
    this.n = n;

    setupMap();
  }

  // modifies c to next permutation or returns null if such permutation does
  // not exist
  private static ArrayList<Integer> nextPermutation(
      final ArrayList<Integer> c) {
    // 1. finds the largest k, that c[k] < c[k+1]
    int first = getFirst(c);
    if (first == -1) {
      return null; // no greater permutation
    }
    // 2. find last index toSwap, that c[k] < c[toSwap]
    int toSwap = c.size() - 1;
    while (c.get(first).compareTo(c.get(toSwap)) >= 0) {
      --toSwap;
    }
    // 3. swap elements with indexes first and last
    swap(c, first++, toSwap);
    // 4. reverse sequence from k+1 to n (inclusive)
    toSwap = c.size() - 1;
    while (first < toSwap) {
      swap(c, first++, toSwap--);
    }
    return c;
  }

  // finds the largest k, that c[k] < c[k+1]
  // if no such k exists (there is not greater permutation), return -1
  private static int getFirst(final ArrayList<Integer> c) {
    for (int i = c.size() - 2; i >= 0; --i) {
      if (c.get(i).compareTo(c.get(i + 1)) < 0) {
        return i;
      }
    }
    return -1;
  }

  public int getT() {
    return t;
  }

  public void setT(int t) {
    this.t = t;
  }

  public int getN() {
    return n;
  }

  public void setN(int n) {
    this.n = n;
  }

  public double calculate(List<BigInteger> sequence) {
    reset();
    List<BigInteger> subSequence;
    int hash;
    int upperBound = Math.min(n, sequence.size() / t);
    for (int i = 0; i < upperBound; ++i) {
      subSequence = new ArrayList<>(sequence.subList(i * t, (i + 1) * t));
      hash = permHash(subSequence);
      if (!frequency.containsKey(hash)) {
        throw new IllegalStateException(
            "Hash map is not properly initialized!");
      }
      int freqNew = frequency.get(hash) + 1;
      frequency.put(hash, freqNew);
    }

    BigDecimal chisq = BigDecimal.ZERO;
    // Computes chi squared
    for (HashMap.Entry<Integer, Integer> kv : frequency.entrySet()) {
      BigInteger y = BigInteger.valueOf(kv.getValue());
      y = y.multiply(y);
      chisq = chisq.add(
          new BigDecimal(y.multiply(BigInteger.valueOf(tFact)))
                    .divide(
                        BigDecimal.valueOf(upperBound),
                        BigDecimal.ROUND_FLOOR));
    }
    return chisq.doubleValue() - upperBound;
  }

  private void reset() {
    for (HashMap.Entry<Integer, Integer> kv : frequency.entrySet()) {
      frequency.put(kv.getKey(), 0);
    }
  }

  private void setupMap() {
    ArrayList<Integer> perm = new ArrayList<>();
    for (int i = 0; i < t; ++i) {
      perm.add(i + 1);
    }

    int hash;
    do {
      hash = initialPermHash(perm);
      frequency.put(hash, 0);
      perm = nextPermutation(perm);
    } while (perm != null);
  }

  private int permHash(List<BigInteger> list) {
/*
    ArrayList<BigInteger> sorted = new ArrayList<>(list);
    Collections.sort(sorted);
    int hash = 0;
    for (BigInteger initial : list) {
      hash = t * hash + sorted.indexOf(initial);
    }
    return hash;
*/
    int r = t;
    int f = 0;
    BigInteger listMax;
    while (r > 1) {
      listMax = Collections.max(list.subList(0, r));
      int s = list.indexOf(listMax);
      f = r * f + s - 1;
      swap(list, r - 1, s);
      r--;
    }
    return f;
  }

  private int initialPermHash(List<Integer> list) {
    int r = t;
    int f = 0;
    int listMax;
    List<Integer> listCopy = new ArrayList<>(list);
    while (r > 1) {
      listMax = Collections.max(listCopy.subList(0, r));
      int s = listCopy.indexOf(listMax);
      f = r * f + s - 1;
      swap(listCopy, r - 1, s);
      r--;
    }
    return f;
  }
}
