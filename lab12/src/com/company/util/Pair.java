package com.company.util;

/**
 * infm created it with love on 4/13/16. Enjoy ;)
 */
public class Pair<K extends Comparable<K>, V extends Comparable<V>> {
  public K first;
  public V second;

  public Pair(K first, V second) {
    this.first = first;
    this.second = second;
  }

  public int hashCode() {
    int hashFirst = first != null ? first.hashCode() : 0;
    int hashSecond = second != null ? second.hashCode() : 0;

    return (hashFirst + hashSecond) * hashSecond + hashFirst;
  }

  public boolean equals(Object other) {
    if (other instanceof Pair) {
      Pair otherPair = (Pair) other;
      return
          ((this.first == otherPair.first ||
              (this.first != null && otherPair.first != null &&
                  this.first.equals(otherPair.first))) &&
              (this.second == otherPair.second ||
                  (this.second != null && otherPair.second != null &&
                      this.second.equals(otherPair.second))));
    }

    return false;
  }
}
