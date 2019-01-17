package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.impl.PatentImpl;

public final class PatentBuilder extends AbstractCitationBuilder<PatentBuilder, Patent> {
    private String patentNumber;

    public Patent build() {
        return new PatentImpl(this);
    }

    @Override
    public PatentBuilder from(Patent instance) {
        PatentBuilder builder = new PatentBuilder();
        init(builder, instance);
        return builder.patentNumber(instance.getPatentNumber());
    }

    public PatentBuilder patentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
        return this;
    }

    public String getPatentNumber() {
        return patentNumber;
    }

    @Override
    protected PatentBuilder getThis() {
        return this;
    }
}
