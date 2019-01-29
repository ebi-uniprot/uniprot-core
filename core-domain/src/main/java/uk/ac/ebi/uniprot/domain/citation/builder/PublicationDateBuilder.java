package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.builder.AbstractValueBuilder;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.impl.PublicationDateImpl;

/**
 * Created 29/01/19
 *
 * @author Edd
 */
public class PublicationDateBuilder extends AbstractValueBuilder<PublicationDateBuilder, PublicationDate> {
    public PublicationDateBuilder(String value) {
        super(value);
    }

    @Override
    public PublicationDate build() {
        return new PublicationDateImpl(value);
    }

    @Override
    protected PublicationDateBuilder getThis() {
        return this;
    }
}
