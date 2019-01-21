package org.mcvly.refactoring.first_example;

import java.util.Map;

public class PerformanceCalculator {

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

    public double getAmount() {
        double result;
        switch (getPlayType()) {
            case "tragedy":
                result = 40000;
                if (getAudience() > 30) {
                    result += 1000 * (getAudience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (getAudience() > 20) {
                    result += 10000 + 500 * (getAudience() - 20);
                }
                result += 300 * getAudience();
                break;
            default:
                throw new RuntimeException("Unknown type: " + getPlayType());
        }
        return result;
    }

    public int getVolumeCredits() {
        int result = 0;
        result += Math.max(getAudience() - 30, 0);
        // add extra credit for every ten comedy attendees
        if ("comedy".equals(getPlayType())) {
            result += Math.floor(getAudience() / 5);
        }
        return result;
    }

    private String getPlayType() {
        return play.get("type");
    }
}
