package org.uniprot.core.uniparc.impl;

import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.util.Pair;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class UniParcCrossReferencePair implements Pair<String, List<UniParcCrossReference>> {
    @Serial
    private static final long serialVersionUID = 7906365717659009263L;
    private String key;
    private List<UniParcCrossReference> value;

    UniParcCrossReferencePair() {
    }

    public UniParcCrossReferencePair(String key, List<UniParcCrossReference> value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public List<UniParcCrossReference> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniParcCrossReferencePair that = (UniParcCrossReferencePair) o;
        return Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }
}
