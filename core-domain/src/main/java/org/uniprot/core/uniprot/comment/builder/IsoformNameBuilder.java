package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullList;

import java.util.List;

import org.uniprot.core.uniprot.comment.IsoformName;
import org.uniprot.core.uniprot.comment.impl.APIsoformImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class IsoformNameBuilder extends AbstractEvidencedValueBuilder<IsoformNameBuilder, IsoformName> {
    private IsoformNameBuilder() {}

    public IsoformNameBuilder(String value, List<Evidence> evidences) {
        this.value = value;
        this.evidences = nonNullList(evidences);
    }

    @Override
    public IsoformName build() {
        return new APIsoformImpl.IsoformNameImpl(value, evidences);
    }

    @Override
    protected IsoformNameBuilder getThis() {
        return this;
    }
}