package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.Objects;
import java.util.Set;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class ComputationallyMappedReferenceImpl extends AbstractMappedReference
        implements ComputationallyMappedReference {
    private final String annotation;

    public ComputationallyMappedReferenceImpl() {
        this(null, null, null, null, null);
    }

    public ComputationallyMappedReferenceImpl(
            Set<MappedSource> sources,
            String pubMedId,
            UniProtKBAccession uniProtKBAccession,
            Set<String> sourceCategories,
            String annotation) {
        super(sources, pubMedId, uniProtKBAccession, sourceCategories);
        this.annotation = annotation;
    }

    @Override
    public String getAnnotation() {
        return annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComputationallyMappedReferenceImpl that = (ComputationallyMappedReferenceImpl) o;
        return Objects.equals(annotation, that.annotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation);
    }
}
