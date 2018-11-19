package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.impl.FreeTextImpl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NoteImpl extends FreeTextImpl implements Note {
	@JsonCreator
    public NoteImpl(@JsonProperty("texts") List<EvidencedValue> texts) {
        super(texts);   
    }
	@JsonIgnore
	@Override
	public boolean isValid() {
		return !getTexts().isEmpty();
	}

}
