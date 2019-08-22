package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullList;

import java.util.List;

import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.NoteImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

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