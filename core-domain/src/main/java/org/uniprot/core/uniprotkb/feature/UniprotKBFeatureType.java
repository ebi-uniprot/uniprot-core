package org.uniprot.core.uniprotkb.feature;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.FeatureType;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;

public enum UniprotKBFeatureType implements FeatureType {
    /**
     * Enumerates all features types in UniProt. Important!!! The order which is here must the order
     * existing in the file cc_ord which is placed in /ebi/sp/misc1/pc/sprot/various/ft_ord
     */
    INIT_MET("initiator methionine", "Initiator methionine", FeatureCategory.MOLECULE_PROCESSING, true),
    SIGNAL("signal peptide", "Signal", FeatureCategory.MOLECULE_PROCESSING, false),
    PROPEP("propeptide", "Propeptide", FeatureCategory.MOLECULE_PROCESSING, false),
    TRANSIT("transit peptide", "Transit peptide", FeatureCategory.MOLECULE_PROCESSING, false),
    CHAIN("chain", "Chain", FeatureCategory.MOLECULE_PROCESSING, false),
    PEPTIDE("peptide", "Peptide", FeatureCategory.MOLECULE_PROCESSING, false),
    TOPO_DOM("topological domain", "Topological domain", FeatureCategory.REGIONS, false),
    TRANSMEM("transmembrane region", "Transmembrane", FeatureCategory.REGIONS, false),
    INTRAMEM("intramembrane region", "Intramembrane", FeatureCategory.REGIONS, false),
    DOMAIN("domain", "Domain", FeatureCategory.REGIONS, false),
    REPEAT("repeat", "Repeat", FeatureCategory.REGIONS, false),
    @Deprecated
    CA_BIND("calcium-binding region", "Calcium binding", FeatureCategory.REGIONS, false),
    ZN_FING("zinc finger region", "Zinc finger", FeatureCategory.REGIONS, false),
    DNA_BIND("DNA-binding region", "DNA binding", FeatureCategory.REGIONS, false),
    @Deprecated
    NP_BIND("nucleotide phosphate-binding region", "Nucleotide binding", FeatureCategory.REGIONS, false),
    REGION("region of interest", "Region", FeatureCategory.REGIONS, true),
    COILED("coiled-coil region", "Coiled coil", FeatureCategory.REGIONS, false),
    MOTIF("short sequence motif", "Motif", FeatureCategory.REGIONS, false),
    COMPBIAS("compositionally biased region", "Compositional bias", FeatureCategory.REGIONS, false),
    ACT_SITE("active site", "Active site", FeatureCategory.SITES, true),
    @Deprecated
    METAL("metal ion-binding site", "Metal binding", FeatureCategory.SITES, true),
    BINDING("binding site", "Binding site", FeatureCategory.SITES, true),
    SITE("site", "Site", FeatureCategory.SITES, true),
    NON_STD(
            "non-standard amino acid",
            "Non-standard residue",
            FeatureCategory.AMINO_ACID_MODIFICATIONS, false),
    MOD_RES("modified residue", "Modified residue", FeatureCategory.AMINO_ACID_MODIFICATIONS, true),
    LIPID("lipid moiety-binding region", "Lipidation", FeatureCategory.AMINO_ACID_MODIFICATIONS, false),
    CARBOHYD("glycosylation site", "Glycosylation", FeatureCategory.AMINO_ACID_MODIFICATIONS, true),
    DISULFID("disulfide bond", "Disulfide bond", FeatureCategory.AMINO_ACID_MODIFICATIONS, true),
    CROSSLNK("cross-link", "Cross-link", FeatureCategory.AMINO_ACID_MODIFICATIONS, true),
    VAR_SEQ("splice variant", "Alternative sequence", FeatureCategory.NATURAL_VARIATIONS, false),
    VARIANT("sequence variant", "Natural variant", FeatureCategory.NATURAL_VARIATIONS, true),
    MUTAGEN("mutagenesis site", "Mutagenesis", FeatureCategory.EXPERIMENTAL_INFO, true),
    UNSURE("unsure residue", "Sequence uncertainty", FeatureCategory.EXPERIMENTAL_INFO, true),
    CONFLICT("sequence conflict", "Sequence conflict", FeatureCategory.EXPERIMENTAL_INFO, false),
    NON_CONS(
            "non-consecutive residues", "Non-adjacent residues", FeatureCategory.EXPERIMENTAL_INFO, false),
    NON_TER("non-terminal residue", "Non-terminal residue", FeatureCategory.EXPERIMENTAL_INFO, false),
    HELIX("helix", "Helix", FeatureCategory.SECONDARY_STRUCTURE, false),
    TURN("turn", "Turn", FeatureCategory.SECONDARY_STRUCTURE, false),
    STRAND("strand", "Beta strand", FeatureCategory.SECONDARY_STRUCTURE, false);

    private final String value;
    private final String displayName;
    private final FeatureCategory category;
    private final boolean addExperimental;

    UniprotKBFeatureType(String value, String displayName, FeatureCategory category, boolean addExperimental) {
        this.value = value;
        this.displayName = displayName;
        this.category = category;
        this.addExperimental = addExperimental;
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

    /**
     * Return if we can add implicit experimental evidence for this FeatureType.
     *
     * @return
     */
    public boolean isAddExperimental() {
        return addExperimental;
    }
}
