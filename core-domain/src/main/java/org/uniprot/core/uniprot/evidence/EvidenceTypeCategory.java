package org.uniprot.core.uniprot.evidence;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author jluo
 * @date: 29 Aug 2019
 */
public enum EvidenceTypeCategory implements EnumDisplay<EvidenceCode> {
    I,
    C,
    A;

    @Override
    public @Nonnull String toDisplayName() {
        return name();
    }
}
