package org.uniprot.core.proteome.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.GenomeAnnotation;

import javax.annotation.Nonnull;

/**
 * @author lgonzales
 * @since 12/11/2020
 */
public class GenomeAnnotationBuilder implements Builder<GenomeAnnotation> {

    private String source;

    private String url;

    public @Nonnull GenomeAnnotationBuilder source(String source) {
        this.source = source;
        return this;
    }

    public @Nonnull GenomeAnnotationBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Nonnull
    @Override
    public GenomeAnnotation build() {
        return new GenomeAnnotationImpl(source, url);
    }

    public static @Nonnull GenomeAnnotationBuilder from(@Nonnull GenomeAnnotation instance) {
        return new GenomeAnnotationBuilder()
                .source(instance.getSource())
                .url(instance.getUrl());
    }
}
