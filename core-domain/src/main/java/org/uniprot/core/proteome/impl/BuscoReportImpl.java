package org.uniprot.core.proteome.impl;

import org.uniprot.core.proteome.BuscoReport;

import java.util.Objects;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public class BuscoReportImpl implements BuscoReport {

    private static final long serialVersionUID = -4682876141794045279L;

    private int complete;
    private int completeSingle;
    private int completeDuplicated;
    private int fragmented;
    private int missing;
    private int total;
    private String lineageDb;

    // no arg constructor for JSON deserialization
    BuscoReportImpl(){

    }

    BuscoReportImpl(int complete, int completeSingle, int completeDuplicated, int fragmented, int missing, int total, String lineageDb) {
        this.complete = complete;
        this.completeSingle = completeSingle;
        this.completeDuplicated = completeDuplicated;
        this.fragmented = fragmented;
        this.missing = missing;
        this.total = total;
        this.lineageDb = lineageDb;
    }

    @Override
    public int getComplete() {
        return complete;
    }

    @Override
    public int getCompleteSingle() {
        return completeSingle;
    }

    @Override
    public int getCompleteDuplicated() {
        return completeDuplicated;
    }

    @Override
    public int getFragmented() {
        return fragmented;
    }

    @Override
    public int getMissing() {
        return missing;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public String getLineageDb() {
        return lineageDb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuscoReportImpl that = (BuscoReportImpl) o;
        return getComplete() == that.getComplete() &&
                getCompleteSingle() == that.getCompleteSingle() &&
                getCompleteDuplicated() == that.getCompleteDuplicated() &&
                getFragmented() == that.getFragmented() &&
                getMissing() == that.getMissing() &&
                getTotal() == that.getTotal() &&
                Objects.equals(getLineageDb(), that.getLineageDb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComplete(), getCompleteSingle(), getCompleteDuplicated(), getFragmented(), getMissing(), getTotal(), getLineageDb());
    }
}
