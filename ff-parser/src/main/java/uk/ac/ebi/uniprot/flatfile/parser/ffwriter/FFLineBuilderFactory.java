package uk.ac.ebi.uniprot.flatfile.parser.ffwriter;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;

import java.util.List;

public interface FFLineBuilderFactory {
	FFLineBuilder< List<UniProtAccession> > createAcLineBuilder();
	FFLineBuilder<List<Comment> > createCcLineBuilder();
	FFLineBuilder<ProteinDescription> createDeLineBuilder();
	
}
