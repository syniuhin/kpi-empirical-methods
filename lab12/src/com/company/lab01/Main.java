package com.company.lab01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

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
              System.out.println("Solving 1...");
              solve1();
              break;
            case 2:
              System.out.println("Solving 2...");
              solve2();
              break;
            case 3:
              System.out.println("Solving 3...");
              solve3();
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
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void solve1() {
    Task01 task01 = new Task01();
    BigInteger x = BigInteger.valueOf(6065038420L);
    try {
      File file = new File("task01.out");
      if (!file.exists()) {
        file.createNewFile();
      }

      PrintWriter pw = new PrintWriter(file);
      task01.solve(x, pw);
      pw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void solve2() throws FileNotFoundException {
    Task02 task02 = new Task02(17, 52, 6, 7);
    System.out.println(String.format("Mod:  17  Potential:%4d  Period:%4d",
                                     task02.getPotential(),
                                     task02.getPeriod()));
    System.out.println();

    int mersenneM = 4095;
    int mersenneA = 1366;
    task02 = new Task02(mersenneM, mersenneA, 6, System.currentTimeMillis());
    System.out.print(
        String.format("Mod:4095  Potential:%4d  ", task02.getPotential()));
    System.out.println(String.format("Period:%4d", task02.getPeriod()));
    System.out.println();

    for (int i = 2; i < mersenneM; ++i) {
      if (mersenneM % i == 0) {
        BigInteger bigI = BigInteger.valueOf(i);
        System.out.print(String.format("Mod:%4d  ", i));
        System.out.print(
            String.format("Potential:%4d  ", task02.getPotential(bigI)));
        System.out.println(String.format("Period:%4d", task02.getPeriod(bigI)));
      }
    }
  }

  private static void solve3() throws FileNotFoundException {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis());
    Task03 task03 = new Task03(x0);
    System.out.println(String.format("Period: %d", task03.getPeriod()));
  }

  private static void solve4() throws FileNotFoundException {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis());
/*
    Task04 task04 = new Task04(BigInteger.valueOf(3571),
                               BigInteger.valueOf(123),
                               BigInteger.valueOf(7), x0);
    System.out.println(String.format("Period: %d", task04.getPeriod()));
*/
    Task04 task04 = new Task04(BigInteger.valueOf(19),
                               BigInteger.valueOf(5),
                               BigInteger.valueOf(5),
                               BigInteger.valueOf(3));
    task04.getPeriod();
  }

  private static void solve5() throws FileNotFoundException {
    int mersenneM = 4095;
    int mersenneA = 1366;
    Task02 genX = new Task02(mersenneM, mersenneA, 6,
                             System.currentTimeMillis());
    Task04 genY = new Task04(BigInteger.valueOf(3571),
                             BigInteger.valueOf(123),
                             BigInteger.valueOf(7),
                             BigInteger.valueOf(98878796786L));

    Task05 task05 = new Task05(genX, genY, BigInteger.valueOf(mersenneM));
    System.out.println(String.format("Period: %d", task05.getPeriod()));
  }

  private static void solve6() throws FileNotFoundException {
    BigInteger x0 = BigInteger.valueOf(System.currentTimeMillis());
    BigInteger p = BigInteger.valueOf(98162786123L).nextProbablePrime();
    BigInteger a = p.modPow(BigInteger.valueOf(2017),
                            BigInteger.valueOf(7117826381L)
                                      .nextProbablePrime());
    BigInteger b = p.xor(BigInteger.valueOf(System.currentTimeMillis()));
    System.out.println("Params: " + p.toString() + " " + a.toString() + " " + b
        .toString() + " " + x0.toString());
    Task06 task06 = new Task06(p, a, b, x0);
    System.out.println(String.format("Period: %d", task06.getPeriod()));
  }
}
