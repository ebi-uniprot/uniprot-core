package org.uniprot.core.uniparc;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum SignatureDbType implements EnumDisplay {
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
    NCBIFAM("NCBIfam");

    private final String name;

    SignatureDbType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull SignatureDbType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, SignatureDbType.class);
    }
}
