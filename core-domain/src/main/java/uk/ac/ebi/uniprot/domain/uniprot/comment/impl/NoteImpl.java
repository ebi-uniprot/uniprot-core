package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import java.util.Collections;
import java.util.List;

public class NoteImpl extends FreeTextImpl implements Note {
    private static final long serialVersionUID = -5402129813413280620L;

    private NoteImpl() {
        super(Collections.emptyList());
    }

    public NoteImpl(List<EvidencedValue> texts) {
        super(texts);
    }

    @Override
    public boolean isValid() {
        return !getTexts().isEmpty();
    }
}
