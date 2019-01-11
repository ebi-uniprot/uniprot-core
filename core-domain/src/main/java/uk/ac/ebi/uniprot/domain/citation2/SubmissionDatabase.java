package uk.ac.ebi.uniprot.domain.citation2;

import uk.ac.ebi.uniprot.domain.EnumDisplay;

public enum SubmissionDatabase implements EnumDisplay<SubmissionDatabase> {

    PDB("PDB data bank"),
    PIR("PIR data bank"),
    SWISS_PROT("Swiss-Prot"),
    UNIPROTKB("UniProtKB"),
    EMBL_GENBANK_DDBJ("EMBL/GenBank/DDBJ databases"),
    UNKNOWN("Unknown");

    private String name;

    private SubmissionDatabase(String name) {
        this.name = name;
    }

    public static SubmissionDatabase typeOf(String name) {
        for (SubmissionDatabase submissionDatabase : SubmissionDatabase
                .values()) {
            if (submissionDatabase.getName().equals(name)) {
                return submissionDatabase;
            }
        }
        throw new IllegalArgumentException("the feature with the description " + name + " doesn't exist");
    }

    public String getName() {
        return name;
    }

    @Override
    public String toDisplayName() {
        return getName();
    }
}
