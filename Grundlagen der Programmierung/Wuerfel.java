public class Wuerfel {
    public static void main(String[] args) {
        if (args.length != 3) {
            fail();
        }

        int numberOfDiceFaces = Integer.parseInt(args[0]);
        int consecutiveWins = Integer.parseInt(args[1]);
        int numberOfSimulations = Integer.parseInt(args[2]);

        if (numberOfDiceFaces < 0 || consecutiveWins < 0 || numberOfSimulations < 0) {
            fail();
        }

        double winThreshold = ((double) numberOfDiceFaces - 1) / numberOfDiceFaces;
        int totalNumberOfThrows = 0;

        for (int simulation = 0; simulation < numberOfSimulations; simulation++) {
            int numberOfthrows = 0;
            int currentWins = 0;

            while (currentWins < consecutiveWins) {
                if (Math.random() >= winThreshold) {
                    currentWins++;
                } else {
                    currentWins = 0;
                }
                numberOfthrows++;
            }

            totalNumberOfThrows += numberOfthrows;
        }

        System.out.println((double) totalNumberOfThrows / numberOfSimulations);
    }

    static void fail() {
        System.out.println("Bitte geben Sie drei ganze positive Zahlen als Argumente an.");
        System.exit(-1);
    }
}
