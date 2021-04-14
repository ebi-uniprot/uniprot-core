package org.uniprot.core.interpro.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.interpro.InterProMatch;
import org.uniprot.core.interpro.InterProMatchContainer;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 14 Apr 2021
 */
public class InterProMatchContainerBuilder implements Builder<InterProMatchContainer> {
    private long id;
    private UniProtKBAccession uniProtAccession;
    private List<InterProMatch> interProMatches = new ArrayList<>();

    @Override
    public InterProMatchContainer build() {
        return new InterProMatchContainerImpl(id, uniProtAccession, interProMatches);
    }

    public static @Nonnull InterProMatchContainerBuilder from(
            @Nonnull InterProMatchContainer instance) {
        return new InterProMatchContainerBuilder()
                .id(instance.getId())
                .uniProtAccession(instance.getUniProtAccession())
                .interProMatchesSet(instance.getInterProMatches());
    }

    public @Nonnull InterProMatchContainerBuilder id(long id) {
        this.id = id;
        return this;
    }

    public @Nonnull InterProMatchContainerBuilder uniProtAccession(
            UniProtKBAccession uniProtAccession) {
        this.uniProtAccession = uniProtAccession;
        return this;
    }

    public @Nonnull InterProMatchContainerBuilder interProMatchesSet(
            List<InterProMatch> interProMatches) {
        this.interProMatches = Utils.modifiableList(interProMatches);
        return this;
    }

    public @Nonnull InterProMatchContainerBuilder interProMatchesAdd(InterProMatch interProMatch) {
        Utils.addOrIgnoreNull(interProMatch, this.interProMatches);
        return this;
    }
}
