package org.uniprot.core.flatfile.writer;

import java.util.List;

import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.description.ProteinDescription;

public interface FFLineBuilderFactory {
	FFLineBuilder< List<UniProtAccession> > createAcLineBuilder();
	FFLineBuilder<List<Comment> > createCcLineBuilder();
	FFLineBuilder<ProteinDescription> createDeLineBuilder();
	
}
