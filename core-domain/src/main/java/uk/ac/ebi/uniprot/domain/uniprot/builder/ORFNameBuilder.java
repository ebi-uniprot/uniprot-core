package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import static org.uniprot.core.common.Utils.nonNullList;

import java.util.List;

/**
 *
 * @author lgonzales
 */
public class ORFNameBuilder extends AbstractEvidencedValueBuilder<ORFNameBuilder, ORFName> {

    public ORFNameBuilder(){

    }

    public ORFNameBuilder(String orf, List<Evidence> evidences) {
        this.value = orf;
        this.evidences = nonNullList(evidences);
    }

    @Override
    protected ORFNameBuilder getThis() {
        return this;
    }

    @Override
    public ORFName build() {
        return new GeneImpl.ORFNameImpl(value,evidences);
    }
}
