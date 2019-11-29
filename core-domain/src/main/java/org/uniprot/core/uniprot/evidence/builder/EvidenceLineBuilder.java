package org.uniprot.core.uniprot.evidence.builder;

import java.time.LocalDate;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.impl.EvidenceLineImpl;

/**
 * Created 25/01/19
 *
 * @author Edd
 */
public class EvidenceLineBuilder implements Builder<EvidenceLineBuilder, EvidenceLine> {
    private String evidence;
    private LocalDate createDate;
    private String curator;

    @Override
    public @Nonnull EvidenceLine build() {
        return new EvidenceLineImpl(evidence, createDate, curator);
    }

    @Override
    public @Nonnull EvidenceLineBuilder from(@Nonnull EvidenceLine instance) {
        return this.evidence(instance.getEvidence())
                .curator(instance.getCurator())
                .creationDate(instance.getCreateDate());
    }

    public @Nonnull EvidenceLineBuilder evidence(String evidence) {
        this.evidence = evidence;
        return this;
    }

    public @Nonnull EvidenceLineBuilder creationDate(LocalDate date) {
        this.createDate = date;
        return this;
    }

    public @Nonnull EvidenceLineBuilder curator(String curator) {
        this.curator = curator;
        return this;
    }
}
