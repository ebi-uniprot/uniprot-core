package org.uniprot.core.uniref.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniref.OverlapRegion;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public abstract class AbstractUniRefMemberBuilder<
                B extends AbstractUniRefMemberBuilder<B, T>, T extends UniRefMember>
        implements Builder<T> {
    protected UniRefMemberIdType memberIdType;
    protected String memberId;
    protected String organismName;
    protected long organismTaxId;
    protected int sequenceLength;
    protected String proteinName;
    protected List<UniProtAccession> accessions = new ArrayList<>();;
    protected UniRefEntryId uniref50Id;
    protected UniRefEntryId uniref90Id;
    protected UniRefEntryId uniref100Id;
    protected UniParcId uniparcId;
    protected OverlapRegion overlapRegion;
    protected Boolean seed;

    public @Nonnull B memberIdType(UniRefMemberIdType memberIdType) {
        this.memberIdType = memberIdType;
        return getThis();
    }

    public @Nonnull B memberId(String memberId) {
        this.memberId = memberId;
        return getThis();
    }

    public @Nonnull B organismName(String organismName) {
        this.organismName = organismName;
        return getThis();
    }

    public @Nonnull B organismTaxId(long organismTaxId) {
        this.organismTaxId = organismTaxId;
        return getThis();
    }

    public @Nonnull B sequenceLength(int sequenceLength) {
        this.sequenceLength = sequenceLength;
        return getThis();
    }

    public @Nonnull B proteinName(String proteinName) {
        this.proteinName = proteinName;
        return getThis();
    }

    public @Nonnull B addAccession(UniProtAccession accession) {
        addOrIgnoreNull(accession, this.accessions);

        return getThis();
    }

    public @Nonnull B accessions(List<UniProtAccession> accessions) {
        if (accessions != null) {
            this.accessions = modifiableList(accessions);
        }

        return getThis();
    }

    public @Nonnull B uniref100Id(UniRefEntryId uniref100Id) {
        this.uniref100Id = uniref100Id;
        return getThis();
    }

    public @Nonnull B uniref90Id(UniRefEntryId uniref90Id) {
        this.uniref90Id = uniref90Id;
        return getThis();
    }

    public @Nonnull B uniref50Id(UniRefEntryId uniref50Id) {
        this.uniref50Id = uniref50Id;
        return getThis();
    }

    public @Nonnull B uniparcId(UniParcId uniparcId) {
        this.uniparcId = uniparcId;
        return getThis();
    }

    public @Nonnull B overlapRegion(OverlapRegion overlapRegion) {
        this.overlapRegion = overlapRegion;
        return getThis();
    }

    public @Nonnull B isSeed(Boolean seed) {
        this.seed = seed;
        return getThis();
    }

    protected static <B extends AbstractUniRefMemberBuilder, T extends UniRefMember> void init(
            @Nonnull B builder, @Nonnull T instance) {
        builder.memberIdType(instance.getMemberIdType());
        builder.memberId(instance.getMemberId());
        builder.organismName(instance.getOrganismName());
        builder.organismTaxId(instance.getOrganismTaxId());
        builder.sequenceLength(instance.getSequenceLength());
        builder.proteinName(instance.getProteinName());
        builder.accessions(instance.getUniProtAccessions());
        builder.uniref100Id(instance.getUniRef100Id());
        builder.uniref90Id(instance.getUniRef90Id());
        builder.uniref50Id(instance.getUniRef50Id());
        builder.uniparcId(instance.getUniParcId());
        builder.overlapRegion(instance.getOverlapRegion());
        builder.isSeed(instance.isSeed());
    }

    protected abstract @Nonnull B getThis();
}
