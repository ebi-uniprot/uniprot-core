package uk.ac.ebi.uniprot.domain.uniprot.features;


public enum FeatureType {
    /**
     * Enumerates all features types in UniProt.
     * Important!!! The order which is here must
     * the order existing in the file  cc_ord which
     * is placed in /ebi/sp/misc1/pc/sprot/various/ft_ord
     */
    INIT_MET("initiator methionine"),
    SIGNAL("signal peptide"),
    PROPEP("propeptide"),
    TRANSIT("transit peptide"),
    CHAIN("chain"),
    PEPTIDE("peptide"),
    TOPO_DOM("topological domain"),
    TRANSMEM("transmembrane region"),
    DOMAIN("domain","Other domain of interest"),
    REPEAT("repeat"),
    CA_BIND("calcium-binding region"),
    ZN_FING("zinc finger region"),
    DNA_BIND("DNA-binding region"),
    NP_BIND("nucleotide phosphate-binding region","Nucleotide-binding region"),
    REGION("region of interest"),
    COILED("coiled-coil region"),
    MOTIF("short sequence motif"),
    COMPBIAS("compositionally biased region"),
    ACT_SITE("active site"),
    METAL("metal ion-binding site"),
    BINDING("binding site","Other binding site"),
    SITE("site","Other site of interest"),
    NON_STD("non-standard amino acid"),
    MOD_RES("modified residue","Post-translationally modified residue"),
    LIPID("lipid moiety-binding region"),
    CARBOHYD("glycosylation site"),
    DISULFID("disulfide bond"),
    CROSSLNK("cross-link"),
    VAR_SEQ("splice variant"),
    VARIANT("sequence variant","Sequence variation"),
    MUTAGEN("mutagenesis site"),
    UNSURE("unsure residue","Uncertainty in sequence"),
    CONFLICT("sequence conflict"),
    NON_CONS("non-consecutive residues"),
    NON_TER("non-terminal residue"),
    HELIX("helix"),
    TURN("turn"),
    STRAND("strand"),
    INTRAMEM("intramembrane region");


    private String value;
    private String displayName;

    FeatureType(String value,
                String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    private FeatureType(String type) {
        this(type, type);
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name();
    }

    public String getDisplayName() {
        return displayName;
    }

    public static FeatureType typeOf(String value) {
        for (FeatureType featureType : FeatureType.values()) {
            if (featureType.getValue().equals(value)) {
                return featureType;
            }
        }
        throw new IllegalArgumentException("the fetaure with the description " + value + " doesn't exist");
    }
}
