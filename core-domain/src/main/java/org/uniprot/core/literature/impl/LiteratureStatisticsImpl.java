package org.uniprot.core.literature.impl;

import java.util.Objects;

import org.uniprot.core.impl.StatisticsImpl;
import org.uniprot.core.literature.LiteratureStatistics;

/** @author lgonzales */
public class LiteratureStatisticsImpl extends StatisticsImpl implements LiteratureStatistics {

    private long mappedProteinCount;

    // no arg constructor for JSON deserialization
    LiteratureStatisticsImpl() {
        this(0, 0, 0);
    }

    LiteratureStatisticsImpl(
            long reviewedProteinCount, long unreviewedProteinCount, long mappedProteinCount) {
        super(reviewedProteinCount, unreviewedProteinCount);
        this.mappedProteinCount = mappedProteinCount;
    }

    @Override
    public long getMappedProteinCount() {
        return mappedProteinCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteratureStatisticsImpl that = (LiteratureStatisticsImpl) o;
        return super.equals(that) && getMappedProteinCount() == that.getMappedProteinCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMappedProteinCount());
    }

    @Override
    public String toString() {
        return "LiteratureStatisticsImpl{"
                + "reviewedProteinCount="
                + getReviewedProteinCount()
                + ", unreviewedProteinCount="
                + getUnreviewedProteinCount()
                + ", mappedProteinCount="
                + mappedProteinCount
                + '}';
    }
}
