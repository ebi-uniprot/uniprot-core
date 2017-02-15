package uk.ac.ebi.uniprot.domain.uniprot.comments;

public enum InteractionType {
	SELF,
	XENO,
	BINARY,
	UNKNOWN;

	public String  getLink (){

		switch (this){
			case SELF: return "http://www.ebi.ac.uk/intact/search/do/search?self=";
			case BINARY: return "http://www.ebi.ac.uk/intact/search/do/search?binary=";
			case XENO: return "http://www.ebi.ac.uk/intact/search/do/search?xeno=";
			case UNKNOWN: return "";
		}

        return "";
	}

}
