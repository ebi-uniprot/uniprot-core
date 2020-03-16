package org.uniprot.core.uniprotkb.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprotkb.InternalLine;
import org.uniprot.core.uniprotkb.InternalSection;
import org.uniprot.core.uniprotkb.SourceLine;
import org.uniprot.core.uniprotkb.evidence.EvidenceLine;
import org.uniprot.core.util.Utils;

public class InternalSectionImpl implements InternalSection {
    private static final long serialVersionUID = 3250758805630819227L;
    private List<InternalLine> internalLines;
    private List<EvidenceLine> evidenceLines;
    private List<SourceLine> sourceLines;

    // no arg constructor for JSON deserialization
    InternalSectionImpl() {
        this.internalLines = Collections.emptyList();
        this.evidenceLines = Collections.emptyList();
        this.sourceLines = Collections.emptyList();
    }

    InternalSectionImpl(
            List<InternalLine> internalLines,
            List<EvidenceLine> evidenceLines,
            List<SourceLine> sourceLines) {
        this.internalLines = Utils.unmodifiableList(internalLines);
        this.evidenceLines = Utils.unmodifiableList(evidenceLines);
        this.sourceLines = Utils.unmodifiableList(sourceLines);
    }

    @Override
    public List<InternalLine> getInternalLines() {
        return internalLines;
    }

    @Override
    public List<SourceLine> getSourceLines() {
        return sourceLines;
    }

    @Override
    public List<EvidenceLine> getEvidenceLines() {
        return evidenceLines;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((evidenceLines == null) ? 0 : evidenceLines.hashCode());
        result = prime * result + ((internalLines == null) ? 0 : internalLines.hashCode());
        result = prime * result + ((sourceLines == null) ? 0 : sourceLines.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        InternalSectionImpl other = (InternalSectionImpl) obj;
        if (!evidenceLines.equals(other.evidenceLines)) return false;
        if (!internalLines.equals(other.internalLines)) return false;
        return sourceLines.equals(other.sourceLines);
    }
}
