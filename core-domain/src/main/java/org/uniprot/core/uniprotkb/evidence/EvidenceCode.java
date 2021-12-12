package org.uniprot.core.uniprotkb.evidence;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum EvidenceCode implements EnumDisplay<EvidenceCode> {
    ECO_0000269(
            "ECO:0000269",
            "Experimental",
            "Literature reference",
            Collections.singletonList(
                    "x Publication(s) with 'Manual assertion based on experiment in'"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),
    ECO_0000303(
            "ECO:0000303",
            "Non-traceable author statement",
            "Literature reference",
            Collections.singletonList(
                    "x Publication(s) with 'Manual assertion based on opinion in'"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),
    ECO_0000305(
            "ECO:0000305",
            "Curator inference",
            "Literature reference (optional)",
            Arrays.asList(
                    "Curated", "x Publication(s) with 'Manual assertion inferred by curator from'"),
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
    ECO_0000244(
            "ECO:0000244",
            "Combinatorial",
            "a PDB entry or literature reference (for large-scale proteomics publications)",
            Collections.singletonList("Combined sources"),
            EnumSet.of(Category.MANUAL)),
    ECO_0000312(
            "ECO:0000312",
            "Imported information",
            "an entry from nucleotide sequence databases, PDB, model organism databases",
            Collections.singletonList("Imported"),
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
    ECO_0000213(
            "ECO:0000213",
            "Combinatorial",
            "A PDB entry",
            Collections.singletonList("Combined sources"),
            EnumSet.of(Category.AUTOMATIC)),
    ECO_0000313(
            "ECO:0000313",
            "Imported information",
            "An entry from nucleotide sequence databases (EMBL, Ensembl),"
                    + " PDB, model organism databases",
            Collections.singletonList("Imported"),
            EnumSet.of(Category.AUTOMATIC)),
    ECO_0000259(
            "ECO:0000259",
            "Sequence motif match (InterPro)",
            "An entry in an InterPro member database",
            Collections.singletonList("InterPro annotation"),
            EnumSet.of(Category.AUTOMATIC)),
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
            EnumSet.of(Category.AUTOMATIC)); // replacing ECO_0000213
    // from 2021.2 release

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

    public @Nonnull static EvidenceCode typeOf(@Nonnull String code) {
        for (EvidenceCode eCode : EvidenceCode.values()) {
            if (code.equals(eCode.getCode())) return eCode;
        }
        throw new IllegalArgumentException(code + " is not valid Evidence code");
    }

    public @Nonnull String getCode() {
        return code;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return getCode();
    }

    public @Nonnull String getName() {
        return name;
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
        AUTOMATIC
    }
}
