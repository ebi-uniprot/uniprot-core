package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.MappedSource;

import java.util.Objects;
import java.util.Set;

/**
 * Created 03/12/2020
 *
 * @author Edd
 */
public class MappedSourceImpl implements MappedSource {
    private final String source;
    private final Set<String> sourceIds;

    public MappedSourceImpl() {
        this(null, null);
    }

    public MappedSourceImpl(String source, Set<String> sourceIds) {
        this.source = source;
        this.sourceIds = sourceIds;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public Set<String> getSourceIds() {
        return sourceIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedSourceImpl that = (MappedSourceImpl) o;
        return Objects.equals(source, that.source) && Objects.equals(sourceIds, that.sourceIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, sourceIds);
    }
}
