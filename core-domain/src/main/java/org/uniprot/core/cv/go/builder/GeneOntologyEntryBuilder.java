package org.uniprot.core.cv.go.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableSet;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoAspect;

public class GeneOntologyEntryBuilder implements Builder<GeneOntologyEntry> {
    private String id;
    private String name;
    private GoAspect aspect;
    private Set<GeneOntologyEntry> ancestors = new HashSet<>();

    public @Nonnull GeneOntologyEntryBuilder id(String goId) {
        this.id = goId;
        return this;
    }

    public @Nonnull GeneOntologyEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull GeneOntologyEntryBuilder aspect(GoAspect aspect) {
        this.aspect = aspect;
        return this;
    }

    public @Nonnull GeneOntologyEntryBuilder ancestorsSet(Set<GeneOntologyEntry> ancestors) {
        this.ancestors = modifiableSet(ancestors);
        return this;
    }

    public @Nonnull GeneOntologyEntryBuilder ancestorsAdd(GeneOntologyEntry ancestor) {
        addOrIgnoreNull(ancestor, this.ancestors);
        return this;
    }

    public @Nonnull GeneOntologyEntry build() {
        return new GeneOntologyEntryImpl(id, name, aspect, ancestors);
    }

    public static @Nonnull GeneOntologyEntryBuilder from(@Nonnull GeneOntologyEntry instance) {
        return new GeneOntologyEntryBuilder()
                .id(instance.getId())
                .name(instance.getName())
                .aspect(instance.getAspect())
                .ancestorsSet(instance.getAncestors());
    }
}
