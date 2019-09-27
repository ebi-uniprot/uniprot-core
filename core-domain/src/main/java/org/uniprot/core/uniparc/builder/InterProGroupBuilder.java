package org.uniprot.core.uniparc.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.impl.InterProGroupImpl;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class InterProGroupBuilder implements Builder<InterProGroupBuilder, InterproGroup> {
    private String id;
    private String name;

    @Override
    public InterproGroup build() {
        return new InterProGroupImpl(id, name);
    }

    public InterProGroupBuilder id(String id) {
        this.id = id;
        return this;
    }

    public InterProGroupBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public InterProGroupBuilder from(InterproGroup instance) {
        this.id = instance.getId();
        this.name = instance.getName();
        return this;
    }
}
