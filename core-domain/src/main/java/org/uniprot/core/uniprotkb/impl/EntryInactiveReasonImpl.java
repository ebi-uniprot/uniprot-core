package org.uniprot.core.uniprotkb.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprotkb.DeletedReason;
import org.uniprot.core.uniprotkb.EntryInactiveReason;
import org.uniprot.core.uniprotkb.InactiveReasonType;
import org.uniprot.core.util.Utils;

public class EntryInactiveReasonImpl implements EntryInactiveReason {
    private static final long serialVersionUID = 1358481260367130982L;
    private InactiveReasonType inactiveReasonType;
    private List<String> mergeDemergeTo;
    private DeletedReason deletedReason;

    EntryInactiveReasonImpl() {
        this.mergeDemergeTo = Collections.emptyList();
    }

    EntryInactiveReasonImpl(InactiveReasonType inactiveReasonType, List<String> mergeDemergeTo, DeletedReason deletedReason) {
        this.inactiveReasonType = inactiveReasonType;
        this.mergeDemergeTo = Utils.unmodifiableList(mergeDemergeTo);
        this.deletedReason = deletedReason;
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
    public DeletedReason getDeletedReason() {
        return deletedReason;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result + ((inactiveReasonType == null) ? 0 : inactiveReasonType.hashCode());
        result = prime * result + ((mergeDemergeTo == null) ? 0 : mergeDemergeTo.hashCode());
        result = prime * result + ((deletedReason == null) ? 0 : deletedReason.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EntryInactiveReasonImpl other = (EntryInactiveReasonImpl) obj;
        if (inactiveReasonType != other.inactiveReasonType) return false;
        if (deletedReason != other.deletedReason) return false;
        return mergeDemergeTo.equals(other.mergeDemergeTo);
    }
}
