package org.uniprot.core.uniparc;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author jluo
 * @date: 21 May 2019
 */
public enum SignatureDbType implements EnumDisplay<SignatureDbType> {
    CDD("CDD"),
    GENE3D("Gene3D"),
    HAMAP("HAMAP"),
    PANTHER("PANTHER"),
    PFAM("Pfam"),
    PIRSF("PIRSF"),
    PRINTS("PRINTS"),
    PRODOM("ProDom"),
    PROSITE("PROSITE"),
    SFLD("SFLD"),
    SMART("SMART"),
    SUPFAM("SUPFAM"),
    TIGRFAMS("TIGRFAMs");

    private final String name;

    SignatureDbType(String name) {
        this.name = name;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return name;
    }

    public static @Nonnull SignatureDbType typeOf(@Nonnull String value) {
        for (SignatureDbType type : SignatureDbType.values()) {
            if (type.toDisplayName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("The SignatureDbType: " + value + " doesn't exist");
    }
}
