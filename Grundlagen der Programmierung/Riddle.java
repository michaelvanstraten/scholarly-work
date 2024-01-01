public class Riddle {
  static int[] currentSolution;
  static int solutionCount;
  static boolean countOnly;

  public static void main(String[] args) {
    if (args.length != 1) {
      fail();
    }

    int N = Integer.parseInt(args[0]);

    if (!(0 < N && N <= 15)) {
      fail();
    }

    currentSolution = new int[N * 2];
    countOnly = N > 9;

    solve();
    if (solutionCount == 0) System.out.println("keine Loesung");
    else System.out.println(String.format("%d Loesungen", solutionCount));
  }

  public static boolean isPossible(int i, int n) {
    if (!(currentSolution[i] == 0
        && currentSolution.length > i + n + 1
        && currentSolution[i + n + 1] == 0)) return false;

    for (int j = 0; j < currentSolution.length; j++) {
      if (currentSolution[j] == n) return false;
    }

    return true;
  }

  static void solve() {
    for (int i = 0; i < currentSolution.length; i++) {
      if (currentSolution[i] == 0) {
        for (int n = 1; n <= currentSolution.length / 2; n++) {
          if (isPossible(i, n)) {
            currentSolution[i] = n;
            currentSolution[i + n + 1] = n;
            solve();
            currentSolution[i] = 0;
            currentSolution[i + n + 1] = 0;
          }
        }
        return;
      }
    }

    solutionCount++;
    if (!countOnly) {
      printSolution();
    }
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
