package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.List;
import java.util.Set;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class ComputationallyMappedReferenceImpl extends AbstractMappedReference
        implements ComputationallyMappedReference {
    private String annotation;

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
}
