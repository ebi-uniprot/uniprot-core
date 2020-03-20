package org.uniprot.core.uniprotkb.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtKBAccessionBuilder extends AbstractValueBuilder<UniProtKBAccession> {
    public UniProtKBAccessionBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniProtKBAccession build() {
        return new UniProtKBAccessionImpl(value);
    }

    public static @Nonnull UniProtKBAccessionBuilder from(@Nonnull UniProtKBAccession instance) {
        return new UniProtKBAccessionBuilder(instance.getValue());
    }
}
