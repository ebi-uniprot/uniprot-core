package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.RuleException;

public abstract class AbstractRuleExceptionBuilder<
                S extends AbstractRuleExceptionBuilder, T extends RuleException<R>, R>
        implements Builder<T> {

    protected String note;
    protected String category;
    protected R annotation;
    protected List<UniProtKBAccession> accessions = new ArrayList<>();

    public AbstractRuleExceptionBuilder(String category) {
        this.category = category;
    }

    public @Nonnull S note(String note) {
        this.note = note;
        return getThis();
    }

    public @Nonnull S category(String category) {
        this.category = category;
        return getThis();
    }

    public @Nonnull S annotation(R annotation) {
        this.annotation = annotation;
        return getThis();
    }

    public @Nonnull S accessionsAdd(UniProtKBAccession accession) {
        addOrIgnoreNull(accession, this.accessions);
        return getThis();
    }

    public @Nonnull S accessionsSet(List<UniProtKBAccession> accessions) {
        this.accessions = modifiableList(accessions);
        return getThis();
    }

    protected abstract @Nonnull S getThis();
}
