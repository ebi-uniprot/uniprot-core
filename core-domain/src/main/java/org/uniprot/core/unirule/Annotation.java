package org.uniprot.core.unirule;

import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

/** @author sahmad */
public interface Annotation extends RuleExceptionAnnotation {
    Comment getComment();

    Keyword getKeyword();

    Gene getGene();

    UniProtKBCrossReference getDbReference();

    ProteinDescription getProteinDescription();
}
