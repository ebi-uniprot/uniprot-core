package org.uniprot.core.publication.impl;

import static java.util.Collections.emptySet;

import java.util.Objects;
import java.util.Set;

import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class ComputationallyMappedReferenceImpl extends AbstractMappedReference
        implements ComputationallyMappedReference {
    private static final long serialVersionUID = -4834442720823846138L;

    private final String annotation;

    public ComputationallyMappedReferenceImpl() {
        this(null, null, null, emptySet(), null);
    }

    public ComputationallyMappedReferenceImpl(
            MappedSource source,
            String citationId,
            UniProtKBAccession uniProtKBAccession,
            Set<String> sourceCategories,
            String annotation) {
        super(source, citationId, uniProtKBAccession, sourceCategories);
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
