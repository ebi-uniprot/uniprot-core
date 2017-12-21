package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface AlternativeProductsComment extends Comment {
	public List<APEvent> getEvents();
	public List<APIsoform> getIsoforms();
	public APNote getNote();
}
