package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface TextOnlyComment extends Comment {
    List<CommentText> getTexts();
	
}
