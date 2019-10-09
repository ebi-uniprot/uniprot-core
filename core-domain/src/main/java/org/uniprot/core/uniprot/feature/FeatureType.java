package org.uniprot.core.uniprot.feature;


import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum FeatureType implements EnumDisplay<FeatureType> {
    /**
     * Enumerates all features types in UniProt. Important!!! The order which is here must the order
     * existing in the file cc_ord which is placed in /ebi/sp/misc1/pc/sprot/various/ft_ord
     */
    INIT_MET("initiator methionine", "initiator methionine", FeatureCategory.MOLECULE_PROCESSING),
    SIGNAL("signal peptide", "signal peptide", FeatureCategory.MOLECULE_PROCESSING),
    PROPEP("propeptide", "propeptide", FeatureCategory.MOLECULE_PROCESSING),
    TRANSIT("transit peptide", "transit peptide", FeatureCategory.MOLECULE_PROCESSING),
    CHAIN("chain", "chain", FeatureCategory.MOLECULE_PROCESSING),
    PEPTIDE("peptide", "peptide", FeatureCategory.MOLECULE_PROCESSING),
    TOPO_DOM("topological domain", "topological domain", FeatureCategory.REGIONS),
    TRANSMEM("transmembrane region", "transmembrane region", FeatureCategory.REGIONS),
    INTRAMEM("intramembrane region", "intramembrane region", FeatureCategory.REGIONS),
    DOMAIN("domain", "Other domain of interest", FeatureCategory.REGIONS),
    REPEAT("repeat", "repeat", FeatureCategory.REGIONS),
    CA_BIND("calcium-binding region", "calcium-binding region", FeatureCategory.REGIONS),
    ZN_FING("zinc finger region", "zinc finger region", FeatureCategory.REGIONS),
    DNA_BIND("DNA-binding region", "DNA-binding region", FeatureCategory.REGIONS),
    NP_BIND(
            "nucleotide phosphate-binding region",
            "Nucleotide-binding region",
            FeatureCategory.REGIONS),
    REGION("region of interest", "region of interest", FeatureCategory.REGIONS),
    COILED("coiled-coil region", "coiled-coil region", FeatureCategory.REGIONS),
    MOTIF("short sequence motif", "short sequence motif", FeatureCategory.REGIONS),
    COMPBIAS(
            "compositionally biased region",
            "compositionally biased region",
            FeatureCategory.REGIONS),
    ACT_SITE("active site", "active site", FeatureCategory.SITES),
    METAL("metal ion-binding site", "metal ion-binding site", FeatureCategory.SITES),
    BINDING("binding site", "Other binding site", FeatureCategory.SITES),
    SITE("site", "Other site of interest", FeatureCategory.SITES),
    NON_STD(
            "non-standard amino acid",
            "non-standard amino acid",
            FeatureCategory.AMINO_ACID_MODIFICATIONS),
    MOD_RES(
            "modified residue",
            "Post-translationally modified residue",
            FeatureCategory.AMINO_ACID_MODIFICATIONS),
    LIPID(
            "lipid moiety-binding region",
            "lipid moiety-binding region",
            FeatureCategory.AMINO_ACID_MODIFICATIONS),
    CARBOHYD("glycosylation site", "glycosylation site", FeatureCategory.AMINO_ACID_MODIFICATIONS),
    DISULFID("disulfide bond", "disulfide bond", FeatureCategory.AMINO_ACID_MODIFICATIONS),
    CROSSLNK("cross-link", "cross-link", FeatureCategory.AMINO_ACID_MODIFICATIONS),
    VAR_SEQ("splice variant", "splice variant", FeatureCategory.NATURAL_VARIATIONS),
    VARIANT("sequence variant", "Sequence variation", FeatureCategory.NATURAL_VARIATIONS),
    MUTAGEN("mutagenesis site", "mutagenesis site", FeatureCategory.EXPERIMENTAL_INFO),
    UNSURE("unsure residue", "Uncertainty in sequence", FeatureCategory.EXPERIMENTAL_INFO),
    CONFLICT("sequence conflict", "sequence conflict", FeatureCategory.EXPERIMENTAL_INFO),
    NON_CONS(
            "non-consecutive residues",
            "non-consecutive residues",
            FeatureCategory.EXPERIMENTAL_INFO),
    NON_TER("non-terminal residue", "non-terminal residue", FeatureCategory.EXPERIMENTAL_INFO),
    HELIX("helix", "helix", FeatureCategory.SECONDARY_STRUCTURE),
    TURN("turn", "turn", FeatureCategory.SECONDARY_STRUCTURE),
    STRAND("strand", "strand", FeatureCategory.SECONDARY_STRUCTURE);

    private final String value;
    private final String displayName;
    private final FeatureCategory category;

    FeatureType(String value, String displayName, FeatureCategory category) {
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

    public @Nonnull static FeatureType typeOf(String value) {
        for (FeatureType featureType : FeatureType.values()) {
            if (featureType.name().equalsIgnoreCase(value)) {
                return featureType;
            }
        }
        for (FeatureType featureType : FeatureType.values()) {
            if (featureType.getValue().equalsIgnoreCase(value)) {
                return featureType;
            }
        }
        throw new IllegalArgumentException(
                "the fetaure with the description " + value + " doesn't exist");
    }

    @Override
    public @Nonnull String toDisplayName() {
        return displayName;
    }
}
