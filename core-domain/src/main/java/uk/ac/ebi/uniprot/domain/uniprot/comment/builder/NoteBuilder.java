package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.NoteImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import static org.uniprot.core.common.Utils.nonNullList;

import java.util.List;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class NoteBuilder extends AbstractFreeTextBuilder<NoteBuilder, Note> {
    public NoteBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = nonNullList(evidencedValues);
    }

    @Override
    protected NoteBuilder getThis() {
        return this;
    }

    @Override
    protected Note createConcreteInstance() {
        return new NoteImpl(evidencedValues);
    }
}
