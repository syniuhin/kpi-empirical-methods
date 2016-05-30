package com.company;

import com.company.lab01.*;
import com.company.lab02.*;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Scanner;

public class Main {

  private static Criterion criterion;
  private static ValueGenerator lastUsed = null;

  private static Criterion lastUsedPermutation;
  private static Criterion lastUsedConflict;

  public static void main(String[] args) {
    try {
      String token = "enter";
      Scanner scanner = new Scanner(System.in);
      while (!token.equals("exit")) {
        System.out.println("Please enter next command:");
        token = scanner.next();
        if (token.equals("solve")) {
          String s = scanner.next();
          int num = 0;
          if (s.equals("all")) {
            System.out.println("Linear congruent...");
            System.out.println("Permutation criterion:");
            criterion = lastUsedPermutation;
            solve2();
            System.out.println("Conflict criterion:");
            criterion = lastUsedConflict;
            solveAgain();

            System.out.println("Coveyou...");
            System.out.println("Permutation criterion:");
            criterion = lastUsedPermutation;
            solve3();
            System.out.println("Conflict criterion:");
            criterion = lastUsedConflict;
            solveAgain();

            System.out.println("Eichenaur & Lehn...");
            System.out.println("Permutation criterion:");
            criterion = lastUsedPermutation;
            solve4();
            System.out.println("Conflict criterion:");
            criterion = lastUsedConflict;
            solveAgain();

            System.out.println("Merge of 2...");
            System.out.println("Permutation criterion:");
            criterion = lastUsedPermutation;
            solve5();
            System.out.println("Conflict criterion:");
            criterion = lastUsedConflict;
            solveAgain();

            System.out.println("Custom method...");
            System.out.println("Permutation criterion:");
            criterion = lastUsedPermutation;
            solve6();
            System.out.println("Conflict criterion:");
            criterion = lastUsedConflict;
            solveAgain();

            System.out.println("System generator...");
            System.out.println("Permutation criterion:");
            criterion = lastUsedPermutation;
            solve7();
            System.out.println("Conflict criterion:");
            criterion = lastUsedConflict;
            solveAgain();
            continue;
          } else {
            num = Integer.valueOf(s);
          }
          switch (num) {
            case 2:
              System.out.println("Linear congruent...");
              solve2();
              break;
            case 3:
              System.out.println("Coveyou...");
              if (criterion instanceof PermutationCriterion) {
                solve3();
              } else {
                solve3Conflict();
              }
              break;
            case 4:
              System.out.println("Eichenaur & Lehn...");
              solve4();
              break;
            case 5:
              System.out.println("Merge of 2...");
              solve5();
              break;
            case 6:
              System.out.println("Custom method...");
              solve6();
              break;
            case 7:
              solve7();
              break;
            default:
              System.out.println("Invalid token!");
              break;
          }
        } else if (token.equals("repeat")) {
          solveAgain();
        } else if (token.equals("criterion")) {
          int num = scanner.nextInt();
          switch (num) {
            case 1: {
              int t = scanner.nextInt();
              int n = scanner.nextInt();
              System.out.println(String.format(
                  "Chosen permutation criteria with t = %d and n = %d", t, n));
              criterion = new PermutationCriterion(t, n);
              lastUsedPermutation = criterion;
            }
            break;
            case 2: {
              int logn = scanner.nextInt();
              int logm = scanner.nextInt();
              System.out.println(String.format(
                  "Chosen conflict criteria with n = %d and m = %d", 1 << logn,
                  1 << logm));
              criterion = new CollisionCriterion(logn, logm);
              lastUsedConflict = criterion;
              Map<Integer, Double> conflictMap = ((CollisionCriterion) criterion)
                  .conflictNum();
              for (Map.Entry<Integer, Double> kv : conflictMap.entrySet()) {
                System.out.println(String.format(
                    "Probability of having %d " + "collisions is %.6f",
                    kv.getKey(), kv.getValue()));
              }
              System.out.println();
            }
            break;
            default:
              System.out.println("Invalid token!");
              break;
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void solveAgain() {
    System.out.println(
        String.format("Result: %.2f", criterion.calculate(lastUsed)));
    System.out.println();
  }

  private static void solve2() throws FileNotFoundException {
    int mersenneM = 4095;
    int mersenneA = 1366;
    Task02 task02 = new Task02(
        mersenneM, mersenneA, 6, System.currentTimeMillis());
    lastUsed = task02;

    System.out.println(
        String.format("Mod:4095  Potential:%4d  ", task02.getPotential()));
    System.out.println(
        String.format("Result: %.2f", criterion.calculate(task02)));
  }

  private static void solve3() throws FileNotFoundException {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis() / 4 * 4 + 2);
    Task03Variable task03 = new Task03Variable(x0, 24);
    lastUsed = task03;

    System.out.println(
        String.format("Result: %.2f", criterion.calculate(task03)));
  }

  private static void solve3Conflict() {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis() / 4 * 4 + 2);
    Task03Variable task03 = new Task03Variable(
        x0, ((CollisionCriterion) criterion).getLogM());
    System.out.println(String.format("P: %.2f", criterion.calculate(task03)));
  }

  private static void solve4() throws FileNotFoundException {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis());
    Task04 task04 = new Task04(BigInteger.valueOf(3571),
                               BigInteger.valueOf(123), BigInteger.valueOf(7),
                               x0);
    lastUsed = task04;

    System.out.println(
        String.format("Result: %.2f", criterion.calculate(task04)));
  }

  private static void solve5() throws FileNotFoundException {
    int mersenneM = 4095;
    int mersenneA = 1366;
    Task02 genX = new Task02(mersenneM, mersenneA, 6,
                             System.currentTimeMillis());
    Task04 genY = new Task04(BigInteger.valueOf(3571), BigInteger.valueOf(123),
                             BigInteger.valueOf(7),
                             BigInteger.valueOf(98878796786L));

    Task05 task05 = new Task05(genX, genY, BigInteger.valueOf(mersenneM));
    lastUsed = task05;

    System.out.println(
        String.format("Result: %.2f", criterion.calculate(task05)));
  }

  private static void solve6() throws FileNotFoundException {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis());
    BigInteger p = BigInteger.valueOf(98162786123L).nextProbablePrime();
    BigInteger a = p.modPow(BigInteger.valueOf(2017),
                            BigInteger.valueOf(7117826381L)
                                      .nextProbablePrime());
    BigInteger b = p.xor(BigInteger.valueOf(System.currentTimeMillis()));
    System.out.println(
        "Params: " + p.toString() + " " + a.toString() + " " + b.toString() +
        " " + x0.toString());
    Task06 task06 = new Task06(p, a, b, x0);
    lastUsed = task06;

    System.out.println(
        String.format("Result: %.2f", criterion.calculate(task06)));
  }

  private static void solve7() {
    NativeGenerator generator = new NativeGenerator(
        (criterion instanceof CollisionCriterion)
          ? ((CollisionCriterion) criterion).getLogM()
          : 30, System.currentTimeMillis());
    System.out.println(
        String.format("Result: %.2f", criterion.calculate(generator)));
  }
}
