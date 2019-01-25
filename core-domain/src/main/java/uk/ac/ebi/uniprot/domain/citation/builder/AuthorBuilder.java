package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.builder.AbstractValueBuilder;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class AuthorBuilder extends AbstractValueBuilder<AuthorBuilder, Author> {
    public AuthorBuilder(String value) {
        super(value);
    }

    @Override
    public Author build() {
        return new AuthorImpl(value);
    }

    @Override
    protected AuthorBuilder getThis() {
        return this;
    }
}
