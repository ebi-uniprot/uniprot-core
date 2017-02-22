package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface AlternativeProductsComment extends Comment {
	public List<String> getEvents();
	public List<AlternativeProductsIsoform> getIsoforms();
	public AlternativeProductsCommentComment getComment();
}
