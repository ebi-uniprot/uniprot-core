package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniparc.ProteomeIdComponent;

import javax.annotation.Nonnull;

public class ProteomeIdComponentBuilder implements Builder<ProteomeIdComponent> {
    private String proteomeId;
    private String component;

    public @Nonnull ProteomeIdComponentBuilder proteomeId(String proteomeId) {
        this.proteomeId = proteomeId;
        return this;
    }

    public @Nonnull ProteomeIdComponentBuilder component(String component) {
        this.component = component;
        return this;
    }

    @Nonnull
    @Override
    public ProteomeIdComponent build() {
        return new ProteomeIdComponentImpl(proteomeId, component);
    }
}
