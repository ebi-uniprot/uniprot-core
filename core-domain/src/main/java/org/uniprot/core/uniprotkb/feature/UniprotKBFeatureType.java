package org.uniprot.core.uniprotkb.feature;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.FeatureType;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;

public enum UniprotKBFeatureType implements FeatureType {
    /**
     * Enumerates all features types in UniProt. Important!!! The order which is here must the order
     * existing in the file cc_ord which is placed in /ebi/sp/misc1/pc/sprot/various/ft_ord
     */
    INIT_MET("initiator methionine", "Initiator methionine", FeatureCategory.MOLECULE_PROCESSING, EvidenceCode.ECO_0000269),
    SIGNAL("signal peptide", "Signal", FeatureCategory.MOLECULE_PROCESSING, EvidenceCode.ECO_0000255),
    PROPEP("propeptide", "Propeptide", FeatureCategory.MOLECULE_PROCESSING, EvidenceCode.ECO_0000305),
    TRANSIT("transit peptide", "Transit peptide", FeatureCategory.MOLECULE_PROCESSING, EvidenceCode.ECO_0000255),
    CHAIN("chain", "Chain", FeatureCategory.MOLECULE_PROCESSING, EvidenceCode.ECO_0000305),
    PEPTIDE("peptide", "Peptide", FeatureCategory.MOLECULE_PROCESSING, EvidenceCode.ECO_0000305),
    TOPO_DOM("topological domain", "Topological domain", FeatureCategory.REGIONS, EvidenceCode.ECO_0000305),
    TRANSMEM("transmembrane region", "Transmembrane", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    INTRAMEM("intramembrane region", "Intramembrane", FeatureCategory.REGIONS, EvidenceCode.ECO_0000305),
    DOMAIN("domain", "Domain", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    REPEAT("repeat", "Repeat", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    @Deprecated
    CA_BIND("calcium-binding region", "Calcium binding", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    ZN_FING("zinc finger region", "Zinc finger", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    DNA_BIND("DNA-binding region", "DNA binding", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    @Deprecated
    NP_BIND("nucleotide phosphate-binding region", "Nucleotide binding", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    REGION("region of interest", "Region", FeatureCategory.REGIONS, EvidenceCode.ECO_0000269),
    COILED("coiled-coil region", "Coiled coil", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    MOTIF("short sequence motif", "Motif", FeatureCategory.REGIONS, EvidenceCode.ECO_0000255),
    COMPBIAS("compositionally biased region", "Compositional bias", FeatureCategory.REGIONS, EvidenceCode.ECO_0000256),
    ACT_SITE("active site", "Active site", FeatureCategory.SITES, EvidenceCode.ECO_0000269),
    @Deprecated
    METAL("metal ion-binding site", "Metal binding", FeatureCategory.SITES, EvidenceCode.ECO_0000269),
    BINDING("binding site", "Binding site", FeatureCategory.SITES, EvidenceCode.ECO_0000269),
    SITE("site", "Site", FeatureCategory.SITES, EvidenceCode.ECO_0000269),
    NON_STD(
            "non-standard amino acid",
            "Non-standard residue",
            FeatureCategory.AMINO_ACID_MODIFICATIONS, EvidenceCode.ECO_0000305),
    MOD_RES("modified residue", "Modified residue", FeatureCategory.AMINO_ACID_MODIFICATIONS, EvidenceCode.ECO_0000269),
    LIPID("lipid moiety-binding region", "Lipidation", FeatureCategory.AMINO_ACID_MODIFICATIONS, EvidenceCode.ECO_0000255),
    CARBOHYD("glycosylation site", "Glycosylation", FeatureCategory.AMINO_ACID_MODIFICATIONS, EvidenceCode.ECO_0000269),
    DISULFID("disulfide bond", "Disulfide bond", FeatureCategory.AMINO_ACID_MODIFICATIONS, EvidenceCode.ECO_0000269),
    CROSSLNK("cross-link", "Cross-link", FeatureCategory.AMINO_ACID_MODIFICATIONS, EvidenceCode.ECO_0000269),
    VAR_SEQ("splice variant", "Alternative sequence", FeatureCategory.NATURAL_VARIATIONS, EvidenceCode.ECO_0000303),
    VARIANT("sequence variant", "Natural variant", FeatureCategory.NATURAL_VARIATIONS, EvidenceCode.ECO_0000269),
    MUTAGEN("mutagenesis site", "Mutagenesis", FeatureCategory.EXPERIMENTAL_INFO, EvidenceCode.ECO_0000269),
    UNSURE("unsure residue", "Sequence uncertainty", FeatureCategory.EXPERIMENTAL_INFO, EvidenceCode.ECO_0000269),
    CONFLICT("sequence conflict", "Sequence conflict", FeatureCategory.EXPERIMENTAL_INFO, EvidenceCode.ECO_0000305),
    NON_CONS(
            "non-consecutive residues", "Non-adjacent residues", FeatureCategory.EXPERIMENTAL_INFO, EvidenceCode.ECO_0000305),
    NON_TER("non-terminal residue", "Non-terminal residue", FeatureCategory.EXPERIMENTAL_INFO, EvidenceCode.ECO_0000305),
    HELIX("helix", "Helix", FeatureCategory.SECONDARY_STRUCTURE, EvidenceCode.ECO_0007829),
    TURN("turn", "Turn", FeatureCategory.SECONDARY_STRUCTURE, EvidenceCode.ECO_0007829),
    STRAND("strand", "Beta strand", FeatureCategory.SECONDARY_STRUCTURE, EvidenceCode.ECO_0007829);

    private final String value;
    private final String displayName;
    private final FeatureCategory category;
    private final EvidenceCode defaultEvidenceCode;

    UniprotKBFeatureType(String value, String displayName, FeatureCategory category, EvidenceCode defaultEvidenceCode) {
        this.value = value;
        this.displayName = displayName;
        this.category = category;
        this.defaultEvidenceCode = defaultEvidenceCode;
    }

    public @Nonnull String getValue() {
        return value;
    }

    public @Nonnull String getName() {
        return name();
    }

    public static @Nonnull UniprotKBFeatureType typeOf(@Nonnull String value) {
        for (UniprotKBFeatureType featureType : UniprotKBFeatureType.values()) {
            if (featureType.name().equalsIgnoreCase(value)) {
                return featureType;
            }
            if (featureType.getValue().equalsIgnoreCase(value)) {
                return featureType;
            }
        }
        throw new IllegalArgumentException(
                "the fetaure with the description " + value + " doesn't exist");
    }

    @Override
    public @Nonnull String getDisplayName() {
        return displayName;
    }

    @Override
    public @Nonnull String getCompareOn() {
        return value;
    }

    public FeatureCategory getCategory() {
        return category;
    }

    public EvidenceCode getDefaultEvidenceCode() {
        return defaultEvidenceCode;
    }
}
