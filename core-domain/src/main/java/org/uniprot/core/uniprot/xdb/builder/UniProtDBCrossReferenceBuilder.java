package org.uniprot.core.uniprot.xdb.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

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
public class UniProtDBCrossReferenceBuilder
        extends AbstractDBCrossReferenceBuilder<
                UniProtDBCrossReferenceBuilder, UniProtXDbType, UniProtDBCrossReference> {
    private String isoformId;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull UniProtDBCrossReference build() {
        return new UniProtDBCrossReferenceImpl(databaseType, id, properties, isoformId, evidences);
    }

    public static @Nonnull UniProtDBCrossReferenceBuilder from(@Nonnull UniProtDBCrossReference instance) {
        UniProtDBCrossReferenceBuilder builder = new UniProtDBCrossReferenceBuilder();
        AbstractDBCrossReferenceBuilder.init(builder, instance);
        return builder.evidences(instance.getEvidences())
                .isoformId(instance.getIsoformId());
    }

    public @Nonnull UniProtDBCrossReferenceBuilder isoformId(String isoformId) {
        this.isoformId = isoformId;
        return this;
    }

    public @Nonnull UniProtDBCrossReferenceBuilder evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull UniProtDBCrossReferenceBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull UniProtDBCrossReferenceBuilder addProperty(
            DBXRefTypeAttribute attribute, String value) {
        if (notNullOrEmpty(value) && notNull(attribute)) {
            this.properties.add(new Property(attribute.getName(), value));
        }
        return this;
    }

    @Override
    protected @Nonnull UniProtDBCrossReferenceBuilder getThis() {
        return this;
    }
}
