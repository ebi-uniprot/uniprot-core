package org.uniprot.core.citation.impl;

import org.uniprot.core.citation.Author;
import org.uniprot.core.impl.AbstractValueBuilder;

import javax.annotation.Nonnull;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class AuthorBuilder extends AbstractValueBuilder<Author> {
    public AuthorBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull Author build() {
        return new AuthorImpl(value);
    }

    public static @Nonnull AuthorBuilder from(@Nonnull Author instance) {
        return new AuthorBuilder(instance.getValue());
    }
}
