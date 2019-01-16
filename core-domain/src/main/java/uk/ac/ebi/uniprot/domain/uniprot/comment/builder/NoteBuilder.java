package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

import java.util.List;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class NoteBuilder extends AbstractFreeTextBuilder<NoteBuilder, Note> {
    public NoteBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = evidencedValues;
    }

    @Override
    protected NoteBuilder createBuilderInstance(List<EvidencedValue> evidencedValues) {
        return new NoteBuilder(evidencedValues);
    }

    @Override
    protected Note createConcreteInstance() {
        return null;
    }
}
