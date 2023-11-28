import java.util.ArrayList;
import java.util.Collections;

public class Banner {
    // Definition for character set of 0123456789-
    static String[][] FONT_CHARS = { {
            "  ###   ",
            " #   #  ",
            "#     # ",
            "#     # ",
            "#     # ",
            " #   #  ",
            "  ###   " },
            {
                    "   #    ",
                    "  ##    ",
                    " # #    ",
                    "   #    ",
                    "   #    ",
                    "   #    ",
                    " #####  " },
            {
                    " #####  ",
                    "#     # ",
                    "      # ",
                    " #####  ",
                    "#       ",
                    "#       ",
                    "####### " },
            {
                    " #####  ",
                    "#     # ",
                    "      # ",
                    " #####  ",
                    "      # ",
                    "#     # ",
                    " #####  " },
            {
                    "#       ",
                    "#    #  ",
                    "#    #  ",
                    "#    #  ",
                    "####### ",
                    "     #  ",
                    "     #  " },
            {
                    "####### ",
                    "#       ",
                    "#       ",
                    "######  ",
                    "      # ",
                    "#     # ",
                    " #####  " },
            {
                    " #####  ",
                    "#     # ",
                    "#       ",
                    "######  ",
                    "#     # ",
                    "#     # ",
                    " #####  " },
            {
                    "####### ",
                    "#    #  ",
                    "    #   ",
                    "   #    ",
                    "  #     ",
                    "  #     ",
                    "  #     " },
            {
                    " #####  ",
                    "#     # ",
                    "#     # ",
                    " #####  ",
                    "#     # ",
                    "#     # ",
                    " #####  " },
            {
                    " #####  ",
                    "#     # ",
                    "#     # ",
                    " ###### ",
                    "      # ",
                    "#     # ",
                    " #####  " },
            {
                    "        ",
                    "        ",
                    "        ",
                    " #####  ",
                    "        ",
                    "        ",
                    "        " } };
    static int LINE_HEIGHT = 7;

    public static void main(String[] args) {
        // Perform input validation to check for correct arguments
        if (args.length != 1) {
            System.out.println("Bitte geben Sie genau eine Ganzzahl als Argument an.");
            System.exit(-1);
        }

        int number = Integer.parseInt(args[0]);

        ArrayList<Integer> digitsToPrint = new ArrayList<>();

        // Store whether the given number is negative
        boolean isNegative = number < 0;

        // If our number is negative, take its absolute value
        if (isNegative) {
            number = -number;
        }

        do {
            // Modulate the given number by 10 to extract its last digit
            int singlesDigit = number % 10;
            digitsToPrint.add(singlesDigit);
            // Shift the current number to the right by one decimal digit for the next
            // iteration
            number /= 10;
        } while (number != 0);

        // If the number is negative, add a minus sign to the end
        if (isNegative) {
            digitsToPrint.add(10);
        }

        // Reverse numbers since we added to the list in the wrong order to avoid
        // shifting the entire array back every time
        Collections.reverse(digitsToPrint);

        // For every line number
        for (int line = 0; line < LINE_HEIGHT; line++) {
            // Go through all the digits we need to print
            for (Integer digit : digitsToPrint) {
                // Print their respective line data
                System.out.print(FONT_CHARS[digit][line]);
            }
            // Add a newline and carriage return character after every line
            System.out.println();
        }
    }
}
