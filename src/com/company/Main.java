package com.company;

import com.company.lab01.*;
import com.company.lab02.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

  private static Criterion criterion;
  private static List<BigInteger> lastUsed = null;

  public static void main(String[] args) {
    try {
      String token = "enter";
      Scanner scanner = new Scanner(System.in);
      while (!token.equals("exit")) {
        System.out.println("Please enter next command:");
        token = scanner.next();
        if (token.equals("solve")) {
          int num = scanner.nextInt();
          switch (num) {
            case 1:
              System.out.println("Sorry, it doesn't work here!");
              break;
            case 2:
              System.out.println("Solving 2...");
              solve2();
              break;
            case 3:
              System.out.println("Solving 3...");
              if (criterion instanceof PermutationCriterion)
                solve3();
              else
                solve3Conflict();
              break;
            case 4:
              System.out.println("Solving 4...");
              solve4();
              break;
            case 5:
              System.out.println("Solving 5...");
              solve5();
              break;
            case 6:
              System.out.println("Solving 6...");
              solve6();
              break;
            case 7:
              if (criterion instanceof ConflictCriterion)
                solve7();
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
            }
            break;
            case 2: {
              int logn = scanner.nextInt();
              int logm = scanner.nextInt();
              System.out.println(String.format(
                  "Chosen conflict criteria with n = %d and m = %d", 1 << logn, 1 << logm));
              criterion = new ConflictCriterion(logn, logm);
              Map<Integer, Double> conflictMap = ((ConflictCriterion) criterion)
                  .conflictNum();
              for (Map.Entry<Integer, Double> kv : conflictMap.entrySet()) {
                System.out.println(String.format(
                    "Probability of having %d " + "collisions is %.6f",
                    kv.getKey(), kv.getValue()));
              }
              System.out.println();
            }
            break;
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void solveAgain() {
    System.out.println(criterion.calculate(lastUsed));
    System.out.println();
  }

  private static void solve2() throws FileNotFoundException {
    int mersenneM = 4095;
    int mersenneA = 1366;
    Task02 task02 = new Task02(
        mersenneM, mersenneA, 6, System.currentTimeMillis());
    List<BigInteger> list = task02.generateSequence();
    lastUsed = new ArrayList<>(list);

    System.out.print(
        String.format("Mod:4095  Potential:%4d  ", task02.getPotential()));
    System.out.println(String.format("Period:%4d", list.size()));
    System.out.println(criterion.calculate(list));
    System.out.println();

    File logFile = new File("task02.txt");
    PrintWriter logger = new PrintWriter(logFile);
    for (BigInteger i : list) {
      logger.print(i);
      logger.print(" ");
    }
    logger.flush();
    logger.close();
  }

  private static void solve3() throws FileNotFoundException {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis());
    Task03 task03 = new Task03(x0);
    List<BigInteger> list = task03.generateSequence();
    lastUsed = new ArrayList<>(list);

    System.out.println(String.format("Period: %d", list.size()));
    System.out.println(
        String.format("Chi squared: %.2f", criterion.calculate(list)));

/*
    File logFile = new File("task03.txt");
    PrintWriter logger = new PrintWriter(logFile);
    for (BigInteger i : list) {
      logger.print(i);
      logger.print(" ");
    }
    logger.flush();
    logger.close();
*/
  }

  private static void solve3Conflict() {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis());
    Task03Conflict task03 = new Task03Conflict(
        x0, ((ConflictCriterion) criterion).getLogM());
    System.out.println(String.format("P: %.2f",
                                     ((ConflictCriterion) criterion).calculate(
                                         task03)));
  }

  private static void solve4() throws FileNotFoundException {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis());
    Task04 task04 = new Task04(BigInteger.valueOf(3571),
                               BigInteger.valueOf(123), BigInteger.valueOf(7),
                               x0);
    List<BigInteger> list = task04.generateSequence();
    lastUsed = new ArrayList<>(list);

    System.out.println(String.format("Period: %d", list.size()));
    System.out.println(
        String.format("Chi squared: %.2f", criterion.calculate(list)));
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
    List<BigInteger> list = task05.generateSequence();
    lastUsed = new ArrayList<>(list);

    System.out.println(String.format("Period: %d", list.size()));
    System.out.println(
        String.format("Chi squared: %.2f", criterion.calculate(list)));
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
    List<BigInteger> list = task06.generateSequence();
    lastUsed = new ArrayList<>(list);

    System.out.println(String.format("Period: %d", list.size()));
    System.out.println(
        String.format("Chi squared: %.2f", criterion.calculate(list)));
  }

  private static void solve7() {
    ConflictCriterion conflictCriterion = (ConflictCriterion) criterion;
    NativeGenerator generator = new NativeGenerator(conflictCriterion.getLogM());
    System.out.println(String.format("P: %.2f",
                                     conflictCriterion.calculate(generator)));

  }
}
