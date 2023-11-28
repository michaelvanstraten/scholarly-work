// https://github.com/michaelvanstraten/Mathematics-B.Sc./blob/master/Grundlagen%20der%20Programmierung/Deduplikator.java

import java.util.Arrays;

public class Deduplikator {
    public static void main(String[] args) {
        // Convert command-line arguments to an array of unique double values
        double[] uniqueValues = Arrays.stream(args)
                .mapToDouble(Double::parseDouble)
                .distinct()
                .toArray();

        // Print the distinct values
        for (double value : uniqueValues) {
            System.out.println(value);
        }
    }
}

