import java.math.BigInteger;

public class Looping {
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
        // do input validation to check for correct argument
        if (args.length < 1 || (args.length == 2 && !args[1].equals("x")) || args.length > 2) {
            fail();
        }

        long N = Long.valueOf(args[0]);
        boolean checkSmallestTruePalindrome = args.length == 2;

        // create instance of our class since we need a non-static method on it later
        Looping instance = new Looping();

        for (int n = 0; n < N; n++) {
            // convert our current number into a arbitrary precision integer 
            BigInteger number = BigInteger.valueOf(n);

            // calculated palindrome while only iteration a maximum of 100 times
            Palindrome palindrome = instance.calculatePalindrome(number, 100);

            if (palindrome == null) {
                if (!checkSmallestTruePalindrome) {
                    System.out.println(n);
                }
                continue;
            }

            // check if we would overflow a long value
            if (palindrome.value.compareTo(MAX_VALUE_LONG) > 0) {
                if (!checkSmallestTruePalindrome) {
                    System.out.println(n);
                } else {
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
        for (int i = 0; i <= maxIterations; i++) {
            number = number.add(reverse(number));

            if (isPalindrome(number)) {
                return new Palindrome(i, number);
            }
        }

        return null;
    }

    static boolean isPalindrome(BigInteger number) {
        return number.equals(reverse(number));
    }

    static BigInteger reverse(BigInteger number) {
        BigInteger reversedNumber = BigInteger.ZERO;

        while (!number.equals(BigInteger.ZERO)) {
            // shift our reversed number to the left and add the single digit of our number to convert
            reversedNumber = reversedNumber.multiply(BY_TEN).add(number.mod(BY_TEN));

            // drop the single digit of our number to convert, this will result in a 0 if the number only has one digit
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
