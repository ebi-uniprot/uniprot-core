package org.uniprot.core.uniprot.xdb.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Property;
import org.uniprot.core.builder.AbstractCrossReferenceBuilder;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtDatabase;
import org.uniprot.core.uniprot.xdb.impl.UniProtCrossReferenceImpl;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class UniProtCrossReferenceBuilder
        extends AbstractCrossReferenceBuilder<
                UniProtCrossReferenceBuilder, UniProtDatabase, UniProtCrossReference> {
    private String isoformId;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull UniProtCrossReference build() {
        return new UniProtCrossReferenceImpl(database, id, properties, isoformId, evidences);
    }

    public static @Nonnull UniProtCrossReferenceBuilder from(
            @Nonnull UniProtCrossReference instance) {
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
