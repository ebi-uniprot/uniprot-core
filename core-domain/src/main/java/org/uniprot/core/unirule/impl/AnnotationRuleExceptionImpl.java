package org.uniprot.core.unirule.impl;

import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.Annotation;

public class AnnotationRuleExceptionImpl extends AbstractRuleException<Annotation> {

    private static final long serialVersionUID = 5912068124839425973L;

    AnnotationRuleExceptionImpl() {
        super();
    }

    AnnotationRuleExceptionImpl(
            String note,
            String category,
            Annotation annotation,
            List<UniProtKBAccession> accessions) {
        super(note, category, annotation, accessions);
    }
}
