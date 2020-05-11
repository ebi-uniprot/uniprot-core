package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class NoteBuilder extends AbstractFreeTextBuilder<Note> {
    public NoteBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = modifiableList(evidencedValues);
    }

    @Override
    protected @Nonnull Note createConcreteInstance() {
        return new NoteImpl(evidencedValues);
    }

    public static @Nonnull NoteBuilder from(@Nonnull Note instance) {
        return new NoteBuilder(instance.getTexts());
    }
}
