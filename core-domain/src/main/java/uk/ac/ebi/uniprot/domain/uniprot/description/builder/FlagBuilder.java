package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;

/**
 * Created 29/01/19
 *
 * @author Edd
 */
public class FlagBuilder implements Builder2<FlagBuilder, Flag> {
    private FlagType flagType;

    @Override
    public Flag build() {
        return new FlagImpl(flagType);
    }

    @Override
    public FlagBuilder from(Flag instance) {
        this.flagType = instance.getType();
        return this;
    }

    public FlagBuilder(FlagType flagType) {
        this.flagType = flagType;
    }
}
