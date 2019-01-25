package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.impl.InternalLineImpl;

/**
 * @author lgonzales
 */
public class InternalLineBuilder  implements Builder2<InternalLineBuilder, InternalLine> {

    private InternalLineType type;
    private String value;

    public InternalLineBuilder type(InternalLineType type) {
        this.type = type;
        return this;
    }

    public InternalLineBuilder value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public InternalLine build() {
        return new InternalLineImpl(type, value);
    }

    @Override
    public InternalLineBuilder from(InternalLine instance) {
        this.type(instance.getType());
        this.value(instance.getValue());
        return this;
    }

}