package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;
import java.util.Optional;

public interface AlternativeProductsComment extends Comment {
	public List<APEvent> getEvents();
	public List<APIsoform> getIsoforms();
	public Optional<CommentNote> getNote();
}
