package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.impl.SourceLineImpl;

/**
 * @author lgonzales
 */
public class SourceLineBuilder implements Builder2<SourceLineBuilder, SourceLine> {
    private String value;

    public SourceLineBuilder value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public SourceLine build() {
        return new SourceLineImpl(value);
    }

    @Override
    public SourceLineBuilder from(SourceLine instance) {
        this.value(instance.getValue());
        return this;
    }
}