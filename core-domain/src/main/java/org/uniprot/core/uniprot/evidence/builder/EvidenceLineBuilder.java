package org.uniprot.core.uniprot.evidence.builder;

import java.time.LocalDate;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.impl.EvidenceLineImpl;

import javax.annotation.Nonnull;

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

    public EvidenceLineBuilder evidence(String evidence) {
        this.evidence = evidence;
        return this;
    }

    public EvidenceLineBuilder creationDate(LocalDate date) {
        this.createDate = date;
        return this;
    }

    public EvidenceLineBuilder curator(String curator) {
        this.curator = curator;
        return this;
    }
}
