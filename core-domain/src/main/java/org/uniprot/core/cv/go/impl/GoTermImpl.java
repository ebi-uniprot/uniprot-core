package org.uniprot.core.cv.go.impl;

import java.util.Objects;

import org.uniprot.core.cv.go.GoTerm;

public class GoTermImpl implements GoTerm {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8033194717925160409L;
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
