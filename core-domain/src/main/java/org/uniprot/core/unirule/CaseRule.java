package org.uniprot.core.unirule;

/** @author sahmad */
public interface CaseRule<R> extends Rule<R> {
    boolean isOverallStatsExempted();
}
