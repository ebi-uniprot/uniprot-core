package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeText;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class FreeTextImpl implements FreeText {
    private static final long serialVersionUID = -4309930597833279389L;
    private List<EvidencedValue> texts;

    private FreeTextImpl() {
        this.texts = Collections.emptyList();
    }

    public FreeTextImpl(List<EvidencedValue> texts) {
        if ((texts == null) || texts.isEmpty()) {
            this.texts = Collections.emptyList();
        } else {
            this.texts = Collections.unmodifiableList(texts);
        }
    }

    public List<EvidencedValue> getTexts() {
        return texts;
    }

    @Override
    public boolean hasTexts() {
        return Utils.notEmpty(this.texts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FreeTextImpl freeText = (FreeTextImpl) o;
        return Objects.equals(texts, freeText.texts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texts);
    }
}
