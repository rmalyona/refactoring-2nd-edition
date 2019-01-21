package org.mcvly.refactoring.first_example;

import java.util.Map;

public abstract class PerformanceCalculator {

    private final Map performanceDTO;
    private final Map<String, String> play;

    public PerformanceCalculator(Map performanceDTO, Map<String, String> play) {
        this.performanceDTO = performanceDTO;
        this.play = play;
    }

    public String getPlayName() {
        return play.get("name");
    }

    public int getAudience() {
        return (Integer) performanceDTO.get("audience");
    }

    public int getVolumeCredits() {
        return Math.max(getAudience() - 30, 0);
    }

    public abstract double getAmount();
}
