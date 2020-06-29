package com.challenge.herman.model;

import java.io.Serializable;
import java.util.Objects;

public class StatsResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String maximumWaitingTime;
    private String minimumWaitingTime;
    private long totalPendingRequests;

    public StatsResponse() {
    }

    public StatsResponse(String maximumWaitingTime, String minimumWaitingTime, long totalPendingRequests) {
        this.maximumWaitingTime = maximumWaitingTime;
        this.minimumWaitingTime = minimumWaitingTime;
        this.totalPendingRequests = totalPendingRequests;
    }

    public String getMaximumWaitingTime() {
        return maximumWaitingTime;
    }

    public void setMaximumWaitingTime(String maximumWaitingTime) {
        this.maximumWaitingTime = maximumWaitingTime;
    }

    public String getMinimumWaitingTime() {
        return minimumWaitingTime;
    }

    public void setMinimumWaitingTime(String minimumWaitingTime) {
        this.minimumWaitingTime = minimumWaitingTime;
    }

    public long getTotalPendingRequests() {
        return totalPendingRequests;
    }

    public void setTotalPendingRequests(long totalPendingRequests) {
        this.totalPendingRequests = totalPendingRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsResponse that = (StatsResponse) o;
        return totalPendingRequests == that.totalPendingRequests &&
                Objects.equals(maximumWaitingTime, that.maximumWaitingTime) &&
                Objects.equals(minimumWaitingTime, that.minimumWaitingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maximumWaitingTime, minimumWaitingTime, totalPendingRequests);
    }

    @Override
    public String toString() {
        return "StatsResponse{" +
                "maximumWaitingTime='" + maximumWaitingTime + '\'' +
                ", minimumWaitingTime='" + minimumWaitingTime + '\'' +
                ", totalPendingRequests=" + totalPendingRequests +
                '}';
    }
}