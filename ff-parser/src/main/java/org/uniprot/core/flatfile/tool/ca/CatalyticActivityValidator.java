package org.uniprot.core.flatfile.tool.ca;

import java.util.Optional;

import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;



public interface CatalyticActivityValidator {
	Optional<CatalyticActivityComment> validateAndConvert(CatalyticActivityComment comment );
}
