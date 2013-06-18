import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

public class Cyclic extends AppliedAlgorithm {

    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");

    private static String repeat(char c, int n) {
        return n <= 0 ?
                "" :
                String.format(String.format("%%0%dd", n), 0).replaceAll("0", Character.toString(c));
    }

    private static boolean rotationEquals(String first, String second) {
        second += repeat('0', first.length() - second.length());
        boolean equal = first.equals(second);
        for (int i = 0; !equal && i < first.length(); ++i) {
            second = second.substring(1) + second.charAt(0);
            equal = first.equals(second);
        }
        return equal;
    }

    private boolean isCyclic(String number) {
        BigInteger big = new BigInteger(number);
        return rotationEquals(number, big.multiply(TWO).toString()) &&
                rotationEquals(number, big.multiply(THREE).toString());
    }

    @Override
    void execute(Scanner in, PrintStream out) {
        for (String line = in.nextLine(); !line.equals("00") && in.hasNextLine(); line = in.nextLine()) {
            out.println(line + (isCyclic(line) ? " is cyclic" : " is not cyclic"));
        }
    }
}
