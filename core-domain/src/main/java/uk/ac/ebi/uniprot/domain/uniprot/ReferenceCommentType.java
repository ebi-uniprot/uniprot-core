package uk.ac.ebi.uniprot.domain.uniprot;

/**
 * 
 * @author jluo
 *
 */
public enum ReferenceCommentType {
	STRAIN("STRAIN"),
    PLASMID("PLASMID"),
    TRANSPOSON("TRANSPOSON"),
	TISSUE("TISSUE");

    private String value;
    
    private ReferenceCommentType(String type){
        this.value = type;
        
    }

    public String getValue() {
		return value;
	}

	public static ReferenceCommentType typeOf (String value) {
		for (ReferenceCommentType type : ReferenceCommentType.values()) {
			if (type.getValue().equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("The ReferenceComment type " + value + " doesn't exist");
	}
}
