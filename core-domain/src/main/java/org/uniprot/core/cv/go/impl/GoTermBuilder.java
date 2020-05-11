package org.uniprot.core.cv.go.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.go.GoTerm;

public class GoTermBuilder implements Builder<GoTerm> {
    protected String id;
    protected String name;

    public @Nonnull GoTermBuilder id(String goId) {
        this.id = goId;
        return this;
    }

    public @Nonnull GoTermBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull GoTerm build() {
        return new GoTermImpl(id, name);
    }

    public static @Nonnull GoTermBuilder from(@Nonnull GoTerm instance) {
        return new GoTermBuilder().id(instance.getId()).name(instance.getName());
    }
}
