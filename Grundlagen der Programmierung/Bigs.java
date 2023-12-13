import java.util.Arrays;
import java.util.Collections;

class Bigs {
    // addiert die Ziffernfelder a und b
    public static int[] add(int[] a, int[] b) {
        int[] result = new int[Math.max(a.length, b.length) + 1];

        if (a.length < b.length)
            System.arraycopy(a, 0, result, 0, a.length);
        else {
            System.arraycopy(b, 0, result, 0, b.length);
            b = a;
        }

        for (int i = 0; i < b.length; i++) {
            int intermidiat_result = result[i] + b[i];
            result[i] = intermidiat_result % 10;
            result[i + 1] = result[i + 1] + intermidiat_result / 10;
        }

        if (result[result.length - 1] == 0)
            return Arrays.copyOfRange(result, 0, result.length - 1);
        else
            return result;
    }

    // gibt das Ziffernfeld n in lesbarer dezimaler Form aus
    static void print(int[] a) {
        for (int i = a.length; i > 0; i--) {
            System.out.print(a[i - 1]);
        }
        System.out.print("\r\n");
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

    // Rest des Ziffernfeldes n bei Division durch 10 (eine int-Zahl!)
    static int mod10(int[] n) {
        return n[0];
    }

    // ganzzahliger Quotient bei Division durch 10
    static int[] div10(int[] a) {
        int[] result = new int[a.length - 1];

        System.arraycopy(a, 1, result, 0, result.length);

        return result;
    }

    // Umwandlung einer int-Zahl in ein Ziffernfeld
    static int[] fromInt(int n) {
        int[] result = new int[(int) (Math.log10(n) + 1)];

        for (int i = 0; i < result.length; i++) {
            result[i] = n % 10;
            n = n / 10;
        }

        return result;
    }

    // kopiert den Wert von a
    static int[] copy(int[] n) {
        int[] result = new int[n.length];

        System.arraycopy(n, 0, result, 0, result.length);

        return result;
    }

    // multipliziert das Ziffernfeld a mit einer int-Zahl
    static int[] times(int[] a, int n) {
        int[] result = Null();

        while (n > 0) {
            result = add(result, a);
            n -= 1;
        }

        return result;
    }

    // multipliziert das Ziffernfeld n mit 10
    static int[] times10(int[] a) {
        int[] result = new int[a.length + 1];

        System.arraycopy(a, 0, result, 1, a.length);

        return result;
    }

    // multipliziert zwei Ziffernfelder
    static int[] times(int[] a, int[] b) {
        int[] result = Null();

        for (int i = 0; i < b.length; i++) {
            int power = b[i] * 10 ^ i;

            result = add(result, times(a, power));
        }

        return result;
    }

    // Quadratzahl eines Ziffernfeldes
    static int[] square(int[] a) {
        return times(a, a);
    }

    // Kubikzahl eines Ziffernfeldes
    static int[] cubic(int[] a) {
        return times(a, times(a, a));
    }

    // ist dieses Ziffernfeld ein Palindrom? Bemühen Sie sich um eine
    // Implementation,
    // die ohne ein weiteres Zahlenfeld auskommt !
    static boolean palindrom(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != a[a.length - i])
                return false;
        }

        return true;
    }

    // Test auf kleiner-Relation zweier Ziffernfelder: a < b ?
    static boolean less(int[] a, int[] b) {
        if (a.length < b.length)
            return false;

        if (a.length > b.length)
            return true;

        for (int i = a.length - 1; i > 0; i--) {
            if (a[i] < b[i])
                return false;
        }

        return true;
    }

    // Test auf Gleichheit zweier Ziffernfelder
    static boolean equal(int[] a, int[] b) {
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
    static boolean ok(int[] n) {
        for (int i : n) {
            if (i > 9)
                return false;
        }

        return true;
    }

    // gibt die (kleinste) Ziffer mit der groessten Haeufigkeit in n aus
    static void maxDigit(int[] a) {
        int[] digits = new int[10];

        for (int digit : a) {
            digits[digit] += 1;
        }

        int maxValue = digits[0];
        int maxDigit = 0;
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] > maxValue) {
                maxValue = digits[i];
                maxDigit = i;
            }
        }

        System.out.println(maxDigit);
    }

    public static void main(String[] s) {
        int[] a = One();

        for (int i = 0; i < 33222; ++i) {
            a = times(a, 2);
        }

        System.out.println("2^33222 hat " + a.length + " Stellen");
        print(a);
        System.out.println();

        int[] b = fromInt(13);
        int[] c = copy(b);

        for (int i = 1; i < 8978; ++i) {
            c = times(c, b);
        }

        System.out.println("13^8978 hat " + c.length + " Stellen");
        print(c);
        System.out.println();

        System.out.println(less(a, c)); // beantwortet die Frage aus der Aufgabenueberschrift
        maxDigit(a);
        maxDigit(c);
    }
}
