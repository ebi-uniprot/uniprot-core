package org.uniprot.core.unirule.impl;

import org.uniprot.core.unirule.RuleStatus;

import java.util.concurrent.ThreadLocalRandom;

public class RuleStatusTest {

    public static RuleStatus createObject() {
        int index = ThreadLocalRandom.current().nextInt(0, RuleStatus.values().length);
        return RuleStatus.values()[index];
    }
}
