package org.uniprot.core.unirule;

/** @author sahmad */
public interface CaseRule<R extends RuleExceptionAnnotationType> extends Rule<R> {
    boolean isOverallStatsExempted();
}
