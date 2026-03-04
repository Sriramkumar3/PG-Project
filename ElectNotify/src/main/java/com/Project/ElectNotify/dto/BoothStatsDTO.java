package com.Project.ElectNotify.dto;


public class BoothStatsDTO {

    private String boothName;
    private long total;
    private long voted;
    private long remaining;
    private double percentage;

    public BoothStatsDTO(String boothName, long total, long voted) {
        this.boothName = boothName;
        this.total = total;
        this.voted = voted;
        this.remaining = total - voted;
        this.percentage = total > 0 ? (double)voted * 100 / total : 0;
    }

    public String getBoothName() { return boothName; }
    public long getTotal() { return total; }
    public long getVoted() { return voted; }
    public long getRemaining() { return remaining; }
    public double getPercentage() { return Math.round(percentage); }
}
