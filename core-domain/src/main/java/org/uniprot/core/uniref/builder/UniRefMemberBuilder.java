package org.uniprot.core.uniref.builder;

import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.impl.UniRefMemberImpl;

import javax.annotation.Nonnull;

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
                accessions,
                uniref50Id,
                uniref90Id,
                uniref100Id,
                uniparcId,
                overlapRegion,
                seed);
    }

    @Override
    public UniRefMemberBuilder from(@Nonnull UniRefMember instance) {
        return super.fromMember(instance);
    }

    @Override
    protected UniRefMemberBuilder getThis() {
        return this;
    }
}
