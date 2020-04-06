package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.io.Serializable;
import java.util.List;

public interface UniRuleNameGroup extends Serializable {
    UniRuleName getRecommendedName();
    List<UniRuleName> getAlternativeNames();
    EvidencedValue getAllergenName();
    List<EvidencedValue> getCDAntigenNames();
    List<EvidencedValue> getInnNames();
    Flag getFlag();

    interface Flag {
        List<String> getValues();
    }
}
