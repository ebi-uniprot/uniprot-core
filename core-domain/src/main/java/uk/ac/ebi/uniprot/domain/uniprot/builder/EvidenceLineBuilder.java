package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceLineImpl;

import java.time.LocalDate;

/**
 * @author lgonzales
 */
public class EvidenceLineBuilder implements Builder2<EvidenceLineBuilder, EvidenceLine> {
    private String evidence;
    private LocalDate createDate;
    private String curator;

    public EvidenceLineBuilder evidence(String evidence) {
        this.evidence = evidence;
        return this;
    }

    public EvidenceLineBuilder createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public EvidenceLineBuilder curator(String curator) {
        this.curator = curator;
        return this;
    }

    @Override
    public EvidenceLine build() {
        return new EvidenceLineImpl(evidence, createDate, curator);
    }

    @Override
    public EvidenceLineBuilder from(EvidenceLine instance) {
        this.evidence(instance.getEvidence());
        this.createDate(instance.getCreateDate());
        this.curator(instance.getCurator());
        return this;
    }
}