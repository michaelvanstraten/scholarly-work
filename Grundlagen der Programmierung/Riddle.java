public class Riddle {
  static int[] pairPositions, currentSolution, previosSolution;
  static int currentPair, pairPosition, numberOfPairs, numberOfSlots, solutionCount;
  static boolean foundLastSolution, printSolutions;

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
    pairPositions = new int[numberOfPairs];

    currentPair = 1;

    while (!foundLastSolution) {
      findPositionForCurrentPair();
      if (foundPair()) {
        currentSolution[pairPosition] =
            currentSolution[pairPosition + currentPair + 1] = currentPair;
        pairPositions[currentPair - 1] = pairPosition;

        if (currentPair < numberOfPairs) tryHigherPair();
        else checkInSolution();
      } else tryLowerPair();
    }

    if (solutionCount == 0) System.out.println("keine Loesung");
    else System.out.println(String.format("%d Loesungen", solutionCount));
  }

  static void checkInSolution() {
    foundLastSolution = true;
    for (int i = 0, j = numberOfSlots - 1; foundLastSolution && i < j; ) {
      foundLastSolution = currentSolution[i] == previosSolution[j];
      i++;
      j--;
    }

    if (!foundLastSolution) {
      solutionCount++;
      for (pairPosition = 0; pairPosition < numberOfSlots; pairPosition++) {
        previosSolution[pairPosition] = currentSolution[pairPosition];
      }
      if (printSolutions) {
        printSolution();
      }
      removeCurrentPair();
      pairPosition++;
    }
  }

  static void tryHigherPair() {
    currentPair++;
    pairPosition = 0;
  }

  static void tryLowerPair() {
    currentPair--;

    foundLastSolution = currentPair < 1;
    if (!foundLastSolution) {
      removeCurrentPair();
      pairPosition++;
    }
  }

  static void findPositionForCurrentPair() {
    while (!foundPair() && pairPosition + currentPair + 1 < numberOfSlots) {
      pairPosition++;
    }
  }

  static boolean foundPair() {
    return pairPosition + currentPair + 1 < numberOfSlots
        && (currentSolution[pairPosition] == 0
            && currentSolution[pairPosition + currentPair + 1] == 0);
  }

  static void removeCurrentPair() {
    pairPosition = pairPositions[currentPair - 1];
    currentSolution[pairPosition] = currentSolution[pairPosition + currentPair + 1] = 0;
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
