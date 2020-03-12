package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.comment.FreeText;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.util.Utils;

public class FreeTextImpl implements FreeText {
    private static final long serialVersionUID = -4309930597833279389L;
    private List<EvidencedValue> texts;

    // no arg constructor for JSON deserialization
    FreeTextImpl() {
        this.texts = Collections.emptyList();
    }

    FreeTextImpl(List<EvidencedValue> texts) {
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
        return Utils.notNullNotEmpty(this.texts);
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
