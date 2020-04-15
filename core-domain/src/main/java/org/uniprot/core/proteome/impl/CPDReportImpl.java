package org.uniprot.core.proteome.impl;

import java.util.Objects;

import org.uniprot.core.proteome.CPDReport;
import org.uniprot.core.proteome.CPDStatus;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public class CPDReportImpl implements CPDReport {

    private static final long serialVersionUID = 5918492529895632495L;

    private int proteomeCount;
    private double stdCdss;
    private int averageCdss;
    private int confidence;
    private CPDStatus status;

    // no arg constructor for JSON deserialization
    CPDReportImpl() {}

    CPDReportImpl(
            int proteomeCount, double stdCdss, int averageCdss, int confidence, CPDStatus status) {
        this.proteomeCount = proteomeCount;
        this.stdCdss = stdCdss;
        this.averageCdss = averageCdss;
        this.confidence = confidence;
        this.status = status;
    }

    @Override
    public int getProteomeCount() {
        return proteomeCount;
    }

    @Override
    public double getStdCdss() {
        return stdCdss;
    }

    @Override
    public int getAverageCdss() {
        return averageCdss;
    }

    @Override
    public int getConfidence() {
        return confidence;
    }

    @Override
    public CPDStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPDReportImpl cpdReport = (CPDReportImpl) o;
        return getProteomeCount() == cpdReport.getProteomeCount()
                && Double.compare(cpdReport.getStdCdss(), getStdCdss()) == 0
                && getAverageCdss() == cpdReport.getAverageCdss()
                && getConfidence() == cpdReport.getConfidence()
                && getStatus() == cpdReport.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getProteomeCount(), getStdCdss(), getAverageCdss(), getConfidence(), getStatus());
    }
}
