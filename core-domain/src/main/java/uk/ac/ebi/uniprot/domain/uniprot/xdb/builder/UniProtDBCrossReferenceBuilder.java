package uk.ac.ebi.uniprot.domain.uniprot.xdb.builder;

import uk.ac.ebi.uniprot.domain.citation.builder.AbstractDBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class UniProtDBCrossReferenceBuilder extends AbstractDBCrossReferenceBuilder<UniProtDBCrossReferenceBuilder, UniProtXDbType, UniProtDBCrossReference> {
    private String isoformId;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public UniProtDBCrossReference build() {
        return new UniProtDBCrossReferenceImpl(databaseType, id, properties, isoformId, evidences);
    }

    @Override
    public UniProtDBCrossReferenceBuilder from(UniProtDBCrossReference instance) {
        evidences.clear();
        return super.from(instance)
                .evidences(instance.getEvidences())
                .isoformId(instance.getIsoformId());
    }

    @Override
    protected UniProtDBCrossReferenceBuilder getThis() {
        return this;
    }

    public UniProtDBCrossReferenceBuilder isoformId(String isoformId) {
        this.isoformId = isoformId;
        return this;
    }

    public UniProtDBCrossReferenceBuilder evidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
        return this;
    }

    public UniProtDBCrossReferenceBuilder evidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }
}
