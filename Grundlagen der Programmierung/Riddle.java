// https://github.com/michaelvanstraten/Mathematics-B.Sc./blob/master/Grundlagen%20der%20Programmierung/Riddle.java

public class Riddle {
  static int[] currentSolution, previousSolution;
  static int numberOfPairs, numberOfSlots, solutionCount;
  static boolean printSolutions, foundLastSolution;

  public static void main(String[] args) {
    // Validate that there is only one input argument
    if (args.length != 1) {
      fail();
    }

    // Parse the single input argument as our number of pairs to solve for
    numberOfPairs = Integer.parseInt(args[0]);
    numberOfSlots = numberOfPairs * 2;

    // Check that the input meets the requirements set by the problem
    if (numberOfPairs > 15 || numberOfPairs < 1) {
      fail();
    }

    // Set the flag to only print the number of solution if n >= 10
    printSolutions = numberOfPairs < 10;

    // Initialize our solution array as well as the array that stores the previously computed
    // solution (this will be used later)
    currentSolution = new int[numberOfSlots];
    previousSolution = new int[numberOfSlots];

    solve(numberOfPairs);

    // Print the results as describe in the problem
    if (solutionCount == 0) System.out.println("keine Loesung");
    else System.out.println(String.format("%d Loesungen", solutionCount));
  }

  static void solve(int numberOfPairs) {
    // For easy position our pair could possible fit into,
    for (int i = 0; i + numberOfPairs + 1 < numberOfSlots && !foundLastSolution; i++) {
      // check if the pair fits
      if ((currentSolution[i] == 0 && currentSolution[i + numberOfPairs + 1] == 0)) {
        // and if it fits set it.
        currentSolution[i] = currentSolution[i + numberOfPairs + 1] = numberOfPairs;

        // If we are not at the lowest pair, solve for the next lower pair
        if (numberOfPairs > 1) solve(numberOfPairs - 1);
        // and if we found the lowest pair check in the solution.
        else checkInSolution();

        // After checking in the solution set the pair position to try the next.
        currentSolution[i] = currentSolution[i + numberOfPairs + 1] = 0;
      }
    }
  }

  static void checkInSolution() {
    // Check if the solution we just found is a Palindrom to the previous one. If that happens we
    // know that from this point on the solution will repeat as Palindroms.
    foundLastSolution = true;
    for (int i = 0, j = numberOfSlots - 1; foundLastSolution && i < j; ) {
      foundLastSolution = currentSolution[i] == previousSolution[j];
      i++;
      j--;
    }

    // If we have not found the last solution we would like to record it and possible print it.
    if (!foundLastSolution) {
      solutionCount++;
      System.arraycopy(currentSolution, 0, previousSolution, 0, currentSolution.length);
      if (printSolutions) {
        printSolution();
      }
    }
  }

  static void printSolution() {
    // For performance reasons we search for solution from highest to lowest pair (smaller search
    // space) but the problem wants us to print solution found from starting at the lowest to
    // highest pair, this is no problem since both are mirror images of one another, just reverse
    // print it.
    if (currentSolution[0] < currentSolution[numberOfSlots - 1]) {
      for (int i = 0; i < currentSolution.length; i++) {
        System.out.print(currentSolution[i]);
      }
    } else {
      for (int i = numberOfSlots - 1; i >= 0; i--) {
        System.out.print(currentSolution[i]);
      }
    }
    System.out.println();
  }

  private static void fail() {
    System.out.println("Bitte gebe eine ganze positivbe Zahl kleiner als 16 als Parameter an.");
    System.exit(0);
  }
}
