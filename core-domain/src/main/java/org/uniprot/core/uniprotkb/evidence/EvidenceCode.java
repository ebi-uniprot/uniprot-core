package org.uniprot.core.uniprotkb.evidence;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum EvidenceCode implements EnumDisplay {
    @Deprecated
    ECO_0000213(
            "ECO:0000213",
            "Combinatorial",
            "A PDB entry",
            Collections.singletonList("Combined sources"),
            EnumSet.of(Category.AUTOMATIC)),
    @Deprecated
    ECO_0000244(
            "ECO:0000244",
            "Combinatorial",
            "a PDB entry or literature reference (for large-scale proteomics publications)",
            Collections.singletonList("Combined sources"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000245(
            "ECO:0000245",
            "Combinatorial",
            "automatically integrated combinatorial evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000247(
            "ECO:0000247",
            "Sequence alignment",
            "Sequence alignment evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000250(
            "ECO:0000250",
            "Sequence similarity",
            "Another UniProtKB/Swiss-Prot entry",
            Collections.singletonList("By similarity"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000255(
            "ECO:0000255",
            "Sequence model",
            "A rule (optional)",
            Arrays.asList(
                    "Sequence analysis",
                    "UniRule annotation",
                    "Prosite ProRule annotation",
                    "SAAS annotation"),
            EnumSet.of(Category.MANUAL)),
    ECO_0000256(
            "ECO:0000256",
            "Sequence model",
            "a sequence analysis program or an automatic annotation rule",
            Arrays.asList(
                    "Sequence analysis (SIGNAL, TRANSMEM, COILED)",
                    "UniRule annotation (RuleBase, HAMAP or PIRNR/PIRSR)",
                    "SAAS annotation"),
            EnumSet.of(Category.AUTOMATIC)),
    ECO_0000259(
            "ECO:0000259",
            "Sequence motif match (InterPro)",
            "An entry in an InterPro member database",
            Collections.singletonList("InterPro annotation"),
            EnumSet.of(Category.AUTOMATIC)),

    ECO_0000266(
            "ECO:0000266",
            "Sequence orthology",
            "Sequence orthology evidence used in manual assertion",
            Collections.singletonList("Sequence orthology"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000269(
            "ECO:0000269",
            "Experimental",
            "Literature reference",
            Collections.singletonList(
                    "x Publication(s) with 'Manual assertion based on experiment in'"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0000270(
            "ECO:0000270",
            "Expression pattern",
            "expression pattern evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0000303(
            "ECO:0000303",
            "Non-traceable author statement",
            "Literature reference",
            Collections.singletonList(
                    "x Publication(s) with 'Manual assertion based on opinion in'"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0000304(
            "ECO:0000304",
            "Curator inference",
            "Literature reference",
            Arrays.asList(
                    "Curated", "x Publication(s) with 'Manual assertion inferred by curator from'"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000305(
            "ECO:0000305",
            "Curator inference",
            "Literature reference (optional)",
            Arrays.asList(
                    "Curated", "x Publication(s) with 'Manual assertion inferred by curator from'"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000307(
            "ECO:0000307",
            "Manual no evidence",
            "no evidence data found used in manual assertion",
            Arrays.asList("Manual"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000312(
            "ECO:0000312",
            "Imported information",
            "an entry from nucleotide sequence databases, PDB, model organism databases",
            Collections.singletonList("Imported"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000313(
            "ECO:0000313",
            "Imported information",
            "An entry from nucleotide sequence databases (EMBL, Ensembl),"
                    + " PDB, model organism databases",
            Collections.singletonList("Imported"),
            EnumSet.of(Category.AUTOMATIC)),

    ECO_0000314(
            "ECO:0000314",
            "Direct assay",
            "Direct assay evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),
    ECO_0000315(
            "ECO:0000315",
            "Mutant phenotype",
            "Mutant phenotype evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),
    ECO_0000316(
            "ECO:0000316",
            "Genetic interaction",
            "Genetic interaction evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0000317(
            "ECO:0000317",
            "\"Genomic context",
            "Genomic context evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000318(
            "ECO:0000318",
            "Biological aspect of ancestor",
            "Biological aspect of ancestor evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000319(
            "ECO:0000319",
            "Biological aspect of descendant",
            "Biological aspect of descendant evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000320(
            "ECO:0000320",
            "Phylogenetic determination",
            "Phylogenetic determination of loss of key residues evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000321(
            "ECO:0000321",
            "Rapid divergenc",
            "Rapid divergence from ancestral sequence evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.MANUAL)),

    ECO_0000353(
            "ECO:0000353",
            "Physical interaction",
            "Physical interaction evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0000501(
            "ECO:0007669",
            "Physical interaction",
            "evidence used in automatic assertion",
            Collections.singletonList("Imported"),
            EnumSet.of(Category.AUTOMATIC)),

    ECO_0006056(
            "ECO:0006056",
            "High throughput",
            "High throughput evidence used in manual assertion",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0007001(
            "ECO:0007001",
            "High throughput mutant phenotypic",
            "A type of high throughput mutant phenotypic evidence that is used in a manual assertion.",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0007003(
            "ECO:0007003",
            "High throughput interaction phenotypic",
            "A type of high throughput genetic interaction phenotypic evidence that is used in a manual assertion.",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0007005(
            "ECO:0007005",
            "High throughput",
            "A type of high throughput evidence that is used in a manual assertion derived from the high throughput direct measurement of some aspect of a biological feature",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0007007(
            "ECO:0007007",
            "High throughput for gene expression",
            "A type of high throughput evidence that is used in a manual assertion derived from the high throughput characterization of gene expression.",
            Collections.singletonList("Manual"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),

    ECO_0007744(
            "ECO:0007744",
            "Combinatorial",
            "combinatorial computational and experimental evidence used in manual assertion",
            Collections.singletonList("Combined sources"),
            EnumSet.of(Category.MANUAL)), // replacing ECO_0000244 from 2021.2 release

    ECO_0007829(
            "ECO:0007829",
            "Combinatorial",
            "combinatorial computational and experimental evidence used in automatic assertion",
            Collections.singletonList("Combined sources"),
            EnumSet.of(Category.AUTOMATIC)), // replacing ECO_0000213

    ECO_0008006("ECO:0008006",
            "Deep learning",
            "deep learning method evidence used in automatic assertion",
            Collections.singletonList("Automatic Annotation"),
            EnumSet.of(Category.AUTOMATIC));

    private final String code;
    private final String name;
    private final String source;
    private final List<String> labels;
    private final EnumSet<Category> categories;

    EvidenceCode(
            String code,
            String name,
            String source,
            List<String> labels,
            EnumSet<Category> categories) {
        this.code = code;
        this.name = name;
        this.source = source;
        this.labels = labels;
        this.categories = categories;
    }

    public @Nonnull String getCode() {
        return code;
    }

    public @Nonnull String getSource() {
        return source;
    }

    public @Nonnull List<String> getLabels() {
        return labels;
    }

    public @Nonnull EnumSet<Category> getCategories() {
        return categories;
    }

    public enum Category {
        EXPERIMENTAL,
        MANUAL,
        AUTOMATIC;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnull String getDisplayName() {
        return getCode();
    }

    public @Nonnull String getCompareOn() {
        return getCode();
    }

    public static @Nonnull EvidenceCode typeOf(@Nonnull String code) {
        return EnumDisplay.typeOf(code, EvidenceCode.class);
    }
}
