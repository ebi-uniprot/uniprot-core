package org.uniprot.core.citation.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.citation.impl.PublicationDateImpl;

/**
 * Created 29/01/19
 *
 * @author Edd
 */
public class PublicationDateBuilder extends AbstractValueBuilder<PublicationDate> {
    public PublicationDateBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull PublicationDate build() {
        return new PublicationDateImpl(value);
    }

    public static @Nonnull PublicationDateBuilder from(@Nonnull PublicationDate instance) {
        return new PublicationDateBuilder(instance.getValue());
    }
}
