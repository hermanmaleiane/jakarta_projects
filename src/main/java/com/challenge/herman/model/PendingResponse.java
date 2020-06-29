package com.challenge.herman.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PendingResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId;
    private Date submissionDate;
    private String totalWaitingTime;

    public PendingResponse() {
    }

    public PendingResponse(String requestId, Date submissionDate, String totalWaitingTime) {
        this.requestId = requestId;
        this.submissionDate = submissionDate;
        this.totalWaitingTime = totalWaitingTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(String totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PendingResponse that = (PendingResponse) o;
        return Objects.equals(requestId, that.requestId) &&
                Objects.equals(submissionDate, that.submissionDate) &&
                Objects.equals(totalWaitingTime, that.totalWaitingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, submissionDate, totalWaitingTime);
    }

    @Override
    public String toString() {
        return "PendingResponse{" +
                "requestId='" + requestId + '\'' +
                ", submissionDate=" + submissionDate +
                ", totalWaitingTime='" + totalWaitingTime + '\'' +
                '}';
    }
}