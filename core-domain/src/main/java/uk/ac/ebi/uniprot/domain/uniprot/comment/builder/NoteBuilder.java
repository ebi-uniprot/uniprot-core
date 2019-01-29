package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.NoteImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

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
