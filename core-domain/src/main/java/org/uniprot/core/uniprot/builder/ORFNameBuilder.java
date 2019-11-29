package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.gene.ORFName;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.GeneImpl;

import javax.annotation.Nonnull;

/** @author lgonzales */
public class ORFNameBuilder extends AbstractEvidencedValueBuilder<ORFNameBuilder, ORFName> {

    public ORFNameBuilder() {}

    public ORFNameBuilder(String orf, List<Evidence> evidences) {
        this.value = orf;
        this.evidences = modifiableList(evidences);
    }

    @Override
    protected @Nonnull ORFNameBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull ORFName build() {
        return new GeneImpl.ORFNameImpl(value, evidences);
    }
}
