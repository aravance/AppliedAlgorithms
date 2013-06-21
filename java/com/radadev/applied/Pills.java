package  com.radadev.applied;

import java.io.PrintStream;
import java.util.*;

public class Pills extends AppliedAlgorithm {

    @Override
    void execute(Scanner in, PrintStream out) {
        for (int m = in.nextInt(), n = in.nextInt();
             m != 0 && n != 0;
             m = in.nextInt(), n = in.nextInt()) {

            Queue<Boolean> tube = new LinkedList<>();
            for (char c : in.next().toCharArray()) {
                tube.add(c == 'B');
            }

            List<Integer> dead= new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                tube.add(tube.poll());
                if (tube.poll()) {
                    dead.add(i + 1);
                }
            }

            if (dead.isEmpty()) {
                out.println(0);
            } else {
                out.print(dead.get(0));
                for (int i = 1; i < dead.size(); ++i) {
                    out.print(" ");
                    out.print(dead.get(i));
                }
                out.println();
            }
        }
    }
}
