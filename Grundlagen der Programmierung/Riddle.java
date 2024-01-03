public class Riddle {
  static int[] pairPositions, currentSolution, previosSolution;
  static int pair, pairPosition, numberOfPairs, numberOfSlots, solutionCount;
  static boolean finished, printSolutions;

  public static void main(String[] args) {
    if (args.length != 1) {
      fail();
    }

    numberOfPairs = Integer.parseInt(args[0]);
    numberOfSlots = numberOfPairs * 2;

    if (numberOfPairs > 15 || numberOfPairs < 0) {
      fail();
    }

    currentSolution = new int[numberOfSlots];
    previosSolution = new int[numberOfSlots];
    pairPositions = new int[numberOfPairs];
    printSolutions = numberOfPairs < 10;

    pair = 1;
    while (!finished) {
      solve();
    }

    if (solutionCount == 0) System.out.println("keine Loesung");
    else System.out.println(String.format("%d Loesungen", solutionCount));
  }

  static void solve() {
    boolean foundPosition = pairFits(pair);
    while (!foundPosition && pairPosition + pair + 1 < numberOfSlots) {
      pairPosition++;
      foundPosition = pairFits(pair);
    }

    if (foundPosition) {
      currentSolution[pairPosition] = currentSolution[pairPosition + pair + 1] = pair;
      if (pair < numberOfPairs) {
        pairPositions[pair - 1] = pairPosition;
        pairPosition = 0;
        pair++;
      } else {
        boolean isDuplicate = true;
        for (int i = 0, j = numberOfSlots - 1; isDuplicate && i < j; ) {
          isDuplicate = currentSolution[i] == previosSolution[j];
          i++;
          j--;
        }

        if (isDuplicate) {
          finished = true;
          return;
        }
       
        solutionCount++;
        System.arraycopy(currentSolution, 0, previosSolution, 0, numberOfSlots);
        if (printSolutions) {
          printSolution();
        }
        currentSolution[pairPosition] = currentSolution[pairPosition + pair + 1] = 0;
        pairPosition++;
      }
    } else if (pair > 1) {
      pair--;
      pairPosition = pairPositions[pair - 1];
      currentSolution[pairPosition] = currentSolution[pairPosition + pair + 1] = 0;
      pairPosition++;
    }
  }

  static boolean pairFits(int pair) {
    return pairPosition + pair + 1 < numberOfSlots
        && (currentSolution[pairPosition] == 0 && currentSolution[pairPosition + pair + 1] == 0);
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
