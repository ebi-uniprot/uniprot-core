package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.io.Serializable;
import java.util.List;

public interface UniRuleName extends Serializable {
    EvidencedValue getFullName();
    List<EvidencedValue> getShortNames();
    List<EvidencedValue> getECNumbers();
}
