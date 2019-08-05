package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalSection;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.impl.InternalSectionImpl;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class InternalSectionBuilder implements Builder<InternalSectionBuilder, InternalSection> {
    private List<InternalLine> internalLines = new ArrayList<>();
    private List<EvidenceLine> evidenceLines = new ArrayList<>();
    private List<SourceLine> sourceLines = new ArrayList<>();

    @Override
    public InternalSection build() {
        return new InternalSectionImpl(internalLines, evidenceLines, sourceLines);
    }

    @Override
    public InternalSectionBuilder from(InternalSection instance) {
        this.evidenceLines(instance.getEvidenceLines());
        this.internalLines(instance.getInternalLines());
        this.sourceLines(instance.getSourceLines());
        return this;
    }

    public InternalSectionBuilder internalLines(List<InternalLine> internalLines) {
        this.internalLines = nonNullList(internalLines);
        return this;
    }

    public InternalSectionBuilder addInternalLine(InternalLine internalLine) {
        nonNullAdd(internalLine, this.internalLines);
        return this;
    }

    public InternalSectionBuilder evidenceLines(List<EvidenceLine> evidenceLines) {
        this.evidenceLines = nonNullList(evidenceLines);
        return this;
    }

    public InternalSectionBuilder addEvidenceLine(EvidenceLine evidenceLine) {
        nonNullAdd(evidenceLine, this.evidenceLines);
        return this;
    }

    public InternalSectionBuilder sourceLines(List<SourceLine> sourceLines) {
        this.sourceLines = nonNullList(sourceLines);
        return this;
    }

    public InternalSectionBuilder addSourceLine(SourceLine sourceLine) {
        nonNullAdd(sourceLine, this.sourceLines);
        return this;
    }
}
