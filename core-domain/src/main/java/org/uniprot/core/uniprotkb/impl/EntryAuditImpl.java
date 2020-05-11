package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.uniprotkb.EntryAudit;

import java.time.LocalDate;

public final class EntryAuditImpl implements EntryAudit {
    private static final long serialVersionUID = -232879461828356252L;
    private LocalDate firstPublicDate;
    private LocalDate lastAnnotationUpdateDate;
    private LocalDate lastSequenceUpdateDate;
    private int entryVersion;
    private int sequenceVersion;

    EntryAuditImpl() {}

    EntryAuditImpl(
            LocalDate firstPublicDate,
            LocalDate lastAnnotationUpdateDate,
            LocalDate lastSequenceUpdateDate,
            int entryVersion,
            int sequenceVersion) {
        this.firstPublicDate = firstPublicDate;
        this.lastAnnotationUpdateDate = lastAnnotationUpdateDate;
        this.lastSequenceUpdateDate = lastSequenceUpdateDate;
        this.entryVersion = entryVersion;
        this.sequenceVersion = sequenceVersion;
    }

    @Override
    public LocalDate getFirstPublicDate() {
        return firstPublicDate;
    }

    @Override
    public LocalDate getLastAnnotationUpdateDate() {
        return lastAnnotationUpdateDate;
    }

    @Override
    public LocalDate getLastSequenceUpdateDate() {
        return lastSequenceUpdateDate;
    }

    @Override
    public int getEntryVersion() {
        return entryVersion;
    }

    @Override
    public int getSequenceVersion() {
        return sequenceVersion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + entryVersion;
        result = prime * result + ((firstPublicDate == null) ? 0 : firstPublicDate.hashCode());
        result =
                prime * result
                        + ((lastAnnotationUpdateDate == null)
                                ? 0
                                : lastAnnotationUpdateDate.hashCode());
        result =
                prime * result
                        + ((lastSequenceUpdateDate == null)
                                ? 0
                                : lastSequenceUpdateDate.hashCode());
        result = prime * result + sequenceVersion;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EntryAuditImpl other = (EntryAuditImpl) obj;
        if (entryVersion != other.entryVersion) return false;
        if (firstPublicDate == null) {
            if (other.firstPublicDate != null) return false;
        } else if (!firstPublicDate.equals(other.firstPublicDate)) return false;
        if (lastAnnotationUpdateDate == null) {
            if (other.lastAnnotationUpdateDate != null) return false;
        } else if (!lastAnnotationUpdateDate.equals(other.lastAnnotationUpdateDate)) return false;
        if (lastSequenceUpdateDate == null) {
            if (other.lastSequenceUpdateDate != null) return false;
        } else if (!lastSequenceUpdateDate.equals(other.lastSequenceUpdateDate)) return false;
        return sequenceVersion == other.sequenceVersion;
    }
}
