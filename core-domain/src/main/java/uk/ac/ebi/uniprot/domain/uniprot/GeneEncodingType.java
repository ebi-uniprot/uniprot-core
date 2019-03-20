package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.cv.common.EnumDisplay;

/**
 * @author jieluo
 * @date 17 Jan 2017
 * @time 18:40:27
 */
public enum GeneEncodingType implements EnumDisplay<GeneEncodingType> {

    UNKOWN("unknown"),
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

    private String name;

    GeneEncodingType(String name) {
        this.name = name;
    }

    public static GeneEncodingType typeOf(String name) {
        for (GeneEncodingType geneEncodingType : GeneEncodingType.values()) {
            if (geneEncodingType.getName().equalsIgnoreCase(name)) {
                return geneEncodingType;
            }
        }
        throw new IllegalArgumentException("the gene Encoding Type with the description " + name + " doesn't exist");
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toDisplayName() {
        return name;
    }
}
