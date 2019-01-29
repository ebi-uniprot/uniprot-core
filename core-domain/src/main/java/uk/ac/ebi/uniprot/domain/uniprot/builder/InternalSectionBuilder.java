package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.impl.InternalSectionImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class InternalSectionBuilder implements Builder2<InternalSectionBuilder, InternalSection> {
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
        nonNullAddAll(internalLines, this.internalLines);
        return this;
    }

    public InternalSectionBuilder addInternalLine(InternalLine internalLine) {
        nonNullAdd(internalLine, this.internalLines);
        return this;
    }

    public InternalSectionBuilder evidenceLines(List<EvidenceLine> evidenceLines) {
        nonNullAddAll(evidenceLines, this.evidenceLines);
        return this;
    }

    public InternalSectionBuilder addEvidenceLine(EvidenceLine evidenceLine) {
        nonNullAdd(evidenceLine, this.evidenceLines);
        return this;
    }

    public InternalSectionBuilder sourceLines(List<SourceLine> sourceLines) {
        nonNullAddAll(sourceLines, this.sourceLines);
        return this;
    }

    public InternalSectionBuilder addSourceLine(SourceLine sourceLine) {
        nonNullAdd(sourceLine, this.sourceLines);
        return this;
    }
}
