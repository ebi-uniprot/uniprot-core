package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.uniprotkb.EntryInactiveReason;
import org.uniprot.core.uniprotkb.InactiveReasonType;
import org.uniprot.core.util.Utils;

import java.util.Collections;
import java.util.List;

public class EntryInactiveReasonImpl implements EntryInactiveReason {
    private static final long serialVersionUID = 1358481260367130982L;
    private InactiveReasonType inactiveReasonType;
    private List<String> mergeDemergeTo;

    EntryInactiveReasonImpl() {
        this.mergeDemergeTo = Collections.emptyList();
    }

    EntryInactiveReasonImpl(InactiveReasonType inactiveReasonType, List<String> mergeDemergeTo) {
        this.inactiveReasonType = inactiveReasonType;
        this.mergeDemergeTo = Utils.unmodifiableList(mergeDemergeTo);
    }

    @Override
    public InactiveReasonType getInactiveReasonType() {
        return inactiveReasonType;
    }

    @Override
    public List<String> getMergeDemergeTos() {
        return mergeDemergeTo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result + ((inactiveReasonType == null) ? 0 : inactiveReasonType.hashCode());
        result = prime * result + ((mergeDemergeTo == null) ? 0 : mergeDemergeTo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EntryInactiveReasonImpl other = (EntryInactiveReasonImpl) obj;
        if (inactiveReasonType != other.inactiveReasonType) return false;
        return mergeDemergeTo.equals(other.mergeDemergeTo);
    }
}
