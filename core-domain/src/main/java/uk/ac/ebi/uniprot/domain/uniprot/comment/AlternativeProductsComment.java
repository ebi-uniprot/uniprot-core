package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;
import java.util.Optional;

public interface AlternativeProductsComment extends Comment {
	public List<APEvent> getEvents();
	public List<APIsoform> getIsoforms();
	public Optional<Note> getNote();
}
