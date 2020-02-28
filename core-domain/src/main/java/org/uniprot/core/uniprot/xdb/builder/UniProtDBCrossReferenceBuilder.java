package org.uniprot.core.uniprot.xdb.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Property;
import org.uniprot.core.builder.AbstractDBCrossReferenceBuilder;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtDatabase;
import org.uniprot.core.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class UniProtDBCrossReferenceBuilder
        extends AbstractDBCrossReferenceBuilder<
                UniProtDBCrossReferenceBuilder, UniProtDatabase, UniProtDBCrossReference> {
    private String isoformId;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull UniProtDBCrossReference build() {
        return new UniProtDBCrossReferenceImpl(databaseType, id, properties, isoformId, evidences);
    }

    public static @Nonnull UniProtDBCrossReferenceBuilder from(
            @Nonnull UniProtDBCrossReference instance) {
        UniProtDBCrossReferenceBuilder builder = new UniProtDBCrossReferenceBuilder();
        AbstractDBCrossReferenceBuilder.init(builder, instance);
        return builder.evidencesSet(instance.getEvidences()).isoformId(instance.getIsoformId());
    }

    public @Nonnull UniProtDBCrossReferenceBuilder isoformId(String isoformId) {
        this.isoformId = isoformId;
        return this;
    }

    public @Nonnull UniProtDBCrossReferenceBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull UniProtDBCrossReferenceBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull UniProtDBCrossReferenceBuilder propertiesAdd(
            UniProtDatabaseAttribute attribute, String value) {
        if (notNullNotEmpty(value) && notNull(attribute)) {
            this.properties.add(new Property(attribute.getName(), value));
        }
        return this;
    }

    @Override
    protected @Nonnull UniProtDBCrossReferenceBuilder getThis() {
        return this;
    }
}
