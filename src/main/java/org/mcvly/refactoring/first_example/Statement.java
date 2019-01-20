package org.mcvly.refactoring.first_example;

import org.mcvly.refactoring.first_example.dto.Invoice;
import org.mcvly.refactoring.first_example.dto.Performance;

import java.util.Map;

public class Statement {
    private Map plays;
    private Invoice invoice;

    public Statement(Map plays, Invoice invoice) {
        this.plays = plays;
        this.invoice = invoice;
    }

    public String statement() {
        String result = String.format("Statement for %s\n", invoice.getCustomer());
        for (Performance performance : invoice.getPerformances()) {
            // print line for this order
            result += String.format("  %s: %s (%d seats)\n", playFor(performance).get("name"), usd(amountFor(performance)), performance.getAudience());
        }

        result += String.format("Amount owed is %s\n", usd(totalAmount()));
        result += String.format("You earned %d credits\n", totalVolumeCredits());
        return result;
    }

    private double totalAmount() {
        double result = 0;
        for (Performance performance : invoice.getPerformances()) {
            result += amountFor(performance);
        }
        return result;
    }

    private int totalVolumeCredits() {
        int result = 0;
        for (Performance performance : invoice.getPerformances()) {
            result += volumeCreditsFor(performance);
        }
        return result;
    }

    private int volumeCreditsFor(Performance performance) {
        int result = 0;
        result += Math.max(performance.getAudience() - 30, 0);
        // add extra credit for every ten comedy attendees
        if ("comedy".equals(playFor(performance).get("type"))) {
            result += Math.floor(performance.getAudience() / 5);
        }
        return result;
    }

    private double amountFor(Performance performance) {
        double result;
        switch (playFor(performance).get("type")) {
            case "tragedy":
                result = 40000;
                if (performance.getAudience() > 30) {
                    result += 1000 * (performance.getAudience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (performance.getAudience() > 20) {
                    result += 10000 + 500 * (performance.getAudience() - 20);
                }
                result += 300 * performance.getAudience();
                break;
            default:
                throw new RuntimeException("Unknown type: " + playFor(performance).get("type"));
        }
        return result;
    }

    private Map<String, String> playFor(Performance performance) {
        return (Map<String, String>) plays.get(performance.getPlayId());
    }

    private String usd(double amount) {
        return String.format("$%.2f", amount / 100);
    }
}
