public class GGT {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Bitte gebe zwei ganze positive Zahlen als Argumente an.");
            System.exit(-1);
        }

        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        if (!(m > 0 && n > 0)) {
            System.out.println("Nur positive ganze Zahlen sind als Argumente erlaubt.");
            System.exit(-1);
        }

        int r = -1;
        while (r != 0) {
            if (m < n) {
                m = m + n;
                n = m - n;
                m = m - n;
            }

            r = m - n;
            m = n;
            n = r;
        }

        System.out.println(String.format("ggT(%s, %s) = %d", args[0], args[1], m));
    }
}
