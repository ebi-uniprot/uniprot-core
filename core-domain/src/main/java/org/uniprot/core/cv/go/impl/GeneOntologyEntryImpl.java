package org.uniprot.core.cv.go.impl;

import static org.uniprot.core.util.Utils.unmodifiableSet;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoAspect;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class GeneOntologyEntryImpl extends GoTermImpl implements GeneOntologyEntry {
    private static final long serialVersionUID = -5760727679729608097L;
    private final GoAspect aspect;
    private final Set<GeneOntologyEntry> ancestors;

    GeneOntologyEntryImpl() {
        this(null, null, null, Collections.emptySet());
    }

    GeneOntologyEntryImpl(
            String goId, String goTerm, GoAspect aspect, Set<GeneOntologyEntry> ancestors) {
        super(goId, goTerm);
        this.aspect = aspect;
        this.ancestors = unmodifiableSet(ancestors);
    }

    public GoAspect getAspect() {
        return aspect;
    }

    public Set<GeneOntologyEntry> getAncestors() {
        return ancestors;
    }

    public int compareTo(GeneOntologyEntry g1) {
        return g1.getId().compareTo(this.getId());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneOntologyEntryImpl that = (GeneOntologyEntryImpl) o;
        return Objects.equals(getId(), that.getId());
    }

    public int hashCode() {
        return Objects.hash(getId());
    }
}
