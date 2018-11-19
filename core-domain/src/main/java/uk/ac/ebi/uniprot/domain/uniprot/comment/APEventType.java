package uk.ac.ebi.uniprot.domain.uniprot.comment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum APEventType {
	ALTERNATIVE_PROMOTER_USAGE("Alternative promoter usage"),
	ALTERNATIVE_SPLICING("Alternative splicing"),
	ALTERNATIVE_INITIATION("Alternative initiation"),
	RIBOSOMAL_FRAMESHIFTING("Ribosomal frameshifting");
	
	private final String name;
	APEventType(String name){
		this.name= name;
	}
	 @JsonValue
	public String getName() {
		return name;
	}
	 public static APEventType typeOf(String value) {
	        for (APEventType type : APEventType.values()) {
	            if (type.getName().equalsIgnoreCase(value)) {
	                return type;
	            }
	        }

	        throw new IllegalArgumentException("The AP event type: " + value + " doesn't exist");
	    }
}