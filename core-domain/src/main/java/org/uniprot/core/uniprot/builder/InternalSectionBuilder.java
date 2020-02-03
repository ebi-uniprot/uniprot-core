package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

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
public class InternalSectionBuilder implements Builder<InternalSection> {
    private List<InternalLine> internalLines = new ArrayList<>();
    private List<EvidenceLine> evidenceLines = new ArrayList<>();
    private List<SourceLine> sourceLines = new ArrayList<>();

    @Override
    public @Nonnull InternalSection build() {
        return new InternalSectionImpl(internalLines, evidenceLines, sourceLines);
    }

    public static @Nonnull InternalSectionBuilder from(@Nonnull InternalSection instance) {
        return new InternalSectionBuilder()
                .evidenceLines(instance.getEvidenceLines())
                .internalLines(instance.getInternalLines())
                .sourceLines(instance.getSourceLines());
    }

    public @Nonnull InternalSectionBuilder internalLines(List<InternalLine> internalLines) {
        this.internalLines = modifiableList(internalLines);
        return this;
    }

    public @Nonnull InternalSectionBuilder addInternalLine(InternalLine internalLine) {
        addOrIgnoreNull(internalLine, this.internalLines);
        return this;
    }

    public @Nonnull InternalSectionBuilder evidenceLines(List<EvidenceLine> evidenceLines) {
        this.evidenceLines = modifiableList(evidenceLines);
        return this;
    }

    public @Nonnull InternalSectionBuilder addEvidenceLine(EvidenceLine evidenceLine) {
        addOrIgnoreNull(evidenceLine, this.evidenceLines);
        return this;
    }

    public @Nonnull InternalSectionBuilder sourceLines(List<SourceLine> sourceLines) {
        this.sourceLines = modifiableList(sourceLines);
        return this;
    }

    public @Nonnull InternalSectionBuilder addSourceLine(SourceLine sourceLine) {
        addOrIgnoreNull(sourceLine, this.sourceLines);
        return this;
    }
}
