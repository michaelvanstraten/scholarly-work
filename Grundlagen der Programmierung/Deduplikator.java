import java.util.Arrays;

public class Deduplikator {
    public static void main(String[] args) {
        double[] distinctValues = Arrays.stream(args)
                .mapToDouble(Double::parseDouble)
                .distinct()
                .toArray();

        for (double value : distinctValues) {
            System.out.println(value);
        }
    }
}

