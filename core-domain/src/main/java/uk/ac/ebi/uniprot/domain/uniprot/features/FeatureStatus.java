package uk.ac.ebi.uniprot.domain.uniprot.features;

/**
 * Defines which status can be assigned to a Swissprot Feature.
 */
public enum FeatureStatus {

	EXPERIMENTAL("Experimental"), // DEFAULT
	POTENTIAL("Potential"),
	PROBABLE("Probable"),
	BY_SIMILARITY("By similarity"),
	OTHER("Other"),//lawrence adding for SPIN - will go once added support elsewhere
	NONE("None"),//lawrence adding for SPIN - will go once added support elsewhere
    DEFAULT("Default");  //In case there is not Feature Status defined


    private String value;

    private FeatureStatus(String status) {
        this.value = status;
    }

	public String getName(){
		return name();
	}

	public String getValue() {
		return value;
	}

	static public FeatureStatus typeOf(String value) {
		for (FeatureStatus status : FeatureStatus.values()) {
			if(status.getValue().equalsIgnoreCase(value)){
				return status;
			}
		}
		System.err.println("value = " + value);
		throw new IllegalArgumentException();
	}

    public String shortName() {
		switch (this) {
            case EXPERIMENTAL:
               return "EXP";
            case POTENTIAL:
                return "POT";
            case PROBABLE:
                return "PRO";
            case BY_SIMILARITY:
                return "SIM";
            case OTHER:
                return "OTH";
            case NONE:
                return "NON";
            case DEFAULT:
                return "DEF";
            }

        return "";
    }

}
