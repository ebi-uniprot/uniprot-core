package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

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