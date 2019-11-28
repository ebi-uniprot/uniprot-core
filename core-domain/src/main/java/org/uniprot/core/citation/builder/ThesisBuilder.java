package org.uniprot.core.citation.builder;

import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.impl.ThesisImpl;

import javax.annotation.Nonnull;

public final class ThesisBuilder extends AbstractCitationBuilder<ThesisBuilder, Thesis> {
    private String institute;
    private String address;

    public @Nonnull Thesis build() {
        return new ThesisImpl(
                authoringGroups, authors, xrefs, title, publicationDate, institute, address);
    }

    @Override
    public @Nonnull ThesisBuilder from(Thesis instance) {
        init(instance);
        return this.institute(instance.getInstitute()).address(instance.getAddress());
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
    protected ThesisBuilder getThis() {
        return this;
    }
}
