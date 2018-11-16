package uk.ac.ebi.uniprot.parser.ffwriter;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;

public interface FFLineBuilderFactory {
	FFLineBuilder< List<UniProtAccession> > createAcLineBuilder();
	FFLineBuilder<List<Comment> > createCcLineBuilder();
	FFLineBuilder<ProteinDescription> createDeLineBuilder();
	
}
