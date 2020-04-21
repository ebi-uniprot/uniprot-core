package org.uniprot.core.unirule.impl;

import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.PositionalFeature;

public class PositionalRuleExceptionImpl extends AbstractRuleException<PositionalFeature> {

    private static final long serialVersionUID = -1465183148205445458L;

    PositionalRuleExceptionImpl() {
        super();
    }

    PositionalRuleExceptionImpl(
            String note,
            String category,
            PositionalFeature positionalFeature,
            List<UniProtKBAccession> accessions) {
        super(note, category, positionalFeature, accessions);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}