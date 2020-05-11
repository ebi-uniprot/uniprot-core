package org.uniprot.core.proteome.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.BuscoReport;
import org.uniprot.core.proteome.CPDReport;
import org.uniprot.core.proteome.ProteomeCompletenessReport;

import javax.annotation.Nonnull;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public class ProteomeCompletenessReportBuilder implements Builder<ProteomeCompletenessReport> {

    private BuscoReport buscoReport;
    private CPDReport cpdReport;

    public @Nonnull ProteomeCompletenessReportBuilder buscoReport(BuscoReport buscoReport) {
        this.buscoReport = buscoReport;
        return this;
    }

    public @Nonnull ProteomeCompletenessReportBuilder cpdReport(CPDReport cpdReport) {
        this.cpdReport = cpdReport;
        return this;
    }

    @Nonnull
    @Override
    public ProteomeCompletenessReport build() {
        return new ProteomeCompletenessReportImpl(buscoReport, cpdReport);
    }

    public static @Nonnull ProteomeCompletenessReportBuilder from(
            @Nonnull ProteomeCompletenessReport instance) {
        return new ProteomeCompletenessReportBuilder()
                .cpdReport(instance.getCPDReport())
                .buscoReport(instance.getBuscoReport());
    }
}
