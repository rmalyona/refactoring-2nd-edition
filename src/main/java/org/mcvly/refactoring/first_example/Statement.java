package org.mcvly.refactoring.first_example;

import org.mcvly.refactoring.first_example.dto.Invoice;

import java.util.Map;

public class Statement {
    public static String statement(Map plays, Invoice invoice) {
        double totalAmount = 0;
        int volumeCredits = 0;
        String result = String.format("org.mcvly.refactoring.first_example.Statement for %s\n", invoice.getCustomer());

        for (Invoice.Performance performance : invoice.getPerformances()) {
            Map<String, String> play = (Map<String, String>) plays.get(performance.getPlayId());
            double thisAmount = 0;
            switch (play.get("type")) {
                case "tragedy":
                    thisAmount = 40000;
                    if (performance.getAudience() > 30) {
                        thisAmount += 1000 * (performance.getAudience() - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (performance.getAudience() > 20) {
                        thisAmount += 10000 + 500 * (performance.getAudience() - 20);
                    }
                    thisAmount += 300 * performance.getAudience();
                    break;
                default:
                    throw new RuntimeException("Unknown type: " + play.get("type"));
            }
            // add volume credits
            volumeCredits += Math.max(performance.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy".equals(play.get("type"))) {
                volumeCredits += Math.floor(performance.getAudience() / 5);
            }
            // print line for this order
            result += String.format("  %s: $%.2f (%d seats)\n", play.get("name"), thisAmount / 100, performance.getAudience());
            totalAmount += thisAmount;
        }
        result += String.format("Amount owed is $%.2f\n", totalAmount / 100);
        result += String.format("You earned %d credits\n", volumeCredits);
        return result;
    }
}
