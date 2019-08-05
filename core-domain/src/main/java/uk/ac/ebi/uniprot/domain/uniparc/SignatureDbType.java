package uk.ac.ebi.uniprot.domain.uniparc;

import org.uniprot.core.common.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 21 May 2019
 *
*/

public enum SignatureDbType implements EnumDisplay<SignatureDbType> {
	CDD ("CDD"),
	GENE3D("Gene3D"),
	HAMAP("HAMAP"),
	PANTHER("PANTHER"),
	PFAM("Pfam"),
	PIRSF("PIRSF"),
	PRINTS("PRINTS"),
	PRODOM("ProDom"),
	PROSITE("PROSITE"),
	SFLD("SFLD"),
	SMART("SMART"),
	SUPFAM("SUPFAM"),
	TIGRFAMS("TIGRFAMs");

	private final String name;
	
	SignatureDbType(String name){
		this.name= name;
	}
	@Override
	public String toDisplayName() {
		return name;
	}

	public static SignatureDbType typeOf(String value) {
		for (SignatureDbType type : SignatureDbType.values()) {
			if (type.toDisplayName().equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("The SignatureDbType: " + value + " doesn't exist");

	}
}

