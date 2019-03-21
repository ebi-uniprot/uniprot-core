package uk.ac.ebi.uniprot.domain.uniprot.xdb.builder;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractDBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.cv.xdb.DBXRefTypeAttribute;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

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

    public UniProtDBCrossReferenceBuilder isoformId(String isoformId) {
        this.isoformId = isoformId;
        return this;
    }

    public UniProtDBCrossReferenceBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public UniProtDBCrossReferenceBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }

    public UniProtDBCrossReferenceBuilder addProperty(DBXRefTypeAttribute attribute, String value) {
        if (value != null && !value.isEmpty() && attribute != null) {
            this.properties.add(new Property(attribute.getName(), value));
        }
        return this;
    }

    @Override
    protected UniProtDBCrossReferenceBuilder getThis() {
        return this;
    }
}
