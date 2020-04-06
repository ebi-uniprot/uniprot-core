package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.GeneLocation;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

import java.io.Serializable;

public interface Annotation extends Serializable {
    Comment getComment();
    Keyword getKeyword();
    GeneLocation getGene();
    UniProtKBCrossReference getDBReference();
    ProteinType getProteinType();
}
