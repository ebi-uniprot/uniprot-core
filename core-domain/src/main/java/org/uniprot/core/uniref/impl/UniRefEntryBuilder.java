package org.uniprot.core.uniref.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
public class UniRefEntryBuilder implements Builder<UniRefEntry> {
    private UniRefEntryId id;
    private String name;
    private Integer memberCount;
    private LocalDate updated;
    private UniRefType entryType;
    private Organism commonTaxon;
    private String seedId;
    private List<GeneOntologyEntry> goTerms = new ArrayList<>();
    private RepresentativeMember representativeMember;
    private List<UniRefMember> members = new ArrayList<>();

    @Override
    public @Nonnull UniRefEntry build() {
        return new UniRefEntryImpl(
                id,
                name,
                memberCount,
                updated,
                entryType,
                commonTaxon,
                seedId,
                goTerms,
                representativeMember,
                members);
    }

    public static @Nonnull UniRefEntryBuilder from(@Nonnull UniRefEntry instance) {
        return new UniRefEntryBuilder()
                .id(instance.getId())
                .name(instance.getName())
                .updated(instance.getUpdated())
                .entryType(instance.getEntryType())
                .commonTaxon(instance.getCommonTaxon())
                .seedId(instance.getSeedId())
                .goTermsSet(instance.getGoTerms())
                .representativeMember(instance.getRepresentativeMember())
                .membersSet(instance.getMembers());
    }

    public @Nonnull UniRefEntryBuilder id(UniRefEntryId id) {
        this.id = id;
        return this;
    }

    public @Nonnull UniRefEntryBuilder id(String id) {
        this.id = new UniRefEntryIdBuilder(id).build();
        return this;
    }

    public @Nonnull UniRefEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull UniRefEntryBuilder updated(LocalDate updated) {
        this.updated = updated;
        return this;
    }

    public @Nonnull UniRefEntryBuilder entryType(UniRefType entryType) {
        this.entryType = entryType;
        return this;
    }

    public @Nonnull UniRefEntryBuilder commonTaxon(Organism commonTaxon) {
        this.commonTaxon = commonTaxon;
        return this;
    }

    public @Nonnull UniRefEntryBuilder seedId(String seedId) {
        this.seedId = seedId;
        return this;
    }

    public @Nonnull UniRefEntryBuilder goTermsSet(List<GeneOntologyEntry> goTerms) {
        this.goTerms = Utils.modifiableList(goTerms);
        return this;
    }

    public @Nonnull UniRefEntryBuilder goTermsAdd(GeneOntologyEntry goTerm) {
        Utils.addOrIgnoreNull(goTerm, this.goTerms);
        return this;
    }

    public @Nonnull UniRefEntryBuilder representativeMember(
            RepresentativeMember representativeMember) {
        this.representativeMember = representativeMember;
        return this;
    }

    public @Nonnull UniRefEntryBuilder membersSet(List<UniRefMember> unirefMembers) {
        this.members = Utils.modifiableList(unirefMembers);
        return this;
    }

    public @Nonnull UniRefEntryBuilder membersAdd(UniRefMember unirefMember) {
        Utils.addOrIgnoreNull(unirefMember, this.members);
        return this;
    }

    public @Nonnull UniRefEntryBuilder memberCount(Integer memberCount) {
        this.memberCount = memberCount;
        return this;
    }
}
