package org.uniprot.core.cv.keyword.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.keyword.KeywordGeneOntology;

public class KeywordGeneOntologyBuilder implements Builder<KeywordGeneOntology> {
    private String id;
    private String term;

    public @Nonnull KeywordGeneOntologyBuilder id(String goId) {
        this.id = goId;
        return this;
    }

    public @Nonnull KeywordGeneOntologyBuilder term(String goTerm) {
        this.term = goTerm;
        return this;
    }

    public @Nonnull KeywordGeneOntology build() {
        return new KeywordGeneOntologyImpl(id, term);
    }

    public static @Nonnull KeywordGeneOntologyBuilder from(@Nonnull KeywordGeneOntology instance) {
        return new KeywordGeneOntologyBuilder().id(instance.getGoId()).term(instance.getGoTerm());
    }
}
