package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.EntryInactiveReason;
import uk.ac.ebi.uniprot.domain.uniprot.InactiveReasonType;

import java.util.Collections;
import java.util.List;

public class EntryInactiveReasonImpl implements EntryInactiveReason {
    private static final long serialVersionUID = 1358481260367130982L;
    private InactiveReasonType inactiveReasonType;
    private List<String> mergeDemergeTo;

    private EntryInactiveReasonImpl() {
        this.mergeDemergeTo = Collections.emptyList();
    }

    public EntryInactiveReasonImpl(InactiveReasonType inactiveReasonType, List<String> mergeDemergeTo) {
        this.inactiveReasonType = inactiveReasonType;
        this.mergeDemergeTo = Utils.nonNullUnmodifiableList(mergeDemergeTo);
    }

    @Override
    public InactiveReasonType getInactiveReasonType() {
        return inactiveReasonType;
    }

    @Override
    public List<String> getMergeDemergeTo() {
        return mergeDemergeTo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((inactiveReasonType == null) ? 0 : inactiveReasonType.hashCode());
        result = prime * result + ((mergeDemergeTo == null) ? 0 : mergeDemergeTo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntryInactiveReasonImpl other = (EntryInactiveReasonImpl) obj;
        if (inactiveReasonType != other.inactiveReasonType)
            return false;
        if (mergeDemergeTo == null) {
            return other.mergeDemergeTo == null;
        } else return mergeDemergeTo.equals(other.mergeDemergeTo);
    }

}
