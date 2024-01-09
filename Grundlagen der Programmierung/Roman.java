// https://github.com/michaelvanstraten/Mathematics-B.Sc./blob/master/Grundlagen%20der%20Programmierung/Roman.java

public class Roman {
  static int I = 1;
  static int V = 5;
  static int X = 10;
  static int L = 50;
  static int C = 100;
  static int D = 500;
  static int M = 1000;

  static String roman(int n) {
    if (n >= M) return "M" + roman(n - M);
    if (n >= M - C) return "CM" + roman(n - M + C);
    if (n >= D) return "D" + roman(n - D);
    if (n >= D - C) return "CD" + roman(n - D + C);
    if (n >= C) return "C" + roman(n - C);
    if (n >= C - X) return "XC" + roman(n - C + X);
    if (n >= L) return "L" + roman(n - L);
    if (n >= L - X) return "XL" + roman(n - L + X);
    if (n >= X) return "X" + roman(n - X);
    if (n >= X - I) return "IX" + roman(n - X + I);
    if (n >= V) return "V" + roman(n - V);
    if (n >= V - I) return "IV" + roman(n - V + I);
    if (n >= 1) return "I" + roman(n - 1);
    return "";
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Bitte eine Zahl als Parameter angeben.");
      System.exit(0);
    }

    int N = Integer.parseInt(args[0]);

    // Behandlung fehlender oder falscher Eingabeparameter
    assert (1 <= N && N <= 5000);

    System.out.println(roman(N));
  }
}
