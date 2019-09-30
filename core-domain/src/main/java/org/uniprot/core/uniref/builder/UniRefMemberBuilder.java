package org.uniprot.core.uniref.builder;

import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.impl.UniRefMemberImpl;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class UniRefMemberBuilder
        extends AbstractUniRefMemberBuilder<UniRefMemberBuilder, UniRefMember> {

    @Override
    public UniRefMember build() {
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
    public UniRefMemberBuilder from(UniRefMember instance) {
        super.init(instance);
        return this;
    }

    @Override
    protected UniRefMemberBuilder getThis() {
        return this;
    }
}
