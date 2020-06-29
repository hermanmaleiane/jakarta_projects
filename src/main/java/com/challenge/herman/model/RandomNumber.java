package com.challenge.herman.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RandomNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId;
    private BigInteger generatedNumber;
    private Date submissionDate;
    private String requestState;
    private long processingTime;
    private String formattedProcessingTime;

    public RandomNumber() {
    }


    public RandomNumber(String requestId, BigInteger generatedNumber, Date submissionDate, String requestState, long processingTime) {
        this.requestId = requestId;
        this.generatedNumber = generatedNumber;
        this.submissionDate = submissionDate;
        this.requestState = requestState;
        this.processingTime = processingTime;
        this.formattedProcessingTime = String.format("%d min, %d sec, %d millis", TimeUnit.MILLISECONDS.toMinutes(processingTime), TimeUnit.MILLISECONDS.toSeconds(processingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(processingTime)),processingTime);
    }

    public void setProcessingTime(long processingTime) {
        this.formattedProcessingTime = String.format("%d min, %d sec, %d millis", TimeUnit.MILLISECONDS.toMinutes(processingTime), TimeUnit.MILLISECONDS.toSeconds(processingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(processingTime)),processingTime);
        this.processingTime = processingTime;
    }

    public String getFormattedProcessingTime() {
        return formattedProcessingTime;
    }

    public void setFormattedProcessingTime(String formattedProcessingTime) {
        this.formattedProcessingTime = formattedProcessingTime;
    }

    public BigInteger getGeneratedNumber() {
        return generatedNumber;
    }

    public void setGeneratedNumber(BigInteger generatedNumber) {
        this.generatedNumber = generatedNumber;
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

    public String getRequestState() {
        return requestState;
    }

    public void setRequestState(String requestState) {
        this.requestState = requestState;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomNumber that = (RandomNumber) o;
        return processingTime == that.processingTime &&
                Objects.equals(requestId, that.requestId) &&
                Objects.equals(generatedNumber, that.generatedNumber) &&
                Objects.equals(submissionDate, that.submissionDate) &&
                Objects.equals(requestState, that.requestState) &&
                Objects.equals(formattedProcessingTime, that.formattedProcessingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, generatedNumber, submissionDate, requestState, processingTime, formattedProcessingTime);
    }

    @Override
    public String toString() {
        return "RandomNumber{" +
                "requestId='" + requestId + '\'' +
                ", generatedNumber=" + generatedNumber +
                ", submissionDate=" + submissionDate +
                ", requestState='" + requestState + '\'' +
                ", processingTime=" + processingTime +
                ", formattedProcessingTime='" + formattedProcessingTime + '\'' +
                '}';
    }
}