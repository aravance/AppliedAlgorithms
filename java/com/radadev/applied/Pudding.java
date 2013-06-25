package com.radadev.applied;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class Pudding extends AppliedAlgorithm {

    private static List<String> evaluate(
            String result, Collection<String> conditions, Collection<String> givens,
            Collection<String> symbols, List<String> variables, Map<String, String> table, int current) {
        List<String> results = new LinkedList<>();
        if (current < variables.size()) {
            String variable = variables.get(current);
            for (String symbol : symbols) {
                table.put(variable, symbol);
                results.addAll(evaluate(result, conditions, givens, symbols, variables, table, current + 1));
            }
        } else {
            Set<String> substituted = new HashSet<>();
            for (String condition : conditions) {
                for (String var : table.keySet()) {
                    condition = condition.replaceAll("\\b" + var + "\\b", table.get(var));
                }
                substituted.add(condition);
            }

            if (givens.containsAll(substituted)) {
                for (String var : table.keySet()) {
                    result = result.replaceAll("\\b" + var + "\\b", table.get(var));
                }
                results.add(result);
            }
        }
        return results;
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        int c = 1;
        for (String line = in.nextLine(); !line.equals("end"); line = in.nextLine()) {

            List<String> conditions = new ArrayList<>();
            do {
                conditions.add(line);
                line = in.nextLine();
            } while (!line.equals("-"));

            String result = in.nextLine();
            result = result.substring(2, result.length() - 2);

            List<String> givens = new ArrayList<>();
            for (line = in.nextLine(); !line.equals("end"); line = in.nextLine()) {
                givens.add(line);
            }

            Set<String> variables = new HashSet<>();
            for (String formula : conditions) {
                StringTokenizer tokenizer = new StringTokenizer(formula);
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (Character.isUpperCase(token.charAt(0))) {
                        variables.add(token);
                    }
                }
            }

            Set<String> symbols = new HashSet<>();
            for (String given : givens) {
                StringTokenizer tokenizer = new StringTokenizer(given);
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (!token.equals("(") && !token.equals(")")) {
                        symbols.add(token);
                    }
                }
            }

            List<String> results = evaluate(
                    result, conditions, givens,
                    symbols, new ArrayList<>(variables), new HashMap<String, String>(), 0);

            if (c > 1) out.println();
            out.println("Proof #" + c++);
            if (results.size() == 0) {
                out.println("No matches");
            } else {
                Collections.sort(results);
                for (String s : results) {
                    out.println(s);
                }
            }

            in.nextLine(); // throw away empty line
        }
    }
}
