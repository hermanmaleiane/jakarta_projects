package com.challenge.herman.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

public class HistoryResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigInteger generatedNumber;
    private String processingTime;

    public HistoryResponse() {
    }

    public HistoryResponse(BigInteger generatedNumber, String processingTime) {
        this.generatedNumber = generatedNumber;
        this.processingTime = processingTime;
    }

    public BigInteger getGeneratedNumber() {
        return generatedNumber;
    }

    public void setGeneratedNumber(BigInteger generatedNumber) {
        this.generatedNumber = generatedNumber;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryResponse that = (HistoryResponse) o;
        return Objects.equals(generatedNumber, that.generatedNumber) &&
                Objects.equals(processingTime, that.processingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generatedNumber, processingTime);
    }

    @Override
    public String toString() {
        return "HistoryResponse{" +
                "generatedNumber='" + generatedNumber + '\'' +
                ", processingTime='" + processingTime + '\'' +
                '}';
    }
}