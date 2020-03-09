package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public class NoteImpl extends FreeTextImpl implements Note {
    private static final long serialVersionUID = -5402129813413280620L;

    // no arg constructor for JSON deserialization
    NoteImpl() {
        super(Collections.emptyList());
    }

    NoteImpl(List<EvidencedValue> texts) {
        super(texts);
    }

    @Override
    public boolean isValid() {
        return !getTexts().isEmpty();
    }
}
