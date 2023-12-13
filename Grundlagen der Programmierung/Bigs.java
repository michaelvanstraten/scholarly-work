// https://github.com/michaelvanstraten/Mathematics-B.Sc./blob/master/Grundlagen%20der%20Programmierung/Bigs.java
import java.util.Arrays;

class Bigs {
    // addiert die Ziffernfelder a und b
    public static int[] add(int[] a, int[] b) {
        // The sum of two numbers with decimal digits n, m has at most max(n, m) + 1
        // digits, so we allocate an array of that size.
        int[] result = new int[Math.max(a.length, b.length) + 1];

        // We want to add similar elements from the two lists a, b while also not
        // allocating another array at the same time. This makes sure that b is always
        // our longest list.
        if (a.length < b.length)
            System.arraycopy(a, 0, result, 0, a.length);
        else {
            System.arraycopy(b, 0, result, 0, b.length);
            b = a;
        }

        // Since b is our longest list, we can safely iterate over it and assume that we
        // add every element from a and b
        for (int i = 0; i < b.length; i++) {
            // Add the digit from the smaller list (result[i]) with the corresponding
            // element from b. Result[i] will be 0 if there are no more elements from a,
            // and store the result
            int intermediateResult = result[i] + b[i];
            // Calculate the ith digit of our sum
            result[i] = intermediateResult % 10;
            // Put any overflowing value into the next digit
            result[i + 1] += intermediateResult / 10;
        }

        // We allocated one more digit at the beginning for cases where the resulting
        // sum is one bigger than max(n, m)
        // This is not always the case, so we will remove it here in that case
        if (result[result.length - 1] == 0)
            return Arrays.copyOfRange(result, 0, result.length - 1);
        else
            return result;
    }

    // gibt das Ziffernfeld a in lesbarer dezimaler Form aus
    static void print(int[] a) {
        // For every digit in the list, note here reversed since the number is stored
        // using big-endian notation
        for (int i = a.length; i > 0; i--) {
            // Print the corresponding digit
            System.out.print(a[i - 1]);
        }
        // Print a newline character
        System.out.println();
    }

    // konstruiert ein einstelliges Ziffernfeld aus d
    static int[] digit(int d) {
        return new int[] { d % 10 };
    }

    // konstruiert das Ziffernfeld, welches den Wert Null repraesentiert
    static int[] Null() {
        return new int[] { 0 };
    }

    // konstruiert das Ziffernfeld, welches den Wert Eins repraesentiert
    static int[] One() {
        return new int[] { 1 };
    }

    // // Rest des Ziffernfeldes n bei Division durch 10 (eine int-Zahl!)
    static int mod10(int[] a) {
        return a[0];
    }

    // ganzzahliger Quotient bei Division durch 10
    static int[] div10(int[] a) {
        int[] result = new int[a.length - 1];

        // An integer divided by ten, however large, is just the singles digit removed.
        // Here we basically shift our number to the right by 1 to remove it.
        System.arraycopy(a, 1, result, 0, result.length);

        return result;
    }

    // Umwandlung einer int-Zahl in ein Ziffernfeld
    static int[] fromInt(int n) {
        // Calculate the number of base 10 digits of n
        int[] result = new int[(int) (Math.log10(n) + 1)];

        // For every digit we want to calculate
        for (int i = 0; i < result.length; i++) {
            // Get the singles digit of n
            result[i] = n % 10;
            // Drop the singles digit of n
            n = n / 10;
        }

        return result;
    }

    // kopiert den Wert von a
    static int[] copy(int[] a) {
        int[] result = new int[a.length];

        System.arraycopy(a, 0, result, 0, result.length);

        return result;
    }

    // multipliziert das Ziffernfeld a mit einer int-Zahl
    static int[] times(int[] a, int n) {
        int[] result = Null();

        // Multiplication can be performed using repeated addition
        // So we add a n times with itself. If n = 0, result will also be 0
        while (n > 0) {
            result = add(result, a);
            n--;
        }

        return result;
    }

