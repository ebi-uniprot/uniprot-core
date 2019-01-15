package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;
import uk.ac.ebi.uniprot.domain.uniprot.impl.FreeTextImpl;

import java.util.Collections;
import java.util.List;

public class NoteImpl extends FreeTextImpl implements Note {

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
