package com.radadev.applied;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Led extends AppliedAlgorithm {

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (int display = in.nextInt(); display != 0; display = in.nextInt()) {
            LedNumber.reset();

            out.print("Case <" + display);
            LedNumber.breakDisplay(display);
            for (display = in.nextInt(); display != 0; display = in.nextInt()) {
                out.print("," + display);
                LedNumber.breakDisplay(display);
            }
            out.print(">:");

            List<LedNumber> numbers = new ArrayList<>(Arrays.asList(LedNumber.values()));
            while (!numbers.isEmpty()) {
                LedNumber number = numbers.remove(0);
                boolean matched = false;
                for (Iterator<LedNumber> iter = numbers.iterator(); iter.hasNext(); ) {
                    LedNumber led = iter.next();
                    if (led.confusable(number)) {
                        if (!matched) out.print(" {" + number);
                        matched = true;
                        out.print("," + led);
                        iter.remove();
                    }
                }
                if (matched) out.print("}");
            }

            out.println();
        }
    }

    private static enum LedNumber {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;
        private boolean displays[] = new boolean[8];

        public static void reset() {
            for (LedNumber led : values()) {
                led.displays[1] = led != ONE && led != FOUR;
                led.displays[2] = led != ONE && led != TWO && led != THREE && led != SEVEN;
                led.displays[3] = led != FIVE && led != SIX;
                led.displays[4] = led.ordinal() > 1 && led != SEVEN;
                led.displays[5] = led != FOUR && led.ordinal() % 2 == 0;
                led.displays[6] = led != TWO;
                led.displays[7] = led != ONE && led != FOUR && led != SEVEN && led != NINE;
            }
        }

        public static void breakDisplay(int display) {
            for (LedNumber led : values()) {
                led.displays[display] = false;
            }
        }

        public boolean confusable(LedNumber o) {
            return Arrays.equals(displays, o.displays);
        }

        @Override
        public String toString() {
            return Integer.toString(ordinal());
        }
    }
}
