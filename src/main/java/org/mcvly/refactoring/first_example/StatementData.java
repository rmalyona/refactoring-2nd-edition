package org.mcvly.refactoring.first_example;

import lombok.Data;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Data
class StatementData {
    private String customer;
    private List<Performance> performances;
    private double totalAmount;
    private int totalVolumeCredits;

    @Data
    static class Performance {
        private int audience;
        private double amount;
        private int volumeCredits;
        private String playName;
        private String playType;
    }

    public StatementData(Map plays, Map invoice) {
        this.setCustomer((String) invoice.get("customer"));
        this.setPerformances(enrichPerformance(plays, (List<Map>) invoice.get("performances")));
        this.setTotalAmount(totalAmount());
        this.setTotalVolumeCredits(totalVolumeCredits());
    }

    private List<StatementData.Performance> enrichPerformance(Map plays, List<Map> performances) {
        return performances.stream().map(performanceDTO -> {
            Map<String, String> play = (Map<String, String>) plays.get(performanceDTO.get("playID"));
            StatementData.Performance performance = new StatementData.Performance();
            performance.setAudience((Integer) performanceDTO.get("audience"));
            performance.setPlayName(play.get("name"));
            performance.setPlayType(play.get("type"));
            performance.setAmount(amountFor(performance));
            performance.setVolumeCredits(volumeCreditsFor(performance));
            return performance;
        }).collect(toList());
    }


    private double totalAmount() {
        return performances.stream().mapToDouble(performance -> performance.amount).sum();
    }

    private int totalVolumeCredits() {
        return performances.stream().mapToInt(performance -> performance.volumeCredits).sum();
    }

    private int volumeCreditsFor(StatementData.Performance performance) {
        int result = 0;
        result += Math.max(performance.getAudience() - 30, 0);
        // add extra credit for every ten comedy attendees
        if ("comedy".equals(performance.getPlayType())) {
            result += Math.floor(performance.getAudience() / 5);
        }
        return result;
    }

    private double amountFor(StatementData.Performance performance) {
        double result;
        switch (performance.getPlayType()) {
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
                throw new RuntimeException("Unknown type: " + performance.getPlayType());
        }
        return result;
    }
}