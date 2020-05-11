package org.uniprot.core.proteome.impl;

import org.uniprot.core.proteome.BuscoReport;
import org.uniprot.core.proteome.CPDReport;
import org.uniprot.core.proteome.ProteomeCompletenessReport;

import java.util.Objects;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public class ProteomeCompletenessReportImpl implements ProteomeCompletenessReport {

    private static final long serialVersionUID = -7923662394693441279L;

    private final BuscoReport buscoReport;
    private final CPDReport cpdReport;

    // no arg constructor for JSON deserialization
    ProteomeCompletenessReportImpl() {
        this(null, null);
    }

    ProteomeCompletenessReportImpl(BuscoReport buscoReport, CPDReport cpdReport) {
        this.buscoReport = buscoReport;
        this.cpdReport = cpdReport;
    }

    @Override
    public BuscoReport getBuscoReport() {
        return buscoReport;
    }

    public CPDReport getCPDReport() {
        return cpdReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProteomeCompletenessReportImpl that = (ProteomeCompletenessReportImpl) o;
        return Objects.equals(getBuscoReport(), that.getBuscoReport())
                && Objects.equals(cpdReport, that.cpdReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuscoReport(), cpdReport);
    }
}
