package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.nonNullList;

import java.util.List;

import org.uniprot.core.gene.ORFName;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.GeneImpl;

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
