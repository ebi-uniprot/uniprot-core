package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author lgonzales
 * @since 07/10/2020
 */
public enum ExclusionReason implements EnumDisplay {
    DERIVED_FROM_ENVIRONMENTAL_SOURCE("derived from environmental source"),
    HIGH_CONTIG_L50("high contig L50"),
    MISSING_RRNA_GENES("missing rRNA genes"),
    DERIVED_FROM_SURVEILLANCE_PROJECT("derived from surveillance project"),
    MISSING_RIBOSOMAL_PROTEIN_GENES("missing ribosomal protein genes"),
    VALIDATION_ERRORS("validation errors"),
    MANY_FRAMESHIFTED_PROTEINS("many frameshifted proteins"),
    MISSING_TRNA_GENES("missing tRNA genes"),
    DERIVED_FROM_METAGENOME("derived from metagenome"),
    DERIVED_FROM_SINGLE_CELL("derived from single cell"),
    ABNORMAL_GENE_TO_SEQUENCE_RATIO_PARTIAL("abnormal gene to sequence ratio partial"),
    METAGENOME("metagenome"),
    UNTRUSTWORTHY_AS_TYPE("untrustworthy as type"),
    CHIMERIC("chimeric"),
    LOW_CONTIG_N50_CHIMERIC("low contig N50 chimeric"),
    UNVERIFIED_SOURCE_ORGANISM("unverified source organism"),
    MIXED_CULTURE("mixed culture"),
    MISASSEMBLED("misassembled"),
    MISASSEMBLED_HYBRID("misassembled hybrid"),
    GENOME_LENGTH_TOO_LARGE("genome length too large"),
    CONTAMINATED("contaminated"),
    GENOME_LENGTH_TOO_SMALL("genome length too small"),
    LOW_GENE_COUNT("low gene count"),
    LOW_QUALITY_SEQUENCE("low quality sequence"),
    FRAGMENTED_ASSEMBLY("fragmented assembly"),
    REFSEQ_ANNOTATION_FAILED("RefSeq annotation failed"),
    DERIVED_FROM_LARGE_SCALE_PROJECT("derived from large-scale project"),
    FROM_LARGE_MULTI_ISOLATE_PROJECT("from large multi-isolate project");

    private final String displayName;

    ExclusionReason(String displayName) {
        this.displayName = displayName;
    }

    @Nonnull
    @Override
    public String getName() {
        return displayName;
    }

    @Nonnull
    @Override
    public String getCompareOn() {
        return displayName;
    }

    @Nonnull
    @Override
    public String getDisplayName() {
        return displayName;
    }

    public static @Nonnull ExclusionReason typeOf(@Nonnull String displayName) {
        return EnumDisplay.typeOf(displayName, ExclusionReason.class);
    }
}
