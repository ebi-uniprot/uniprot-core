package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.List;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class ComputationallyMappedReferenceImpl extends AbstractMappedReference
        implements ComputationallyMappedReference {
    private String annotation;

    public ComputationallyMappedReferenceImpl(
            String source,
            String sourceId,
            String pubMedId,
            UniProtKBAccession uniProtKBAccession,
            List<String> sourceCategories,
            String annotation) {
        super(source, sourceId, pubMedId, uniProtKBAccession, sourceCategories);
        this.annotation = annotation;
    }

    @Override
    public String getAnnotation() {
        return annotation;
    }
}
