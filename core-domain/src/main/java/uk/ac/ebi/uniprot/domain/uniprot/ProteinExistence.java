package uk.ac.ebi.uniprot.domain.uniprot;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 
 * @author jieluo
 *
 */
public enum ProteinExistence {

    PROTEIN_LEVEL ("Evidence at protein level", "1: Evidence at protein level"),
    TRANSCRIPT_LEVEL("Evidence at transcript level", "2: Evidence at transcript level"),
    HOMOLOGY("Inferred from homology", "3: Inferred from homology"),
	PREDICTED("Predicted", "4: Predicted"),
	UNCERTAIN("Uncertain", "5: Uncertain"),
	UNKNOWN("UNKNOWN", "UNKNOWN");
    
	private String value;
	private String displayName;

	public String getDisplayName() {
		return displayName;
	}
	private ProteinExistence(String type) {
		this.value = type;
		this.displayName =type;
	}
	private ProteinExistence(String type, String displayName) {
		this.value = type;
		this.displayName = displayName;
	}
	 @JsonValue
	public String getValue() {
		return value;
	}

	public static ProteinExistence typeOf(String value) {
         for (ProteinExistence proteinExistence : ProteinExistence.values()) {
             if (proteinExistence.getValue().equalsIgnoreCase(value)) {
				return proteinExistence;
			}
		}
		throw new IllegalArgumentException("the proteinExistence " + value + " doesn't exist");
	}


    public String toString() {
        return this.getValue();
    }
}
