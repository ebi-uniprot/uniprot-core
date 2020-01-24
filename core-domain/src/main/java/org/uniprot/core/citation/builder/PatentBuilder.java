package org.uniprot.core.citation.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.citation.Patent;
import org.uniprot.core.citation.impl.PatentImpl;

public final class PatentBuilder extends AbstractCitationBuilder<PatentBuilder, Patent> {
    private String patentNumber;

    public @Nonnull Patent build() {
        return new PatentImpl(
                authoringGroups, authors, xrefs, title, publicationDate, patentNumber);
    }

    public static @Nonnull PatentBuilder from(@Nonnull Patent instance) {
        PatentBuilder builder = new PatentBuilder();
        init(builder, instance);
        return builder.patentNumber(instance.getPatentNumber());
    }

    public @Nonnull PatentBuilder patentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
        return this;
    }

    @Override
    protected @Nonnull PatentBuilder getThis() {
        return this;
    }
}
