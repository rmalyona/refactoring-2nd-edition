package org.mcvly.refactoring.first_example;

import java.util.Map;

public class ComedyCalculator extends PerformanceCalculator {
    public ComedyCalculator(Map performanceDTO, Map<String, String> play) {
        super(performanceDTO, play);
    }

    @Override
    public double getAmount() {
        int result = 30000;
        if (getAudience() > 20) {
            result += 10000 + 500 * (getAudience() - 20);
        }
        result += 300 * getAudience();
        return result;
    }

    @Override
    public int getVolumeCredits() {
        return super.getVolumeCredits() + (int) Math.floor(getAudience() / 5);
    }
}
