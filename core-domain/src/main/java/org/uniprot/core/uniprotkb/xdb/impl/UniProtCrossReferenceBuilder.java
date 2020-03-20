package org.uniprot.core.uniprotkb.xdb.impl;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Property;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.impl.AbstractCrossReferenceBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class UniProtCrossReferenceBuilder
        extends AbstractCrossReferenceBuilder<
                UniProtCrossReferenceBuilder, UniProtKBDatabase, UniProtKBCrossReference> {
    private String isoformId;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull UniProtKBCrossReference build() {
        return new UniProtKBCrossReferenceImpl(database, id, properties, isoformId, evidences);
    }

    public static @Nonnull UniProtCrossReferenceBuilder from(
            @Nonnull UniProtKBCrossReference instance) {
        UniProtCrossReferenceBuilder builder = new UniProtCrossReferenceBuilder();
        AbstractCrossReferenceBuilder.init(builder, instance);
        return builder.evidencesSet(instance.getEvidences()).isoformId(instance.getIsoformId());
    }

    public @Nonnull UniProtCrossReferenceBuilder isoformId(String isoformId) {
        this.isoformId = isoformId;
        return this;
    }

    public @Nonnull UniProtCrossReferenceBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull UniProtCrossReferenceBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull UniProtCrossReferenceBuilder propertiesAdd(
            UniProtDatabaseAttribute attribute, String value) {
        if (notNullNotEmpty(value) && notNull(attribute)) {
            this.properties.add(new Property(attribute.getName(), value));
        }
        return this;
    }

    @Override
    protected @Nonnull UniProtCrossReferenceBuilder getThis() {
        return this;
    }
}
