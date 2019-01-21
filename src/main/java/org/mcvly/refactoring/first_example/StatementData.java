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
        private final String playName;
        private final double amount;
        private final int audience;
        private final int volumeCredits;
    }

    public StatementData(Map plays, Map invoice) {
        this.setCustomer((String) invoice.get("customer"));
        this.setPerformances(enrichPerformance(plays, (List<Map>) invoice.get("performances")));
        this.setTotalAmount(totalAmount());
        this.setTotalVolumeCredits(totalVolumeCredits());
    }

    private List<StatementData.Performance> enrichPerformance(Map plays, List<Map> performances) {
        return performances.stream().map(performanceDTO -> {
            PerformanceCalculator calculator = new PerformanceCalculator(performanceDTO, playFor(plays, performanceDTO));
            return new Performance(calculator.getPlayName(), calculator.getAmount(), calculator.getAudience(), calculator.getVolumeCredits());
        }).collect(toList());
    }

    private Map<String, String> playFor(Map plays, Map performanceDTO) {
        return (Map<String, String>) plays.get(performanceDTO.get("playID"));
    }


    private double totalAmount() {
        return performances.stream().mapToDouble(performance -> performance.amount).sum();
    }

    private int totalVolumeCredits() {
        return performances.stream().mapToInt(performance -> performance.volumeCredits).sum();
    }
}