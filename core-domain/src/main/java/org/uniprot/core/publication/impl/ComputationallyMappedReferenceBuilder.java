package org.uniprot.core.publication.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.publication.ComputationallyMappedReference;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class ComputationallyMappedReferenceBuilder
        extends AbstractMappedReferenceBuilder<
                ComputationallyMappedReferenceBuilder, ComputationallyMappedReference> {
    private String annotation;

    public ComputationallyMappedReferenceBuilder annotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    @Nonnull
    @Override
    public ComputationallyMappedReference build() {
        return new ComputationallyMappedReferenceImpl(
                source, citationId, uniProtKBAccession, sourceCategories, annotation);
    }

    @Nonnull
    @Override
    protected ComputationallyMappedReferenceBuilder getThis() {
        return this;
    }

    public static ComputationallyMappedReferenceBuilder from(
            @Nonnull ComputationallyMappedReference instance) {
        ComputationallyMappedReferenceBuilder builder = new ComputationallyMappedReferenceBuilder();
        return AbstractMappedReferenceBuilder.from(builder, instance)
                .annotation(instance.getAnnotation());
    }
}
