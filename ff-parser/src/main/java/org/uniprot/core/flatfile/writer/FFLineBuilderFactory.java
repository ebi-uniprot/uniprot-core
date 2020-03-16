package org.uniprot.core.flatfile.writer;

import java.util.List;

import org.uniprot.core.uniprotkb.UniProtkbAccession;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;

public interface FFLineBuilderFactory {
    FFLineBuilder<List<UniProtkbAccession>> createAcLineBuilder();

    FFLineBuilder<List<Comment>> createCcLineBuilder();

    FFLineBuilder<ProteinDescription> createDeLineBuilder();
}
