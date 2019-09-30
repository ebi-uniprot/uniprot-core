package org.uniprot.core.uniref.impl;

import java.util.Objects;

import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.GoTermType;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class GoTermImpl implements GoTerm {
    private static final long serialVersionUID = -8965126511980015976L;

    private GoTermType type;
    private String id;

    protected GoTermImpl() {}

    public GoTermImpl(GoTermType type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public GoTermType getType() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GoTermImpl other = (GoTermImpl) obj;
        return Objects.equals(type, other.type) && Objects.equals(id, other.id);
    }
}
