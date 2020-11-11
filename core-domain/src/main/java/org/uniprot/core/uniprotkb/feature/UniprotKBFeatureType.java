package org.uniprot.core.uniprotkb.feature;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.FeatureType;

public enum UniprotKBFeatureType implements FeatureType {
    /**
     * Enumerates all features types in UniProt. Important!!! The order which is here must the order
     * existing in the file cc_ord which is placed in /ebi/sp/misc1/pc/sprot/various/ft_ord
     */
    INIT_MET("initiator methionine", "Initiator methionine", FeatureCategory.MOLECULE_PROCESSING),
    SIGNAL("signal peptide", "Signal", FeatureCategory.MOLECULE_PROCESSING),
    PROPEP("propeptide", "Propeptide", FeatureCategory.MOLECULE_PROCESSING),
    TRANSIT("transit peptide", "Transit peptide", FeatureCategory.MOLECULE_PROCESSING),
    CHAIN("chain", "Chain", FeatureCategory.MOLECULE_PROCESSING),
    PEPTIDE("peptide", "Peptide", FeatureCategory.MOLECULE_PROCESSING),
    TOPO_DOM("topological domain", "Topological domain", FeatureCategory.REGIONS),
    TRANSMEM("transmembrane region", "Transmembrane", FeatureCategory.REGIONS),
    INTRAMEM("intramembrane region", "Intramembrane", FeatureCategory.REGIONS),
    DOMAIN("domain", "Domain", FeatureCategory.REGIONS),
    REPEAT("repeat", "Repeat", FeatureCategory.REGIONS),
    CA_BIND("calcium-binding region", "Calcium binding", FeatureCategory.REGIONS),
    ZN_FING("zinc finger region", "Zinc finger", FeatureCategory.REGIONS),
    DNA_BIND("DNA-binding region", "DNA binding", FeatureCategory.REGIONS),
    NP_BIND("nucleotide phosphate-binding region", "Nucleotide binding", FeatureCategory.REGIONS),
    REGION("region of interest", "Region", FeatureCategory.REGIONS),
    COILED("coiled-coil region", "Coiled coil", FeatureCategory.REGIONS),
    MOTIF("short sequence motif", "Motif", FeatureCategory.REGIONS),
    COMPBIAS("compositionally biased region", "Compositional bias", FeatureCategory.REGIONS),
    ACT_SITE("active site", "Active site", FeatureCategory.SITES),
    METAL("metal ion-binding site", "Metal binding", FeatureCategory.SITES),
    BINDING("binding site", "Binding site", FeatureCategory.SITES),
    SITE("site", "Site", FeatureCategory.SITES),
    NON_STD(
            "non-standard amino acid",
            "Non-standard residue",
            FeatureCategory.AMINO_ACID_MODIFICATIONS),
    MOD_RES("modified residue", "Modified residue", FeatureCategory.AMINO_ACID_MODIFICATIONS),
    LIPID("lipid moiety-binding region", "Lipidation", FeatureCategory.AMINO_ACID_MODIFICATIONS),
    CARBOHYD("glycosylation site", "Glycosylation", FeatureCategory.AMINO_ACID_MODIFICATIONS),
    DISULFID("disulfide bond", "Disulfide bond", FeatureCategory.AMINO_ACID_MODIFICATIONS),
    CROSSLNK("cross-link", "Cross-link", FeatureCategory.AMINO_ACID_MODIFICATIONS),
    VAR_SEQ("splice variant", "Alternative sequence", FeatureCategory.NATURAL_VARIATIONS),
    VARIANT("sequence variant", "Natural variant", FeatureCategory.NATURAL_VARIATIONS),
    MUTAGEN("mutagenesis site", "Mutagenesis", FeatureCategory.EXPERIMENTAL_INFO),
    UNSURE("unsure residue", "Sequence uncertainty", FeatureCategory.EXPERIMENTAL_INFO),
    CONFLICT("sequence conflict", "Sequence conflict", FeatureCategory.EXPERIMENTAL_INFO),
    NON_CONS(
            "non-consecutive residues", "Non-adjacent residues", FeatureCategory.EXPERIMENTAL_INFO),
    NON_TER("non-terminal residue", "Non-terminal residue", FeatureCategory.EXPERIMENTAL_INFO),
    HELIX("helix", "Helix", FeatureCategory.SECONDARY_STRUCTURE),
    TURN("turn", "Turn", FeatureCategory.SECONDARY_STRUCTURE),
    STRAND("strand", "Beta strand", FeatureCategory.SECONDARY_STRUCTURE);

    private final String value;
    private final String displayName;
    private final FeatureCategory category;

    UniprotKBFeatureType(String value, String displayName, FeatureCategory category) {
        this.value = value;
        this.displayName = displayName;
        this.category = category;
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
}
