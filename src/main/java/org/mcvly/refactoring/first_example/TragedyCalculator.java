package org.mcvly.refactoring.first_example;

import java.util.Map;

public class TragedyCalculator extends PerformanceCalculator {
    public TragedyCalculator(Map performanceDTO, Map<String, String> play) {
        super(performanceDTO, play);
    }

    @Override
    public double getAmount() {
        int result = 40000;
        if (getAudience() > 30) {
            result += 1000 * (getAudience() - 30);
        }
        return result;
    }
}
