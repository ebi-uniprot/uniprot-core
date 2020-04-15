package org.uniprot.core.proteome.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.BuscoReport;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public class BuscoReportBuilder implements Builder<BuscoReport> {

    private int complete;
    private int completeSingle;
    private int completeDuplicated;
    private int fragmented;
    private int missing;
    private int total;
    private String lineageDb;

    @Nonnull
    @Override
    public BuscoReport build() {
        return new BuscoReportImpl(
                complete,
                completeSingle,
                completeDuplicated,
                fragmented,
                missing,
                total,
                lineageDb);
    }

    public @Nonnull BuscoReportBuilder complete(int complete) {
        this.complete = complete;
        return this;
    }

    public @Nonnull BuscoReportBuilder completeSingle(int completeSingle) {
        this.completeSingle = completeSingle;
        return this;
    }

    public @Nonnull BuscoReportBuilder completeDuplicated(int completeDuplicated) {
        this.completeDuplicated = completeDuplicated;
        return this;
    }

    public @Nonnull BuscoReportBuilder fragmented(int fragmented) {
        this.fragmented = fragmented;
        return this;
    }

    public @Nonnull BuscoReportBuilder missing(int missing) {
        this.missing = missing;
        return this;
    }

    public @Nonnull BuscoReportBuilder total(int total) {
        this.total = total;
        return this;
    }

    public @Nonnull BuscoReportBuilder lineageDb(String lineageDb) {
        this.lineageDb = lineageDb;
        return this;
    }

    public static @Nonnull BuscoReportBuilder from(@Nonnull BuscoReport instance) {
        return new BuscoReportBuilder()
                .complete(instance.getComplete())
                .completeDuplicated(instance.getCompleteDuplicated())
                .completeSingle(instance.getCompleteSingle())
                .fragmented(instance.getFragmented())
                .missing(instance.getMissing())
                .total(instance.getTotal())
                .lineageDb(instance.getLineageDb());
    }
}
