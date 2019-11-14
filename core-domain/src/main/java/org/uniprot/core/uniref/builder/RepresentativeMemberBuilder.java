package org.uniprot.core.uniref.builder;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.impl.RepresentativeMemberImpl;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class RepresentativeMemberBuilder
        extends AbstractUniRefMemberBuilder<RepresentativeMemberBuilder, RepresentativeMember> {
    private Sequence sequence;

    public RepresentativeMemberBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    @Override
    public RepresentativeMember build() {
        return new RepresentativeMemberImpl(
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
                seed,
                sequence);
    }

    @Override
    public RepresentativeMemberBuilder from(RepresentativeMember instance) {
        RepresentativeMemberBuilder builder = super.fromMember(instance);
        builder.sequence(instance.getSequence());
        return builder;
    }

    public RepresentativeMemberBuilder from(UniRefMember instance) {
        return super.fromMember(instance);
    }

    @Override
    protected RepresentativeMemberBuilder getThis() {
        return this;
    }
}
