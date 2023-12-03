// https://github.com/michaelvanstraten/Mathematics-B.Sc./blob/master/Grundlagen%20der%20Programmierung/Looping.java

import java.math.BigInteger;

public class Looping {
    // this helper class is later used to wrap and return our calculated values
    public static class Palindrome {
        int iterations;
        BigInteger value;

        public Palindrome(int iterations, BigInteger value) {
            this.iterations = iterations;
            this.value = value;
        }
    }

    static BigInteger BY_TEN = BigInteger.valueOf(10);
    static BigInteger MAX_VALUE_LONG = BigInteger.valueOf(Long.MAX_VALUE);

    public static void main(String[] args) {
        // do input validation to check for correct arguments
        if (args.length < 1 || (args.length == 2 && !args[1].equals("x")) || args.length > 2) {
            fail();
        }

        long N = Long.valueOf(args[0]);
        boolean checkSmallestTruePalindrome = args.length == 2;

        // create instance of our class since we need a non-static method on it later
        Looping instance = new Looping();

        for (int n = 0; n < N; n++) {
            // convert our current number into an arbitrary precision integer
            BigInteger number = BigInteger.valueOf(n);

            // calculate palindrome while only iteration a maximum of 100 times
            Palindrome palindrome = instance.calculatePalindrome(number, 100);

            if (palindrome == null) {
                if (!checkSmallestTruePalindrome) {
                    System.out.println(n);
                }
                continue;
            }

            // check if we would overflow a long value
            if (palindrome.value.compareTo(MAX_VALUE_LONG) > 0) {
                // emulate the long implementation of just assuming that the number does not
                // generate a palindrome if it overflow
                if (!checkSmallestTruePalindrome) {
                    System.out.println(n);
                } else {
                    // we found our smallest number that would overflow a long value but it is an
                    // actual palindrome, print it and exit the program
                    System.out.println(String.format("%d braucht %d Iterationen bis zum Palindrom %d", n,
                            palindrome.iterations, palindrome.value));
                    System.exit(0);
                }
            }
        }

        if (checkSmallestTruePalindrome) {
            System.out.println("alle Zahlen werden auch durch Abbruch per Überlauf gefunden");
        }
    }

    Palindrome calculatePalindrome(BigInteger number, int maxIterations) {
        for (int i = 1; i <= maxIterations; i++) {
            // do our iteration calculation of reversing the given number and then adding
            // the reversed version of
            number = number.add(reverse(number));

            // if our new number is a palindrome, return it while quitting the loop
            if (isPalindrome(number)) {
                return new Palindrome(i, number);
            }
        }

        // return null if no palindrome was found
        return null;
    }

    static boolean isPalindrome(BigInteger number) {
        // Is the number a palindrome? Well, reverse it and check if they are equal.
        return number.equals(reverse(number));
    }

    static BigInteger reverse(BigInteger number) {
        BigInteger reversedNumber = BigInteger.ZERO;

        while (!number.equals(BigInteger.ZERO)) {
            // shift our reversed number to the left and then add the singles digit of our
            // number
            reversedNumber = reversedNumber.multiply(BY_TEN).add(number.mod(BY_TEN));

            // drop the singles digit of our number, this will result in a 0 if the number
            // only has one digit
            number = number.divide(BY_TEN);
        }

        return reversedNumber;
    }

    static void fail() {
        System.out.println(
                "Bitte geben Sie entweder nur eine Obergrenze oder eine Obergrenze sowie \"x\" als Parameter an.");
        System.exit(-1);
    }
}
