package org.uniprot.core.uniref.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.UniRefEntryImpl;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
public class UniRefEntryBuilder implements Builder<UniRefEntryBuilder, UniRefEntry> {
    private UniRefEntryId id;
    private String name;
    private int memberCount;
    private LocalDate updated;
    private UniRefType entryType;
    private long commonTaxonId;
    private String commonTaxon;
    private List<GoTerm> goTerms = new ArrayList<>();
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
                commonTaxonId,
                commonTaxon,
                goTerms,
                representativeMember,
                members);
    }

    public static @Nonnull UniRefEntryBuilder from(@Nonnull UniRefEntry instance) {
        return new UniRefEntryBuilder().id(instance.getId())
                .name(instance.getName())
                .updated(instance.getUpdated())
                .entryType(instance.getEntryType())
                .commonTaxonId(instance.getCommonTaxonId())
                .commonTaxon(instance.getCommonTaxon())
                .goTerms(instance.getGoTerms())
                .representativeMember(instance.getRepresentativeMember())
                .members(instance.getMembers());
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

    public @Nonnull UniRefEntryBuilder commonTaxonId(long commonTaxonId) {
        this.commonTaxonId = commonTaxonId;
        return this;
    }

    public @Nonnull UniRefEntryBuilder commonTaxon(String commonTaxon) {
        this.commonTaxon = commonTaxon;
        return this;
    }

    public @Nonnull UniRefEntryBuilder goTerms(List<GoTerm> goTerms) {
        this.goTerms = Utils.modifiableList(goTerms);
        return this;
    }

    public @Nonnull UniRefEntryBuilder addGoTerm(GoTerm goTerm) {
        Utils.addOrIgnoreNull(goTerm, this.goTerms);
        return this;
    }

    public @Nonnull UniRefEntryBuilder representativeMember(
            RepresentativeMember representativeMember) {
        this.representativeMember = representativeMember;
        return this;
    }

    public @Nonnull UniRefEntryBuilder members(List<UniRefMember> unirefMembers) {
        this.members = Utils.modifiableList(unirefMembers);
        return this;
    }

    public @Nonnull UniRefEntryBuilder addMember(UniRefMember unirefMember) {
        Utils.addOrIgnoreNull(unirefMember, this.members);
        return this;
    }

    public @Nonnull UniRefEntryBuilder memberCount(int memberCount) {
        this.memberCount = memberCount;
        return this;
    }
}
