package com.radadev.applied;

import com.radadev.applied.utility.MapBuilder;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Barcode extends AppliedAlgorithm {

    private static final Map<Character, String> CODES =
            MapBuilder.newBuilder(new HashMap<Character, String>())
                    .with('0', "nnwwn")
                    .with('1', "wnnnw")
                    .with('2', "nwnnw")
                    .with('3', "wwnnn")
                    .with('4', "nnwnw")
                    .with('5', "wnwnn")
                    .with('6', "nwwnn")
                    .with('7', "nnnww")
                    .with('8', "wnnwn")
                    .with('9', "nwnwn")
                    .buildUnmodifiable();

    private static String toBarcode(String digits) {
        if (digits.length() % 2 == 1) digits = "0" + digits;
        StringBuilder builder = new StringBuilder("NnNn");
        for (int i = 0; i < digits.length(); i += 2) {
            String bars = CODES.get(digits.charAt(i)).toUpperCase();
            String spaces = CODES.get(digits.charAt(i + 1)).toLowerCase();
            for (int j = 0; j < bars.length(); ++j) {
                builder.append(bars.charAt(j));
                builder.append(spaces.charAt(j));
            }
        }
        return builder.append("WnN").toString();
    }

    @Override
    void execute(Scanner in, PrintStream out) {
        for (String line = in.nextLine(); !line.equals("0"); line = in.nextLine()) {
            out.println(toBarcode(line));
        }
    }
}
