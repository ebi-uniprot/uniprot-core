package org.uniprot.core.uniprot.evidence;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.uniprot.core.util.EnumDisplay;

public enum EvidenceCode implements EnumDisplay<EvidenceCode> {
    ECO_0000269(
            "ECO:0000269",
            "Experimental",
            "Literature reference",
            Arrays.asList("x Publication(s) with 'Manual assertion based on experiment in'"),
            EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL)),
    ECO_0000303(
            "ECO:0000303",
            "Non-traceable author statement",
            "Literature reference",
            Arrays.asList("x Publication(s) with 'Manual assertion based on opinion in'"),
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
            Arrays.asList("By similarity"),
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
            Arrays.asList("Combined sources"),
            EnumSet.of(Category.MANUAL)),
    ECO_0000312(
            "ECO:0000312",
            "Imported information",
            "an entry from nucleotide sequence databases, PDB, model organism databases",
            Arrays.asList("Imported"),
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
            Arrays.asList("Combined sources"),
            EnumSet.of(Category.AUTOMATIC)),
    ECO_0000313(
            "ECO:0000313",
            "Imported information",
            "An entry from nucleotide sequence databases (EMBL, Ensembl),"
                    + " PDB, model organism databases",
            Arrays.asList("Imported"),
            EnumSet.of(Category.AUTOMATIC)),
    ECO_0000259(
            "ECO:0000259",
            "Sequence motif match (InterPro)",
            "An entry in an InterPro member database",
            Arrays.asList("InterPro annotation"),
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

    public static EvidenceCode codeOf(String code) {
        for (EvidenceCode eCode : EvidenceCode.values()) {
            if (code.equals(eCode.getCode())) return eCode;
        }
        throw new IllegalArgumentException(code + " is not valid Evidence code");
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toDisplayName() {
        return getCode();
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public List<String> getLabels() {
        return labels;
    }

    public EnumSet<Category> getCategories() {
        return categories;
    }

    public enum Category {
        EXPERIMENTAL,
        MANUAL,
        AUTOMATIC
    }
}
