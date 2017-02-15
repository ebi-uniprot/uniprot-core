package uk.ac.ebi.uniprot.domain.uniprot.citation;


public enum SubmissionDatabase {

	PDB("PDB data bank"),
	PIR("PIR data bank"),
	SWISS_PROT("Swiss-Prot"),
    UNIPROTKB("UniProtKB"),
    EMBL_GENBANK_DDBJ("EMBL/GenBank/DDBJ databases"),
	UNKNOWN("Unknown");

	private String value;

	private SubmissionDatabase(String type) {
		this.value = type;
	}

	public String getValue() {
		return value;
	}

	public static SubmissionDatabase typeOf(String value) {
		for (SubmissionDatabase submissionDatabase : SubmissionDatabase.values()) {
			if (submissionDatabase.getValue().equals(value)) {
				return submissionDatabase;
			}
		}
		throw new IllegalArgumentException("the feature with the description " + value + " doesn't exist");
	}
}
