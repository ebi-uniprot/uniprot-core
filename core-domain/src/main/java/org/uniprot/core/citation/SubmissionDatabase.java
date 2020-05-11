package org.uniprot.core.citation;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum SubmissionDatabase implements EnumDisplay {
    PDB("PDB data bank"),
    PIR("PIR data bank"),
    SWISS_PROT("Swiss-Prot"),
    UNIPROTKB("UniProtKB"),
    EMBL_GENBANK_DDBJ("EMBL/GenBank/DDBJ databases");

    private String name;

    SubmissionDatabase(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull SubmissionDatabase typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, SubmissionDatabase.class);
    }
}
