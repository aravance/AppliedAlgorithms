package com.radadev.applied;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Schedule extends AppliedAlgorithm {

    private static int computeProfit(Collection<Order> orders) {
        int profit = 0;
        PriorityQueue<Order> ordersByTime = new PriorityQueue<>(orders.size(), new DeadlineOrderComparator());
        PriorityQueue<Order> ordersByProfit = new PriorityQueue<>(orders.size(), new ProfitOrderComparator());
        ordersByTime.addAll(orders);
        final int deadline = orders.size() > 0 ? ordersByTime.peek().getDeadline() : 0;
        for (int time = deadline; time > 0; --time) {
            while (!ordersByTime.isEmpty() && ordersByTime.peek().getDeadline() == time) {
                ordersByProfit.add(ordersByTime.poll());
            }
            if (!ordersByProfit.isEmpty()) profit += ordersByProfit.poll().getProfit();
        }
        return profit;
    }

    @Override
    protected void execute(Scanner in, PrintStream out) {
        for (int count = in.nextInt(); count != 0; count = in.nextInt()) {
            Collection<Order> orders = new ArrayList<>();
            for (int i = 0; i < count; ++i) {
                orders.add(new Order(in.nextInt(), in.nextInt(), in.nextInt()));
            }
            out.println(computeProfit(orders));
        }
    }

    @Override
    protected String getFileNameBase() {
        return "sched";
    }

    private static class ProfitOrderComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            int value = Integer.compare(o1.getProfit(), o2.getProfit());
            if (value == 0) value = Integer.compare(o1.getDeadline(), o2.getDeadline());
            if (value == 0) value = Integer.compare(o1.getId(), o2.getId());
            return -value;
        }
    }

    private static class DeadlineOrderComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            int value = Integer.compare(o1.getDeadline(), o2.getDeadline());
            if (value == 0) value = Integer.compare(o1.getProfit(), o2.getProfit());
            if (value == 0) value = Integer.compare(o1.getId(), o2.getId());
            return -value;
        }
    }

    private static class Order {

        private final int id;
        private final int deadline;
        private final int profit;

        Order(int id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }

        int getId() {
            return id;
        }

        int getDeadline() {
            return deadline;
        }

        int getProfit() {
            return profit;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "id=" + id +
                    ", deadline=" + deadline +
                    ", profit=" + profit +
                    '}';
        }
    }
}
