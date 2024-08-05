package org.uniprot.core.json.parser.uniparc;

import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.util.Pair;

import java.util.List;
import java.util.Objects;

public class UniParcCrossRefPair implements Pair<String, List<UniParcCrossReference>>{
    private String key;
    private List<UniParcCrossReference> value;
    public UniParcCrossRefPair(){
        this(null, null);
    }

    public UniParcCrossRefPair(String key, List<UniParcCrossReference> value) {
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
        UniParcCrossRefPair that = (UniParcCrossRefPair) o;
        return Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }
}
