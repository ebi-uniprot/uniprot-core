package org.uniprot.core.interpro.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.interpro.Abstract;
import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProGroup;
import org.uniprot.core.interpro.InterProType;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
public class InterProGroupBuilder implements Builder<InterProGroup> {

    private Abstract entryAbstract;
    private String name;
    private String shortName;
    private InterProAc interProAc;
    private UniProtKBAccession uniprotAccession;
    private InterProType type;

    @Override
    public InterProGroup build() {
        return new InterProGroupImpl(
                entryAbstract, name, shortName, interProAc, uniprotAccession, type);
    }

    public static @Nonnull InterProGroupBuilder from(@Nonnull InterProGroup instance) {
        return new InterProGroupBuilder()
                .entryAbstract(instance.getEntryAbstract())
                .interProAc(instance.getInterProAc())
                .name(instance.getName())
                .shortName(instance.getShortName())
                .uniprotAccession(instance.getUniProtAccession())
                .type(instance.getType());
    }

    public @Nonnull InterProGroupBuilder entryAbstract(Abstract entryAbstract) {
        this.entryAbstract = entryAbstract;
        return this;
    }

    public @Nonnull InterProGroupBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull InterProGroupBuilder shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public @Nonnull InterProGroupBuilder interProAc(InterProAc interProAc) {
        this.interProAc = interProAc;
        return this;
    }

    public @Nonnull InterProGroupBuilder uniprotAccession(UniProtKBAccession uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
        return this;
    }

    public @Nonnull InterProGroupBuilder type(InterProType type) {
        this.type = type;
        return this;
    }
}
