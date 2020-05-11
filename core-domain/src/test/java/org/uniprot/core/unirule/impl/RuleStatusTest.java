package org.uniprot.core.unirule.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.uniprot.core.unirule.RuleStatus;

public class RuleStatusTest {

    public static RuleStatus createObject() {
        int index = ThreadLocalRandom.current().nextInt(0, RuleStatus.values().length);
        return RuleStatus.values()[index];
    }
}
