package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.MappedSource;

import java.util.Objects;

/**
 * Created 03/12/2020
 *
 * @author Edd
 */
public class MappedSourceImpl implements MappedSource {
    private static final long serialVersionUID = 7921806426134087556L;

    private final String source;
    private final String sourceId;

    public MappedSourceImpl() {
        this(null, null);
    }

    public MappedSourceImpl(String source, String sourceId) {
        this.source = source;
        this.sourceId = sourceId;
    }

    @Override
    public String getName() {
        return source;
    }

    @Override
    public String getId() {
        return sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedSourceImpl that = (MappedSourceImpl) o;
        return Objects.equals(source, that.source) && Objects.equals(sourceId, that.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, sourceId);
    }
}
