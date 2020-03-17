package org.uniprot.core.flatfile.writer;

import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;

public interface FFLineBuilderFactory {
    FFLineBuilder<List<UniProtKBAccession>> createAcLineBuilder();

    FFLineBuilder<List<Comment>> createCcLineBuilder();

    FFLineBuilder<ProteinDescription> createDeLineBuilder();
}
