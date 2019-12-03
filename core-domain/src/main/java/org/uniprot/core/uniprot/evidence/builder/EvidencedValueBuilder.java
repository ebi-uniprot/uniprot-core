package org.uniprot.core.uniprot.evidence.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

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
}
