package org.uniprot.core.unirule;

import java.io.Serializable;

import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

/** @author sahmad */
public interface Annotation extends Serializable {
    Comment getComment();

    Keyword getKeyword();

    Gene getGene();

    UniProtKBCrossReference getDBReference();

    ProteinDescription getProteinDescription();
}
