package org.uniprot.core.parser.gff.uniprot;

/**
 * 
 * @author gqi
 *
 */

public enum FeatureLabel {
    /**
     * org.expasy.uniprot.models.Annotation.Type uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureType
     * 
     * Based the above two classes to match the feature key and label    used by gff file
     * 
     */
    INIT_MET("Initiator methionine"),
    SIGNAL("Signal peptide"),
    PROPEP("Propeptide"),
    TRANSIT("Transit peptide"),
    CHAIN("Chain"),
    PEPTIDE("Peptide"),
    TOPO_DOM("Topological domain"),
    TRANSMEM("Transmembrane"),
    DOMAIN("Domain"),
    REPEAT("Repeat"),
    CA_BIND("Calcium binding"),
    ZN_FING("Zinc finger"),
    DNA_BIND("DNA binding"),
    NP_BIND("Nucleotide binding"),             
    REGION("Region"),
    COILED("Coiled coil"),
    MOTIF("Motif"),
    COMPBIAS("Compositional bias"),
    ACT_SITE("Active site"),
    METAL("Metal binding"),
    BINDING("Binding site"),
    SITE("Site"),
    NON_STD("Non-standard residue"),
    MOD_RES("Modified residue"),
    LIPID("Lipidation"),
    CARBOHYD("Glycosylation"),
    DISULFID("Disulfide bond"),
    CROSSLNK("Cross-link"),
    VAR_SEQ("Alternative sequence"),
    VARIANT("Natural variant"),
    MUTAGEN("Mutagenesis"),
    UNSURE("Sequence uncertainty"),
    CONFLICT("Sequence conflict"),
    NON_CONS("Non-adjacent residues"),
    NON_TER("Non-terminal residue"),
    HELIX("Helix"),
    TURN("Turn"),
    STRAND("Beta strand"),
    INTRAMEM("Intramembrane");

    private String label;

    private FeatureLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static String getLabelFromName(String name) {
        for (FeatureLabel featureLabel : FeatureLabel.values()) {
            if (featureLabel.name().equals(name)) {
                return featureLabel.getLabel();
            }
        }
        throw new IllegalArgumentException("the fetaure with the name " + name + " doesn't exist");
    }

}
