package org.uniprot.core.uniref.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.impl.UniRefMemberImpl;

public class UniRefMemberBuilder
        extends AbstractUniRefMemberBuilder<UniRefMemberBuilder, UniRefMember> {

    @Override
    public @Nonnull UniRefMember build() {
        return new UniRefMemberImpl(
                memberIdType,
                memberId,
                organismName,
                organismTaxId,
                sequenceLength,
                proteinName,
                accession,
                uniref50Id,
                uniref90Id,
                uniref100Id,
                uniparcId,
                overlapRegion,
                seed);
    }

    @Override
    public UniRefMemberBuilder from(@Nonnull UniRefMember instance) {
        super.init(instance);
        return this;
    }

    @Override
    protected UniRefMemberBuilder getThis() {
        return this;
    }
}
