package org.uniprot.core.publication.impl;

import java.util.Objects;

import org.uniprot.core.publication.MappedSource;

/**
 * Created 03/12/2020
 *
 * @author Edd
 */
public class MappedSourceImpl implements MappedSource {
    private static final long serialVersionUID = 7921806426134087556L;

    private final String name;
    private final String id;

    public MappedSourceImpl() {
        this(null, null);
    }

    public MappedSourceImpl(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedSourceImpl that = (MappedSourceImpl) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
