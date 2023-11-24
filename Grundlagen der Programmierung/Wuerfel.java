public class Wuerfel {
    public static void main(String[] args) {
        // check for the correct number of arguments
        if (args.length != 3) {
            fail();
        }

        int numberOfDiceFaces = Integer.parseInt(args[0]);
        int consecutiveWins = Integer.parseInt(args[1]);
        int numberOfSimulations = Integer.parseInt(args[2]);

        // check that our parameters are not smaller than 0 since this only works for
        // values above or equal to 0
        if (numberOfDiceFaces < 0 || consecutiveWins < 0 || numberOfSimulations < 0) {
            fail();
        }

        // cast the number of dice Faces to a double to get float division and calculate
        // the win probability threshold
        double winThreshold = ((double) numberOfDiceFaces - 1) / numberOfDiceFaces;
        int totalNumberOfThrows = 0;

        for (int simulation = 0; simulation < numberOfSimulations; simulation++) {
            int numberOfthrows = 0;
            int currentWins = 0;

            // while we have not won as many consecutive times as we want
            while (currentWins < consecutiveWins) {
                // role the dice and check if we wan
                if (Math.random() >= winThreshold) {
                    currentWins++;
                } else {
                    // if we have not won
                    currentWins = 0;
                }
                numberOfthrows++;
            }

            // sum up the number of throws we needed this simulation step with the previos
            // ones
            totalNumberOfThrows += numberOfthrows;
        }

        // normilize the number of throws and print it
        System.out.println((double) totalNumberOfThrows / numberOfSimulations);
    }

    static void fail() {
        System.out.println("Bitte geben Sie drei ganze positive Zahlen als Argumente an.");
        System.exit(-1);
    }
}
