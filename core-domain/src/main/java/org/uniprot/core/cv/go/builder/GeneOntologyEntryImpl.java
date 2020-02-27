package org.uniprot.core.cv.go.builder;

import static org.uniprot.core.util.Utils.unmodifiableSet;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoAspect;

public class GeneOntologyEntryImpl implements GeneOntologyEntry {
    private static final long serialVersionUID = -5760727679729608097L;
    private final String goId;
    private final String goTerm;
    private final GoAspect aspect;
    private final Set<GeneOntologyEntry> ancestors;

    GeneOntologyEntryImpl() {
        this(null, null, null, Collections.emptySet());
    }

    GeneOntologyEntryImpl(
            String goId, String goTerm, GoAspect aspect, Set<GeneOntologyEntry> ancestors) {
        this.goId = goId;
        this.goTerm = goTerm;
        this.aspect = aspect;
        this.ancestors = unmodifiableSet(ancestors);
    }

    public String getId() {
        return goId;
    }

    public String getName() {
        return goTerm;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneOntologyEntryImpl that = (GeneOntologyEntryImpl) o;
        return Objects.equals(goId, that.goId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goId);
    }
}
