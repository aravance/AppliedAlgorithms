import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Mayan extends AppliedAlgorithm {

    long fromMayan(String s) {
        long total = 0;
        try (Scanner in = new Scanner(new ByteArrayInputStream(s.getBytes()))) {
            long current = 0;
            for (String line = in.nextLine(); in.hasNextLine() && !line.equals("#"); line = in.nextLine()) {
                if (line.equals("")) {
                    total = 20 * total + current;
                    current = 0;
                } else if (line.equals("-----")) {
                    current += 5;
                } else if (!line.equals("@")) {
                    current += line.length();
                }
            }
            total = 20 * total + current;
        }
        return total;
    }

    String toMayan(long n) {
        boolean first = true;
        StringBuilder builder = new StringBuilder("#");
        while (first || n > 0) {
            if (first) {
                first = false;
            } else {
                builder.insert(0, "\n");
            }
            int current = (int) n % 20;
            if (current == 0) builder.insert(0, "@\n");
            while (current >= 5) {
                builder.insert(0, "-----\n");
                current -= 5;
            }
            if (current > 0) {
                String stars = String.format(String.format("%%0%dd\n", current), 0).replace("0", "*");
                builder.insert(0, stars);
            }
            n /= 20;
        }
        return builder.toString();
    }

    @Override
    void execute(Scanner in, PrintStream out) {
        for (String line = in.nextLine(); in.hasNextLine() && !line.equals("0"); line = in.nextLine()) {
            long first, second;
            char operator;
            StringBuilder builder = new StringBuilder(line).append("\n");
            for (line = in.nextLine(); !line.equals("#"); line = in.nextLine()) {
                builder.append(line).append("\n");
            }
            builder.append("#");
            first = fromMayan(builder.toString());
            operator = in.nextLine().charAt(0);
            builder.setLength(0);
            for (line = in.nextLine(); !line.equals("#"); line = in.nextLine()) {
                builder.append(line).append("\n");
            }
            builder.append("#");
            second = fromMayan(builder.toString());
            switch (operator) {
                case '+': out.println(toMayan(first + second)); break;
                case '-': out.println(toMayan(first - second)); break;
                default: throw new UnsupportedOperationException("Unknown operator: " + operator);
            }
        }
    }
}
