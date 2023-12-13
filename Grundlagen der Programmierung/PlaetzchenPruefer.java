public class PlaetzchenPruefer {
    public static int pruefePlaetzchen(int anzahlPlaetzchen) {
        int numberOfCookies = anzahlPlaetzchen;

        if (numberOfCookies < 2)
            return numberOfCookies;

        if (numberOfCookies % 2 == 0) {
            numberOfCookies = (numberOfCookies / 2) - 1;

            return 2 + pruefePlaetzchen(numberOfCookies);
        } else
            return 1 + pruefePlaetzchen(numberOfCookies - 1);

    }

    public static void main(String[] args) {
        // Perform input validation
        if (args.length != 1)
            fail();

        // Parse the number of cookies to test
        int numberOfCookies = Integer.parseInt(args[0]);

        if (numberOfCookies < 0)
            fail();

        System.out.println(pruefePlaetzchen(numberOfCookies));
    }

    private static void fail() {
        System.out.println("Bitte eine Zahl, die Anzahl an zu testenden Plätzen, als Argument an.");
        System.exit(1);
    }
}
