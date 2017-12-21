package uk.ac.ebi.uniprot.domain.uniprot.comments;

/**
 * Enumerates all comment types in UniProt.
 * Important!!! The order which is here must
 * the order existing in the file  cc_ord which
 * is placed in /eabbi/sp/misc1/pc/sprot/various/cc_ord
 *
 * CC   -!- FUNCTION: Text.
CC   -!- CATALYTIC ACTIVITY: Text.
CC   -!- COFACTOR: Text.
CC   -!- ENZYME REGULATION: Text.
CC   -!- BIOPHYSICOCHEMICAL PROPERTIES: Text.
CC   -!- PATHWAY: Text.
CC   -!- SUBUNIT: Text.
CC   -!- INTERACTION: Standardized format.
CC   -!- SUBCELLULAR LOCATION: Text.
CC   -!- ALTERNATIVE PRODUCTS: Text.
CC   -!- TISSUE SPECIFICITY: Text.
CC   -!- DEVELOPMENTAL STAGE: Text.
CC   -!- INDUCTION: Text.
CC   -!- DOMAIN: Text.
CC   -!- PTM: Text.
CC   -!- RNA EDITING: Text.
CC   -!- MASS SPECTROMETRY: Text.
CC   -!- POLYMORPHISM: Text.
CC   -!- DISEASE: Text.
CC   -!- DISRUPTION PHENOTYPE: Text.
CC   -!- ALLERGEN: Text.
CC   -!- TOXIC DOSE: Text.
CC   -!- BIOTECHNOLOGY: Text.
CC   -!- PHARMACEUTICAL: Text.
CC   -!- MISCELLANEOUS: Text.
CC   -!- SIMILARITY: Text.
CC   -!- CAUTION: Text.
CC   -!- SEQUENCE CAUTION: Text.
CC   -!- WEB RESOURCE: Text.
 *
 */
public enum CommentType {



    FUNCTION("FUNCTION","function"),
    CATALYTIC_ACTIVITY("CATALYTIC ACTIVITY","catalytic activity"),
    COFACTOR("COFACTOR", "cofactor"),
    ENZYME_REGULATION("ENZYME REGULATION", "enzyme regulation"),
    BIOPHYSICOCHEMICAL_PROPERTIES("BIOPHYSICOCHEMICAL PROPERTIES", "biophysicochemical properties"),
    PATHWAY("PATHWAY", "pathway"),
    SUBUNIT("SUBUNIT", "subunit"),
    INTERACTION("INTERACTION", "interaction"),
    SUBCELLULAR_LOCATION("SUBCELLULAR LOCATION", "subcellular location"),
    ALTERNATIVE_PRODUCTS("ALTERNATIVE PRODUCTS", "alternative products"),
    TISSUE_SPECIFICITY("TISSUE SPECIFICITY", "tissue specificity"),
    DEVELOPMENTAL_STAGE("DEVELOPMENTAL STAGE", "developmental stage"),
    INDUCTION("INDUCTION", "induction"),
    DOMAIN("DOMAIN", "domain"),
    PTM("PTM", "PTM"),
    RNA_EDITING("RNA EDITING", "RNA editing"),
	MASS_SPECTROMETRY("MASS SPECTROMETRY", "mass spectrometry"),
	POLYMORPHISM("POLYMORPHISM","polymorphism"),
	DISEASE("DISEASE","disease"),
	DISRUPTION_PHENOTYPE("DISRUPTION PHENOTYPE","disruption phenotype"),
	ALLERGEN("ALLERGEN","allergen"),
	TOXIC_DOSE("TOXIC DOSE","toxic dose"),
	BIOTECHNOLOGY("BIOTECHNOLOGY","biotechnology"),
	PHARMACEUTICAL("PHARMACEUTICAL","pharmaceutical"),
	MISCELLANEOUS("MISCELLANEOUS","miscellaneous"),
	SIMILARITY("SIMILARITY","similarity"),
	CAUTION("CAUTION","caution"),
	SEQUENCE_CAUTION("SEQUENCE CAUTION","sequence caution"),
	WEBRESOURCE("WEB RESOURCE","online information"),
    UNKNOWN("UNKOWN","unknown");

	private final String xmlDisplayValue;
	private String value;

    CommentType(String value, String xmlDisplayValue) {
        this.value = value;
		this.xmlDisplayValue = xmlDisplayValue;
    }

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    public String toDisplayName() {
        return value;
    }

	/**
	 * Return the comment type as found in the uniprot.xsd i.e. basically lowercase.
	 * @return
	 */
	public String toXmlDisplayName() {
		return xmlDisplayValue;
	}

    public static CommentType dbTypeOf(String value) {
        for (CommentType commentType : CommentType.values()) {
            if (commentType.toString().equalsIgnoreCase(value)) {
                return commentType;
            }
        }

        throw new IllegalArgumentException("The comment type: " + value + " doesn't exist");
    }

    public static CommentType typeOf(String value) {
        for (CommentType commentType : CommentType.values()) {
            if (commentType.toDisplayName().trim().equalsIgnoreCase(value.trim())) {
                return commentType;
            }
        }

        throw new IllegalArgumentException("The comment type: " + value + " doesn't exist");
    }

}
