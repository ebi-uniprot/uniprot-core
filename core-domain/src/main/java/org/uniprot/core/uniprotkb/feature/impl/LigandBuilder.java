package org.uniprot.core.uniprotkb.feature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.feature.Ligand;

public class LigandBuilder implements Builder<Ligand> {
    private String name;
    private String id;
    private String label;
    private String note;

    @Override
    public Ligand build() {
        return new LigandImpl(name, id, label, note);
    }

    public static @Nonnull LigandBuilder from(@Nonnull Ligand instance) {
        return new LigandBuilder()
                .name(instance.getName())
                .id(instance.getId())
                .label(instance.getLabel())
                .note(instance.getNote());
    }

    public LigandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public LigandBuilder id(String id) {
        this.id = id;
        return this;
    }

    public LigandBuilder label(String label) {
        this.label = label;
        return this;
    }

    public LigandBuilder note(String note) {
        this.note = note;
        return this;
    }
}
