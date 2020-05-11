package org.uniprot.core.uniref.impl;

import org.uniprot.core.uniref.UniRefMember;

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

    public static @Nonnull UniRefMemberBuilder from(@Nonnull UniRefMember instance) {
        UniRefMemberBuilder builder = new UniRefMemberBuilder();
        AbstractUniRefMemberBuilder.init(builder, instance);
        return builder;
    }

    @Override
    protected @Nonnull UniRefMemberBuilder getThis() {
        return this;
    }
}
