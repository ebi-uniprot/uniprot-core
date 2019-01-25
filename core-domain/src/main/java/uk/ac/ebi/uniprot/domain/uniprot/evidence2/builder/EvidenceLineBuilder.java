package uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidenceLineImpl;

import java.time.LocalDate;

/**
 * Created 25/01/19
 *
 * @author Edd
 */
public class EvidenceLineBuilder implements Builder2<EvidenceLineBuilder, EvidenceLine> {
    private String evidence;
    private LocalDate createDate;
    private String curator;

    @Override
    public EvidenceLine build() {
        return new EvidenceLineImpl(evidence, createDate, curator);
    }

    @Override
    public EvidenceLineBuilder from(EvidenceLine instance) {
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
