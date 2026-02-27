package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniparc.Proteome;

import javax.annotation.Nonnull;

public class ProteomeBuilder  implements Builder<Proteome> {

    private String id;
    private String component;

    @Nonnull
    @Override
    public Proteome build() {
        return new ProteomeImpl(id, component);
    }

    public ProteomeBuilder id(String id) {
        this.id = id;
        return this;
    }


    public ProteomeBuilder component(String component) {
        this.component = component;
        return this;
    }

    public static @Nonnull ProteomeBuilder from(@Nonnull Proteome instance) {
        return new ProteomeBuilder()
                .id(instance.getId())
                .component(instance.getComponent());
    }
}