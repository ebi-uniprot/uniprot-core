package org.uniprot.core.unirule;

import java.io.Serializable;

public interface ProteinType extends Serializable {
    UniRuleNameGroup getUniRuleNameGroup();
    UniRuleNameGroup getDomain();
    UniRuleNameGroup getComponent();
}
