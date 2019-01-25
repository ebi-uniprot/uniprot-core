package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.impl.InternalSectionImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgonzales
 */
public class InternalSectionBuilder implements Builder2<InternalSectionBuilder, InternalSection> {
    private List<InternalLine> internalLines = new ArrayList<>();
    private List<EvidenceLine> evidenceLines = new ArrayList<>();
    private List<SourceLine> sourceLines = new ArrayList<>();

    public InternalSectionBuilder addInternalLine(InternalLine internalLines) {
        this.internalLines.add(internalLines);
        return this;
    }

    public InternalSectionBuilder internalLines(List<InternalLine> internalLines) {
        this.internalLines.addAll(internalLines);
        return this;
    }

    public InternalSectionBuilder addEvidenceLine(EvidenceLine evidenceLine) {
        this.evidenceLines.add(evidenceLine);
        return this;
    }

    public InternalSectionBuilder evidenceLines(List<EvidenceLine> evidenceLines) {
        this.evidenceLines.addAll(evidenceLines);
        return this;
    }

    public InternalSectionBuilder addSourceLine(SourceLine sourceLines) {
        this.sourceLines.add(sourceLines);
        return this;
    }
    public InternalSectionBuilder sourceLines(List<SourceLine> sourceLines) {
        this.sourceLines.addAll(sourceLines);
        return this;
    }



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
}