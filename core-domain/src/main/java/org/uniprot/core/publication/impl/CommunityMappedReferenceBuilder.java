package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.CommunityAnnotation;
import org.uniprot.core.publication.CommunityMappedReference;

import javax.annotation.Nonnull;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class CommunityMappedReferenceBuilder
        extends AbstractMappedReferenceBuilder<
                        CommunityMappedReferenceBuilder, CommunityMappedReference> {

    private CommunityAnnotation annotation;

    public CommunityMappedReferenceBuilder annotation(CommunityAnnotation annotation) {
        this.annotation = annotation;
        return this;
    }

    @Nonnull
    @Override
    public CommunityMappedReference build() {
        return new CommunityMappedReferenceImpl(
                source, sourceId, pubMedId, uniProtKBAccession, sourceCategories, annotation);
    }

    @Nonnull
    @Override
    protected CommunityMappedReferenceBuilder getThis() {
        return this;
    }

    public static CommunityMappedReferenceBuilder from(
            @Nonnull CommunityMappedReference instance) {
        CommunityMappedReferenceBuilder builder = new CommunityMappedReferenceBuilder();
        return AbstractMappedReferenceBuilder.from(builder, instance)
                .annotation(instance.getCommunityAnnotation());
    }
}
