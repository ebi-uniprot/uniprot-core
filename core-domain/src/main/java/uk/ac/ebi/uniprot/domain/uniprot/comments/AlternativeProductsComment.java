package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface AlternativeProductsComment extends Comment {
	public List<AlternativeProductsEvent> getEvents();
	public List<AlternativeProductsIsoform> getIsoforms();
	public AlternativeProductsCommentComment getComment();
}
