package org.uniprot.core.unirule.impl;

import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

public class PositionalRuleExceptionImpl<PositionalFeature>
        extends AbstractRuleException<PositionalFeature> {

    private static final long serialVersionUID = -1465183148205445458L;

    public PositionalRuleExceptionImpl(
            String note,
            String category,
            PositionalFeature positionalFeature,
            List<UniProtKBAccession> accessions) {
        super(note, category, positionalFeature, accessions);
    }
}
