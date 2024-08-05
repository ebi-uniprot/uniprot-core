package org.uniprot.core.json.parser.uniparc;

import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.util.Pair;

import java.util.List;

public class UniParcCrossRefPair implements Pair<String, List<UniParcCrossReference>>{

    private final String key;
    private final List<UniParcCrossReference> value;

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
}
