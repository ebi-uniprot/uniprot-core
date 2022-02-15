package org.uniprot.core.uniprotkb.feature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.feature.LigandPart;

/**
 * @author jluo
 * @date: 9 Feb 2022
 */
public class LigandPartBuilder implements Builder<LigandPart> {

    private String name;
    private String id;
    private String label;
    private String note;

    @Override
    public LigandPart build() {
        return new LigandPartImpl(name, id, label, note);
    }

    public static @Nonnull LigandPartBuilder from(@Nonnull LigandPart instance) {
        return new LigandPartBuilder()
                .name(instance.getName())
                .id(instance.getId())
                .label(instance.getLabel())
                .note(instance.getNote());
    }

    public @Nonnull LigandPartBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull LigandPartBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull LigandPartBuilder label(String label) {
        this.label = label;
        return this;
    }

    public @Nonnull LigandPartBuilder note(String note) {
        this.note = note;
        return this;
    }
}
