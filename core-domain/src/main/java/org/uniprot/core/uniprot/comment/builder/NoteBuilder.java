package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.NoteImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

import javax.annotation.Nonnull;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class NoteBuilder extends AbstractFreeTextBuilder<NoteBuilder, Note> {
    public NoteBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = modifiableList(evidencedValues);
    }

    @Override
    protected @Nonnull NoteBuilder getThis() {
        return this;
    }

    @Override
    protected @Nonnull Note createConcreteInstance() {
        return new NoteImpl(evidencedValues);
    }
}
