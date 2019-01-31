package uk.ac.ebi.uniprot.domain.uniprot.evidence.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceImpl;

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
        if (databaseName == null && databaseId == null) {
            return new EvidenceImpl(evidenceCode, null);
        } else {
            return new EvidenceImpl(evidenceCode, databaseName, databaseId);
        }
    }

    @Override
    public EvidenceBuilder from(Evidence instance) {
        DBCrossReference<EvidenceType> source = instance.getSource();
        return new EvidenceBuilder()
                .evidenceCode(instance.getEvidenceCode())
                .databaseId(source.getId())
                .databaseName(source.getDatabaseType().getName());
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
