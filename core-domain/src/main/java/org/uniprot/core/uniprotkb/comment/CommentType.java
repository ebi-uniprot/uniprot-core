package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

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
    FUNCTION("FUNCTION", "function"),
    CATALYTIC_ACTIVITY("CATALYTIC ACTIVITY", "catalytic activity"),
    COFACTOR("COFACTOR", "cofactor"),
    ACTIVITY_REGULATION("ACTIVITY REGULATION", "activity regulation"),
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
    POLYMORPHISM("POLYMORPHISM", "polymorphism"),
    DISEASE("DISEASE", "disease"),
    DISRUPTION_PHENOTYPE("DISRUPTION PHENOTYPE", "disruption phenotype"),
    ALLERGEN("ALLERGEN", "allergen"),
    TOXIC_DOSE("TOXIC DOSE", "toxic dose"),
    BIOTECHNOLOGY("BIOTECHNOLOGY", "biotechnology"),
    PHARMACEUTICAL("PHARMACEUTICAL", "pharmaceutical"),
    MISCELLANEOUS("MISCELLANEOUS", "miscellaneous"),
    SIMILARITY("SIMILARITY", "similarity"),
    CAUTION("CAUTION", "caution"),
    SEQUENCE_CAUTION("SEQUENCE CAUTION", "sequence caution"),
    WEBRESOURCE("WEB RESOURCE", "online information"),
    UNKNOWN("UNKOWN", "unknown");

    private String name;
    private final String xmlDisplayValue;

    CommentType(String name, String xmlDisplayValue) {
        this.name = name;
        this.xmlDisplayValue = xmlDisplayValue;
    }

    public @Nonnull String getName() {
        return name;
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