    // multipliziert das Ziffernfeld a mit 10
    static int[] times10(int[] a) {
        int[] result = new int[a.length + 1];

        // Times ten is the same as divide by ten, except shifting the digits to the
        // left
        System.arraycopy(a, 0, result, 1, a.length);

        return result;
    }

    // multipliziert zwei Ziffernfelder
    static int[] times(int[] a, int[] b) {
        int[] result = Null();

        for (int i = 0; i < b.length; i++) {
            int power = b[i] * (int) Math.pow(10, i);

            result = add(result, times(a, power));
        }

        return result;
    }

    // Quadratzahl eines Ziffernfeldes
    static int[] square(int[] a) {
        // Squaring a number is just multiplying it by itself
        return times(a, a);
    }

    // Kubikzahl eines Ziffernfeldes
    static int[] cubic(int[] a) {
        // Cubing a number is just multiplying it by itself two times
        return times(a, times(a, a));
    }

    // ist dieses Ziffernfeld ein Palindrom? Bemühen Sie sich um eine
    // Implementation, die ohne ein weiteres Zahlenfeld auskommt !
    static boolean palindrom(int[] a) {
        // For digit in a up to the half point
        for (int i = 0; i < a.length / 2; i++) {
            // Check if it is equal with its counterpart
            if (a[i] != a[a.length - 1 - i])
                // If not, do an early return
                return false;
        }

        return true;
    }

    // Test auf kleiner-Relation zweier Ziffernfelder: a < b ?
    static boolean less(int[] a, int[] b) {
        // If a has fewer digits than b, it is guaranteed to be smaller
        if (a.length < b.length)
            return true;

        // If a has more digits than b, it is guaranteed to be bigger
        if (a.length > b.length)
            return false;

        // We can conclude from the above that a and b must have the same length, so
        // let's compare every digit in reverse order from big to small.
        // You can think of going deeper in this loop as a and b being possibly closer
        // and closer together.
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] < b[i])
                return true;
            else if (a[i] > b[i])
                return false;
        }

        return false;
    }

    // Test auf Gleichheit zweier Ziffernfelder
    static boolean equal(int[] a, int[] b) {
        // If they don't have the same length, they are not equal
        if (a.length != b.length)
            return false;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    // Test auf Korrektheit eines Ziffernfeldes: Feld existiert und enthaelt
    // mindenstens eine Ziffer, alle Positionen liegen zwischen 0 und 9
    // keine fuehrenden Nullen (ausser bei Null selbst)
    static boolean ok(int[] a) {
        if (a.length > 1 && a[a.length - 1] == 0)
            return false;

        for (int digit : a) {
            if (digit < 0 || digit > 9)
                return false;
        }

        return true;
    }

    // gibt die (kleinste) Ziffer mit der groessten Haeufigkeit in a aus
    static void maxDigit(int[] a) {
        // We have ten digits, 0 through 9, so let's allocate an array of that size
        int[] digits = new int[10];

        // For every digit in a
        for (int digit : a) {
            // Increment the count for that encountered digit
            digits[digit]++;
        }

        int maxValue = digits[0];
        int maxDigit = 0;
        // For every digit we have
        for (int i = 0; i < digits.length; i++) {
            // Check if it has more occurrence than the currently selected
            if (digits[i] > maxValue) {
                // If so, set our new maximum
                maxValue = digits[i];
                // And update the digit
                maxDigit = i;
            }
        }

        System.out.println(String.format("Die häufigste ziffer ist: %d", maxDigit));
    }

    public static void main(String[] args) {
        int[] powerOfTwo = One();

        for (int i = 0; i < 33222; ++i) {
            powerOfTwo = times(powerOfTwo, 2);
        }

        System.out.println("2^33222 hat " + powerOfTwo.length + " Stellen");
        print(powerOfTwo);
        System.out.println();

        int[] base = fromInt(13);
        int[] result = copy(base);

        for (int i = 1; i < 8978; ++i) {
            result = times(result, base);
        }

        System.out.println("13^8978 hat " + result.length + " Stellen");
        print(result);
        System.out.println();

        System.out.println(less(powerOfTwo, result)); // Answers the question from the assignment title
        maxDigit(powerOfTwo);
        maxDigit(result);
    }
}
