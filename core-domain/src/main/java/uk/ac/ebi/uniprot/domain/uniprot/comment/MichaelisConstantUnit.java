package uk.ac.ebi.uniprot.domain.uniprot.comment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MichaelisConstantUnit {

	MOL ("M"),
	MILLI_MOL ("mM"),
	MICRO_MOL ("uM"),
	NANO_MOL ("nM"),
	MG_ML_2 ("mg/mL"),
	MG_ML ("mg/ml");

	private String name;
	MichaelisConstantUnit(String name){
		this.name = name;
	}
	public static MichaelisConstantUnit convert(String unit) {
		for (MichaelisConstantUnit value : MichaelisConstantUnit.values()) {
			if (value.name.equals(unit)) {
				return value;
			}
		}
		throw new RuntimeException("unknown Michaelis Constant Unit: " + unit);
	}
	
	 @JsonValue
 	public String getName() {
		return name;
	}

    /**
     * Added for beans - just use this rather than toDisplayNameString?
     * @return String - the display name
     */
    public String getDisplayString(){
        return toDisplayNameString();
    }

    public String toDisplayNameString() {
    	return name;
	}
}
