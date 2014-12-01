package com.radadev.applied;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Scanner;

public abstract class AppliedAlgorithm {

    private static final int TIME_LIMIT = 60 * 1000;
    private static final Class[] ALGORITHMS = new Class[]{
            Surjections.class,
            Cribbage.class,
            Mayan.class,
            Schedule.class,
            Anagrams.class,
            Subvector.class,
            Cyclic.class,
            Semigroup.class,
            Pills.class,
            Image.class,
            Espanol.class,
            Barcode.class,
            Plinko.class,
            Pancakes.class,
            Pudding.class,
            Cake.class,
            Cubes.class,
            Gopher.class,
            Convex.class,
            // TODO: Ghosts.class
            Led.class,
            Bowling.class,
            Sync.class,
    };

    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) {
        for (Class clazz : ALGORITHMS) {
            final String name = clazz.getSimpleName();

            final AppliedAlgorithm algorithm;
            try {
                algorithm = (AppliedAlgorithm) clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("Could not instantiate " + name + " for testing");
                continue;
            }

            try (Scanner in = new Scanner(algorithm.getInFile());
                 PrintStream out = new PrintStream(algorithm.getOutFile())) {

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        algorithm.execute(in, out);
                    }
                };
                t.start();
                t.join(TIME_LIMIT);
                if (t.isAlive()) {
                    t.interrupt();
                    throw new InterruptedException("time limit exceeded");
                }

                out.flush();
                out.close();

                System.out.println(name + ": " + (testOutput(algorithm) ? "success" : "failure"));
            } catch (FileNotFoundException | InterruptedException e) {
                System.out.println(name + ": error: " + e.getMessage());
            }
        }
    }

    private static boolean testOutput(AppliedAlgorithm algorithm) {

        boolean success = true;

        try (Scanner out = new Scanner(algorithm.getOutFile());
             Scanner expect = new Scanner(algorithm.getExpectedFile())) {
            while (success && (out.hasNextLine() || expect.hasNextLine())) {
                success = out.hasNextLine() && expect.hasNextLine() &&
                        out.nextLine().equals(expect.nextLine());
            }
        } catch (FileNotFoundException e) {
            success = false;
        }
        return success;
    }

    protected String getFileNameBase() {
        return getClass().getSimpleName().toLowerCase();
    }

    private File getInFile() {
        return getFile(getFileNameBase() + ".in");
    }

    private File getOutFile() {
        return new File("out/" + getFileNameBase() + ".out");
    }

    private File getExpectedFile() {
        return getFile(getFileNameBase() + ".expected");
    }

    private File getFile(String filename) {
        Thread thread = Thread.currentThread();
        ClassLoader loader = thread.getContextClassLoader();
        URL resource = loader.getResource(filename);
        return resource == null ? null : new File(resource.getFile());
    }

    abstract protected void execute(Scanner in, PrintStream out);
}
