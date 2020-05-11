package org.uniprot.core.uniprotkb.evidence.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class EvidencedValueBuilder
        extends AbstractEvidencedValueBuilder<EvidencedValueBuilder, EvidencedValue> {
    public EvidencedValueBuilder() {}

    public EvidencedValueBuilder(String value, List<Evidence> evidences) {
        this.value = value;
        this.evidences = modifiableList(evidences);
    }

    @Override
    public @Nonnull EvidencedValue build() {
        return new EvidencedValueImpl(value, evidences);
    }

    @Override
    protected @Nonnull EvidencedValueBuilder getThis() {
        return this;
    }

    public static @Nonnull EvidencedValueBuilder from(@Nonnull EvidencedValue instance) {
        EvidencedValueBuilder builder = new EvidencedValueBuilder();
        AbstractEvidencedValueBuilder.init(builder, instance);
        return builder;
    }
}
