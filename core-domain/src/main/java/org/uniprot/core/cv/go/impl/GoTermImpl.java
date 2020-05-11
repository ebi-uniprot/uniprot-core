package org.uniprot.core.cv.go.impl;

import org.uniprot.core.cv.go.GoTerm;

import java.util.Objects;

public class GoTermImpl implements GoTerm {
    private final String goId;
    private final String name;

    GoTermImpl() {
        this(null, null);
    }

    GoTermImpl(String goId, String name) {
        this.goId = goId;
        this.name = name;
    }

    public String getId() {
        return goId;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoTermImpl goTerm = (GoTermImpl) o;
        return Objects.equals(goId, goTerm.goId);
    }

    public int hashCode() {
        return Objects.hash(goId);
    }
}
