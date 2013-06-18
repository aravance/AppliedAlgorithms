import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public abstract class AppliedAlgorithm {

    static Class[] ALGORITHMS = new Class[] {
        Surjections.class,
        Cribbage.class,
        Mayan.class,
        Sched.class,
        Anagrams.class,
        Subvector.class,
        Cyclic.class,
    };

    public static void main(String[] args) {
        for (Class algorithm : ALGORITHMS) {

            final String name = algorithm.getName();
            final String INFILE = name.toLowerCase() + ".in";
            final String OUTFILE = name.toLowerCase() + ".out";

            try (Scanner in = new Scanner(new FileInputStream(INFILE));
                 PrintStream out = new PrintStream(new FileOutputStream(OUTFILE))) {

                final AppliedAlgorithm instance = (AppliedAlgorithm) algorithm.newInstance();
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        instance.execute(in, out);
                    }
                };
                t.start();
                t.join(60 * 1000);
                if (t.isAlive()) {
                    t.interrupt();
                    throw new InterruptedException("time limit exceeded");
                }

                out.flush();
                out.close();

                System.out.println(name + ": " + (testAlgorithm(algorithm) ? "success" : "failure"));
            } catch (FileNotFoundException |
                    InterruptedException |
                    InstantiationException |
                    IllegalAccessException e) {
                System.out.println(name + ": error: " + e.getMessage());
            }
        }
    }

    private static boolean testAlgorithm(Class algorithm) {

        final String name = algorithm.getName();
        final String OUTFILE = name.toLowerCase() + ".out";
        final String EXPECTFILE = name.toLowerCase() + ".expected";
        boolean success = true;

        try (Scanner out = new Scanner(new FileInputStream(OUTFILE));
             Scanner expect = new Scanner(new FileInputStream(EXPECTFILE))) {
            while (success && (out.hasNextLine() || expect.hasNextLine())) {
                if (out.hasNextLine() && expect.hasNextLine()) {
                    success = out.nextLine().equals(expect.nextLine());
                } else {
                    success = false;
                }
            }
        } catch (FileNotFoundException e) {
            success = false;
        }
        return success;
    }

    abstract void execute(Scanner in, PrintStream out);
}
