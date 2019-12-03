package org.uniprot.core.uniref.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.impl.RepresentativeMemberImpl;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class RepresentativeMemberBuilder
        extends AbstractUniRefMemberBuilder<RepresentativeMemberBuilder, RepresentativeMember> {
    private Sequence sequence;

    public @Nonnull RepresentativeMemberBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    @Override
    public @Nonnull RepresentativeMember build() {
        return new RepresentativeMemberImpl(
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
                seed,
                sequence);
    }

    @Override
    public @Nonnull RepresentativeMemberBuilder from(@Nonnull RepresentativeMember instance) {
        super.init(instance);
        return sequence(instance.getSequence());
    }

    @Override
    protected @Nonnull RepresentativeMemberBuilder getThis() {
        return this;
    }
}
