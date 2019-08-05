package org.uniprot.core.citation.builder;

import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.impl.ThesisImpl;

public final class ThesisBuilder extends AbstractCitationBuilder<ThesisBuilder, Thesis> {
    private String institute;
    private String address;

    public Thesis build() {
        return new ThesisImpl(authoringGroups, authors, xrefs, title, publicationDate, institute, address);
    }

    @Override
    public ThesisBuilder from(Thesis instance) {
        init(instance);
        return this
                .institute(instance.getInstitute())
                .address(instance.getAddress());
    }

    public ThesisBuilder institute(String institute) {
        this.institute = institute;
        return this;
    }

    public ThesisBuilder address(String address) {
        this.address = address;
        return this;
    }

    @Override
    protected ThesisBuilder getThis() {
        return this;
    }
}
