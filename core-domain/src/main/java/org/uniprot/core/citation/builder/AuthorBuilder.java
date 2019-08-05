package org.uniprot.core.citation.builder;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.impl.AuthorImpl;

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
