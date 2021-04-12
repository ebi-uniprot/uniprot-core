package org.uniprot.core.interpro.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.interpro.Abstract;
import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProEntry;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
public class InterProEntryBuilder implements Builder<InterProEntry> {
    private InterProAc interProAc;
    private boolean checked;
    private String name;
    private String shortName;
    private Set<UniProtKBAccession> swissProtAccessions = new HashSet<>();
    private Set<UniProtKBAccession> tremblAccessions = new HashSet<>();
    private Abstract entryAbstract;

    @Override
    public InterProEntry build() {
        return new InterProEntryImpl(
                interProAc,
                checked,
                name,
                shortName,
                swissProtAccessions,
                tremblAccessions,
                entryAbstract);
    }

    public static @Nonnull InterProEntryBuilder from(@Nonnull InterProEntry instance) {
        return new InterProEntryBuilder()
                .interProAc(instance.getInterProAc())
                .checked(instance.isChecked())
                .name(instance.getName())
                .shortName(instance.getShortName())
                .swissProtAccessionsSet(instance.getSwissProtAccessions())
                .tremblAccessionsSet(instance.getTremblAccessions())
                .entryAbstract(instance.getEntryAbstract());
    }

    public @Nonnull InterProEntryBuilder interProAc(InterProAc interProAc) {
        this.interProAc = interProAc;
        return this;
    }

    public @Nonnull InterProEntryBuilder checked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public @Nonnull InterProEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull InterProEntryBuilder shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public @Nonnull InterProEntryBuilder entryAbstract(Abstract entryAbstract) {
        this.entryAbstract = entryAbstract;
        return this;
    }

    public @Nonnull InterProEntryBuilder swissProtAccessionsSet(
            Set<UniProtKBAccession> swissProtAccessions) {
        this.swissProtAccessions = Utils.modifiableSet(swissProtAccessions);
        return this;
    }

    public @Nonnull InterProEntryBuilder swissProtAccessionsAdd(
            UniProtKBAccession swissProtAccession) {
        Utils.addOrIgnoreNull(swissProtAccession, this.swissProtAccessions);
        return this;
    }

    public @Nonnull InterProEntryBuilder tremblAccessionsSet(
            Set<UniProtKBAccession> tremblAccessions) {
        this.tremblAccessions = Utils.modifiableSet(tremblAccessions);
        return this;
    }

    public @Nonnull InterProEntryBuilder tremblAccessionsAdd(UniProtKBAccession tremblAccession) {
        Utils.addOrIgnoreNull(tremblAccession, this.tremblAccessions);
        return this;
    }
}
