public class Riddle {
  static int[] currentSolution, previosSolution;
  static int numberOfPairs, numberOfSlots, solutionCount;
  static boolean printSolutions;

  public static void main(String[] args) {
    if (args.length != 1) {
      fail();
    }

    numberOfPairs = Integer.parseInt(args[0]);
    numberOfSlots = numberOfPairs * 2;

    if (numberOfPairs > 15 || numberOfPairs < 1) {
      fail();
    }

    printSolutions = numberOfPairs < 10;

    currentSolution = new int[numberOfSlots];
    previosSolution = new int[numberOfSlots];

    solve(numberOfPairs);

    if (solutionCount == 0) System.out.println("keine Loesung");
    else System.out.println(String.format("%d Loesungen", solutionCount));
  }

  static boolean solve(int numberOfPairs) {
    if (numberOfPairs == 0) return checkInSolution();

    for (int i = 0; i + numberOfPairs + 1 < numberOfSlots; i++) {
      if ((currentSolution[i] == 0 && currentSolution[i + numberOfPairs + 1] == 0)) {
        currentSolution[i] = currentSolution[i + numberOfPairs + 1] = numberOfPairs;
        if (solve(numberOfPairs - 1)) return true;
        currentSolution[i] = currentSolution[i + numberOfPairs + 1] = 0;
      }
    }

    return false;
  }

  static boolean checkInSolution() {
    boolean foundLastSolution = true;
    for (int i = 0, j = numberOfSlots - 1; foundLastSolution && i < j; ) {
      foundLastSolution = currentSolution[i] == previosSolution[j];
      i++;
      j--;
    }

    if (!foundLastSolution) {
      solutionCount++;
      for (int i = 0; i < numberOfSlots; i++) {
        previosSolution[i] = currentSolution[i];
      }
      if (printSolutions) {
        printSolution();
      }
    }

    return foundLastSolution;
  }

  static void printSolution() {
    for (int i = 0; i < currentSolution.length; i++) {
      System.out.print(currentSolution[i]);
    }
    System.out.println();
  }

  private static void fail() {
    System.out.println("Bitte gebe eine ganze positivbe Zahl kleiner als 16 als Parameter an.");
    System.exit(0);
  }
}
