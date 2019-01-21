package org.mcvly.refactoring.first_example;

import java.util.Map;

public class Statement {

    public String textStatement(Map plays, Map invoice) {
        return renderPlainText(new StatementData(plays, invoice));
    }

    public String htmlStatement(Map plays, Map invoice) {
        return renderHtml(new StatementData(plays, invoice));
    }

    private String renderPlainText(StatementData data) {
        String result = String.format("Statement for %s\n", data.getCustomer());
        for (StatementData.Performance performance : data.getPerformances()) {
            result += String.format("  %s: %s (%d seats)\n", performance.getPlayName(), usd(performance.getAmount()), performance.getAudience());
        }

        result += String.format("Amount owed is %s\n", usd(data.getTotalAmount()));
        result += String.format("You earned %d credits\n", data.getTotalVolumeCredits());
        return result;
    }

    private String renderHtml(StatementData data) {
        String result = String.format("<h1>Statement for %s</h1>\n", data.getCustomer());
        result += "<table>\n";
        result += "<tr><th>play</th><th>seats</th><th>cost</th></tr>\n";
        for (StatementData.Performance performance : data.getPerformances()) {
            result += String.format("  <tr><td>%s</td><td>%d</td>", performance.getPlayName(), performance.getAudience());
            result += String.format("<td>%s</td></tr>\n", usd(performance.getAmount()));
        }
        result += "</table>\n";
        result += String.format("<p>Amount owed is <em>%s</em></p>\n", usd(data.getTotalAmount()));
        result += String.format("<p>You earned <em>%d</em> credits</p>\n", data.getTotalVolumeCredits());
        return result;
    }

    private String usd(double amount) {
        return String.format("$%.2f", amount / 100);
    }

}
