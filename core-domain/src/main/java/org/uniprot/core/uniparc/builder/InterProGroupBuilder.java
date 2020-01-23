package org.uniprot.core.uniparc.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.impl.InterProGroupImpl;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class InterProGroupBuilder implements Builder<InterProGroup> {
    private String id;
    private String name;

    @Override
    public @Nonnull InterProGroup build() {
        return new InterProGroupImpl(id, name);
    }

    public @Nonnull InterProGroupBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull InterProGroupBuilder name(String name) {
        this.name = name;
        return this;
    }

    public static @Nonnull InterProGroupBuilder from(@Nonnull InterProGroup instance) {
        return new InterProGroupBuilder()
        .id(instance.getId())
        .name(instance.getName());
    }
}
