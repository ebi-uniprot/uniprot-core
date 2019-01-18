package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Thesis;
import uk.ac.ebi.uniprot.domain.citation.impl.ThesisImpl;

public final class ThesisBuilder extends AbstractCitationBuilder<ThesisBuilder, Thesis> {
    private String institute;
    private String address;

    public Thesis build() {
        return new ThesisImpl(this);
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

    public String getInstitute() {
        return institute;
    }

    public String getAddress() {
        return address;
    }

    @Override
    protected ThesisBuilder getThis() {
        return this;
    }
}
