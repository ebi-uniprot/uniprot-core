package org.uniprot.core.citation.impl;

import org.uniprot.core.citation.Thesis;

import javax.annotation.Nonnull;

public final class ThesisBuilder extends AbstractCitationBuilder<ThesisBuilder, Thesis> {
    private String institute;
    private String address;

    public @Nonnull Thesis build() {
        return new ThesisImpl(
                authoringGroups,
                authors,
                citationCrossReferences,
                title,
                publicationDate,
                institute,
                address);
    }

    public static @Nonnull ThesisBuilder from(Thesis instance) {
        ThesisBuilder builder = new ThesisBuilder();
        init(builder, instance);
        return builder.institute(instance.getInstitute()).address(instance.getAddress());
    }

    public @Nonnull ThesisBuilder institute(String institute) {
        this.institute = institute;
        return this;
    }

    public @Nonnull ThesisBuilder address(String address) {
        this.address = address;
        return this;
    }

    @Override
    protected @Nonnull ThesisBuilder getThis() {
        return this;
    }
}
