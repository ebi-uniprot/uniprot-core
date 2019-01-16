package uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class EvidenceBuilder implements Builder2<EvidenceBuilder, Evidence> {
    private EvidenceCode evidenceCode;
    private String databaseName;
    private String databaseId;

    @Override
    public Evidence build() {
        return null;
    }

    @Override
    public EvidenceBuilder from(Evidence instance) {
        return new EvidenceBuilder(); // TODO: 16/01/19 HERE
    }

    public EvidenceBuilder evidenceCode(EvidenceCode evidenceCode) {
        this.evidenceCode = evidenceCode;
        return this;
    }

    public EvidenceBuilder databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public EvidenceBuilder databaseId(String databaseId) {
        this.databaseId = databaseId;
        return this;
    }

    public EvidenceCode getEvidenceCode() {
        return evidenceCode;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseId() {
        return databaseId;
    }
}
