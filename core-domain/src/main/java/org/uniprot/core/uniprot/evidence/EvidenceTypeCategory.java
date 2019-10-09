package org.uniprot.core.uniprot.evidence;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

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
