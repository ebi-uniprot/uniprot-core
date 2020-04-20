package org.uniprot.core.proteome.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.CPDReport;
import org.uniprot.core.proteome.CPDStatus;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public class CPDReportBuilder implements Builder<CPDReport> {

    private int proteomeCount;
    private double stdCdss;
    private int averageCdss;
    private int confidence;
    private CPDStatus status;

    public @Nonnull CPDReportBuilder proteomeCount(int proteomeCount) {
        this.proteomeCount = proteomeCount;
        return this;
    }

    public @Nonnull CPDReportBuilder stdCdss(double stdCdss) {
        this.stdCdss = stdCdss;
        return this;
    }

    public @Nonnull CPDReportBuilder averageCdss(int averageCdss) {
        this.averageCdss = averageCdss;
        return this;
    }

    public @Nonnull CPDReportBuilder confidence(int confidence) {
        this.confidence = confidence;
        return this;
    }

    public @Nonnull CPDReportBuilder status(CPDStatus status) {
        this.status = status;
        return this;
    }

    @Nonnull
    @Override
    public CPDReport build() {
        return new CPDReportImpl(proteomeCount, stdCdss, averageCdss, confidence, status);
    }

    public static @Nonnull CPDReportBuilder from(@Nonnull CPDReport instance) {
        return new CPDReportBuilder()
                .averageCdss(instance.getAverageCdss())
                .confidence(instance.getConfidence())
                .status(instance.getStatus())
                .stdCdss(instance.getStdCdss())
                .proteomeCount(instance.getProteomeCount());
    }
}
