package org.uniprot.core.uniprotkb.evidence.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.evidence.EvidenceLine;

import java.time.LocalDate;

import javax.annotation.Nonnull;

/**
 * Created 25/01/19
 *
 * @author Edd
 */
public class EvidenceLineBuilder implements Builder<EvidenceLine> {
    private String evidence;
    private LocalDate createDate;
    private String curator;

    @Override
    public @Nonnull EvidenceLine build() {
        return new EvidenceLineImpl(evidence, createDate, curator);
    }

    public static @Nonnull EvidenceLineBuilder from(@Nonnull EvidenceLine instance) {
        return new EvidenceLineBuilder()
                .evidence(instance.getEvidence())
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
