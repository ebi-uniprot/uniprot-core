package org.uniprot.core.unirule.impl;

import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.SamTriggerType;

public class SamTriggerImpl implements SamTrigger {

    private static final long serialVersionUID = -4274854850320808093L;
    private SamTriggerType samTriggerType;
    private Range expectedHits;

    public SamTriggerImpl(SamTriggerType samTriggerType, Range expectedHits) {
        this.samTriggerType = samTriggerType;
        this.expectedHits = expectedHits;
    }

    @Override
    public SamTriggerType getSamTriggerType() {
        return samTriggerType;
    }

    @Override
    public Range getExpectedHits() {
        return expectedHits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SamTriggerImpl that = (SamTriggerImpl) o;
        return samTriggerType == that.samTriggerType
                && Objects.equals(expectedHits, that.expectedHits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(samTriggerType, expectedHits);
    }
}
