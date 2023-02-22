package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * Enumerates all comment types in UniProt. Important!!! The order which is here must the order
 * existing in the file cc_ord which is placed in /eabbi/sp/misc1/pc/sprot/various/cc_ord
 *
 * <p>CC -!- FUNCTION: Text. CC -!- CATALYTIC ACTIVITY: Text. CC -!- COFACTOR: Text. CC -!- ENZYME
 * REGULATION: Text. CC -!- BIOPHYSICOCHEMICAL PROPERTIES: Text. CC -!- PATHWAY: Text. CC -!-
 * SUBUNIT: Text. CC -!- INTERACTION: Standardized format. CC -!- SUBCELLULAR LOCATION: Text. CC -!-
 * ALTERNATIVE PRODUCTS: Text. CC -!- TISSUE SPECIFICITY: Text. CC -!- DEVELOPMENTAL STAGE: Text. CC
 * -!- INDUCTION: Text. CC -!- DOMAIN: Text. CC -!- PTM: Text. CC -!- RNA EDITING: Text. CC -!- MASS
 * SPECTROMETRY: Text. CC -!- POLYMORPHISM: Text. CC -!- DISEASE: Text. CC -!- DISRUPTION PHENOTYPE:
 * Text. CC -!- ALLERGEN: Text. CC -!- TOXIC DOSE: Text. CC -!- BIOTECHNOLOGY: Text. CC -!-
 * PHARMACEUTICAL: Text. CC -!- MISCELLANEOUS: Text. CC -!- SIMILARITY: Text. CC -!- CAUTION: Text.
 * CC -!- SEQUENCE CAUTION: Text. CC -!- WEB RESOURCE: Text.
 */
public enum CommentType implements EnumDisplay {
    FUNCTION("FUNCTION", "function", true),
    CATALYTIC_ACTIVITY("CATALYTIC ACTIVITY", "catalytic activity", true),
    COFACTOR("COFACTOR", "cofactor", true),
    ACTIVITY_REGULATION("ACTIVITY REGULATION", "activity regulation", true),
    BIOPHYSICOCHEMICAL_PROPERTIES(
            "BIOPHYSICOCHEMICAL PROPERTIES", "biophysicochemical properties", true),
    PATHWAY("PATHWAY", "pathway", true),
    SUBUNIT("SUBUNIT", "subunit", true),
    INTERACTION("INTERACTION", "interaction", false),
    SUBCELLULAR_LOCATION("SUBCELLULAR LOCATION", "subcellular location", true),
    ALTERNATIVE_PRODUCTS("ALTERNATIVE PRODUCTS", "alternative products", false),
    TISSUE_SPECIFICITY("TISSUE SPECIFICITY", "tissue specificity", true),
    DEVELOPMENTAL_STAGE("DEVELOPMENTAL STAGE", "developmental stage", true),
    INDUCTION("INDUCTION", "induction", true),
    DOMAIN("DOMAIN", "domain", false),
    PTM("PTM", "PTM", true),
    RNA_EDITING("RNA EDITING", "RNA editing", true),
    MASS_SPECTROMETRY("MASS SPECTROMETRY", "mass spectrometry", true),
    POLYMORPHISM("POLYMORPHISM", "polymorphism", true),
    DISEASE("DISEASE", "disease", true),
    DISRUPTION_PHENOTYPE("DISRUPTION PHENOTYPE", "disruption phenotype", true),
    ALLERGEN("ALLERGEN", "allergen", true),
    TOXIC_DOSE("TOXIC DOSE", "toxic dose", true),
    BIOTECHNOLOGY("BIOTECHNOLOGY", "biotechnology", true),
    PHARMACEUTICAL("PHARMACEUTICAL", "pharmaceutical", true),
    MISCELLANEOUS("MISCELLANEOUS", "miscellaneous", false),
    SIMILARITY("SIMILARITY", "similarity", false),
    CAUTION("CAUTION", "caution", false),
    SEQUENCE_CAUTION("SEQUENCE CAUTION", "sequence caution", false),
    WEBRESOURCE("WEB RESOURCE", "online information", false),
    UNKNOWN("UNKOWN", "unknown", false);

    private final String name;
    private final String xmlDisplayValue;
    private final boolean addExperimental;

    CommentType(String name, String xmlDisplayValue, boolean addExperimental) {
        this.name = name;
        this.xmlDisplayValue = xmlDisplayValue;
        this.addExperimental = addExperimental;
    }

    public @Nonnull String getName() {
        return name;
    }

    /**
     * Return if we can add implicit experimental evidence for this CommentType.
     *
     * @return
     */
    public boolean isAddExperimental() {
        return addExperimental;
    }

    /**
     * Return the comment type as found in the uniprot.xsd i.e. basically lowercase.
     *
     * @return
     */
    public @Nonnull String toXmlDisplayName() {
        return xmlDisplayValue;
    }

    public static @Nonnull CommentType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, CommentType.class);
    }
}
