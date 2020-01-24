package org.uniprot.core.citation.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.impl.AuthorImpl;

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
