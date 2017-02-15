package uk.ac.ebi.uniprot.domain.uniprot;

/**
 * 
 * @author jieluo
 * @date   17 Jan 2017
 * @time   18:40:27
 *
 */
public enum GeneEncodingType {

    UNKOWN ("unknown"),
    HYDROGENOSOME("Hydrogenosome"),
    MITOCHONDRION("Mitochondrion"),
    NUCLEOMORPH("Nucleomorph"),
    PLASMID("Plasmid"),
    PLASTID("Plastid"),
    APICOPLAST_PLASTID("Apicoplast"),
    CHLOROPLAST_PLASTID("Chloroplast"),
    CYANELLE_PLASTID("Cyanelle"),
    NON_PHOTOSYNTHETIC_PLASTID("Non-photosynthetic plastid"),
    CHROMATOPHORE_PLASTID("Organellar chromatophore");

	private String value;

	private GeneEncodingType(String type) {
		this.value = type;
	}

	public String getValue() {
		return this.value;
	}

	public static GeneEncodingType typeOf (String value) {
		for (GeneEncodingType geneEncodingType : GeneEncodingType.values()) {
			if (geneEncodingType.getValue().equalsIgnoreCase(value)) {
				return geneEncodingType;
			}
		}
		throw new IllegalArgumentException("the gene Encoding Type with the description "+value+" doesn't exist");
	}

}
