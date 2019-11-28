package org.uniprot.core.citation;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum SubmissionDatabase implements EnumDisplay<SubmissionDatabase> {
    PDB("PDB data bank"),
    PIR("PIR data bank"),
    SWISS_PROT("Swiss-Prot"),
    UNIPROTKB("UniProtKB"),
    EMBL_GENBANK_DDBJ("EMBL/GenBank/DDBJ databases"),
    UNKNOWN("Unknown");

    private String name;

    SubmissionDatabase(String name) {
        this.name = name;
    }

    public static @Nonnull SubmissionDatabase typeOf(String name) {
        for (SubmissionDatabase submissionDatabase : SubmissionDatabase.values()) {
            if (submissionDatabase.getName().equalsIgnoreCase(name)) {
                return submissionDatabase;
            }
        }
        throw new IllegalArgumentException(
                "the feature with the description " + name + " doesn't exist");
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return getName();
    }
}
