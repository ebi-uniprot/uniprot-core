package org.uniprot.core.uniprot.xdb.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Property;
import org.uniprot.core.builder.AbstractDBCrossReferenceBuilder;
import org.uniprot.core.cv.xdb.DBXRefTypeAttribute;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;
import org.uniprot.core.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;

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