package org.uniprot.core.citation.builder;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.citation.impl.PublicationDateImpl;

import javax.annotation.Nonnull;

/**
 * Created 29/01/19
 *
 * @author Edd
 */
public class PublicationDateBuilder
        extends AbstractValueBuilder<PublicationDateBuilder, PublicationDate> {
    public PublicationDateBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull PublicationDate build() {
        return new PublicationDateImpl(value);
    }

    @Override
    protected PublicationDateBuilder getThis() {
        return this;
    }
}
