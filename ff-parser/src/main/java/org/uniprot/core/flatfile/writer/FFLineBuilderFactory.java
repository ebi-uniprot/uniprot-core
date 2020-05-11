package org.uniprot.core.flatfile.writer;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;

import java.util.List;

public interface FFLineBuilderFactory {
    FFLineBuilder<List<UniProtKBAccession>> createAcLineBuilder();

    FFLineBuilder<List<Comment>> createCcLineBuilder();

    FFLineBuilder<ProteinDescription> createDeLineBuilder();
}
