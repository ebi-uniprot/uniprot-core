package uk.ac.ebi.uniprot.domain.uniprot;

/**
 * 
 * @author jluo
 *
 */
public enum ReferenceCommentType {

    PLASMID("PLASMID"),
	TISSUE("TISSUE"),
	TRANSPOSON("TRANSPOSON"),
	STRAIN("STRAIN");

    private String value;

    private ReferenceCommentType(String type){
        this.value = type;
    }

    private String getValue() {
		return value;
	}

	public static ReferenceCommentType typeOf (String value) {
		for (ReferenceCommentType type : ReferenceCommentType.values()) {
			if (type.getValue().equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("The SampleSource type " + value + " doesn't exist");
	}
}
