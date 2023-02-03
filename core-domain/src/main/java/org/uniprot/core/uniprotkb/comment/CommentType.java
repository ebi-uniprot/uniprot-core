package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
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
    FUNCTION("FUNCTION", "function", EvidenceCode.ECO_0000269),
    CATALYTIC_ACTIVITY("CATALYTIC ACTIVITY", "catalytic activity", EvidenceCode.ECO_0000269),
    COFACTOR("COFACTOR", "cofactor", EvidenceCode.ECO_0000269),
    ACTIVITY_REGULATION("ACTIVITY REGULATION", "activity regulation", EvidenceCode.ECO_0000269),
    BIOPHYSICOCHEMICAL_PROPERTIES("BIOPHYSICOCHEMICAL PROPERTIES", "biophysicochemical properties", EvidenceCode.ECO_0000269),
    PATHWAY("PATHWAY", "pathway", EvidenceCode.ECO_0000269),
    SUBUNIT("SUBUNIT", "subunit", EvidenceCode.ECO_0000269),
    INTERACTION("INTERACTION", "interaction", EvidenceCode.ECO_0000305),
    SUBCELLULAR_LOCATION("SUBCELLULAR LOCATION", "subcellular location", EvidenceCode.ECO_0000269),
    ALTERNATIVE_PRODUCTS("ALTERNATIVE PRODUCTS", "alternative products", EvidenceCode.ECO_0000269),
    TISSUE_SPECIFICITY("TISSUE SPECIFICITY", "tissue specificity", EvidenceCode.ECO_0000269),
    DEVELOPMENTAL_STAGE("DEVELOPMENTAL STAGE", "developmental stage", EvidenceCode.ECO_0000269),
    INDUCTION("INDUCTION", "induction", EvidenceCode.ECO_0000269),
    DOMAIN("DOMAIN", "domain", EvidenceCode.ECO_0000305),
    PTM("PTM", "PTM", EvidenceCode.ECO_0000269),
    RNA_EDITING("RNA EDITING", "RNA editing", EvidenceCode.ECO_0000269),
    MASS_SPECTROMETRY("MASS SPECTROMETRY", "mass spectrometry", EvidenceCode.ECO_0000269),
    POLYMORPHISM("POLYMORPHISM", "polymorphism", EvidenceCode.ECO_0000269),
    DISEASE("DISEASE", "disease", EvidenceCode.ECO_0000269),
    DISRUPTION_PHENOTYPE("DISRUPTION PHENOTYPE", "disruption phenotype", EvidenceCode.ECO_0000269),
    ALLERGEN("ALLERGEN", "allergen", EvidenceCode.ECO_0000269),
    TOXIC_DOSE("TOXIC DOSE", "toxic dose", EvidenceCode.ECO_0000269),
    BIOTECHNOLOGY("BIOTECHNOLOGY", "biotechnology", EvidenceCode.ECO_0000269),
    PHARMACEUTICAL("PHARMACEUTICAL", "pharmaceutical", EvidenceCode.ECO_0000269),
    MISCELLANEOUS("MISCELLANEOUS", "miscellaneous", EvidenceCode.ECO_0000305),
    SIMILARITY("SIMILARITY", "similarity", EvidenceCode.ECO_0000305),
    CAUTION("CAUTION", "caution", EvidenceCode.ECO_0000305),
    SEQUENCE_CAUTION("SEQUENCE CAUTION", "sequence caution", EvidenceCode.ECO_0000305),
    WEBRESOURCE("WEB RESOURCE", "online information", null),
    UNKNOWN("UNKOWN", "unknown", null);

    private String name;
    private final String xmlDisplayValue;
    private final EvidenceCode defaultEvidenceCode;

    CommentType(String name, String xmlDisplayValue, EvidenceCode defaultEvidenceCode) {
        this.name = name;
        this.xmlDisplayValue = xmlDisplayValue;
        this.defaultEvidenceCode = defaultEvidenceCode;
    }

    public @Nonnull String getName() {
        return name;
    }

    public EvidenceCode getDefaultEvidenceCode() {
        return defaultEvidenceCode;
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
