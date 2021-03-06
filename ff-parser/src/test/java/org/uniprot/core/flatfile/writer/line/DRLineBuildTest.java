package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.dr.DRLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

class DRLineBuildTest {
    private DRLineBuilder builder = new DRLineBuilder();

    @Test
    void testMint() {
        String drLine =
                "DR   EMBL; U12141; AAA99664.1; -; Genomic_DNA.\n" + "DR   MINT; MINT-1356407; -.";
        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createUniProtDBCrossReference("EMBL", "U12141", "AAA99664.1", "-", "Genomic_DNA"));
        xrefs.add(createUniProtDBCrossReference("MINT", "MINT-1356407", "-"));
        FFLine ffLine = builder.build(xrefs);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println("\n");
        System.out.println(drLine);
        assertEquals(drLine, resultString);
    }

    @Test
    void test1() {

        String drLine =
                "DR   EMBL; U12141; AAA99664.1; -; Genomic_DNA.\n"
                        + "DR   EMBL; U62941; AAB49937.1; -; Genomic_DNA.\n"
                        + "DR   EMBL; X94547; CAA64234.1; -; Genomic_DNA.\n"
                        + "DR   EMBL; Z71324; CAA95916.1; -; Genomic_DNA.\n"
                        + "DR   EMBL; BK006947; DAA10497.1; -; Genomic_DNA.\n"
                        + "DR   PIR; S61096; S61096.\n"
                        + "DR   RefSeq; NP_014350.1; NM_001182887.1.\n"
                        + "DR   DIP; DIP-7438N; -.\n"
                        + "DR   IntAct; P53954; 15.\n"
                        + "DR   MINT; MINT-1356407; -.\n"
                        + "DR   STRING; P53954; -.\n"
                        + "DR   CAZy; GT4; Glycosyltransferase Family 4.\n"
                        + "DR   EnsemblFungi; YNL048W; YNL048W; YNL048W.\n"
                        + "DR   GeneID; 855679; -.\n"
                        + "DR   KEGG; sce:YNL048W; -.\n"
                        + "DR   SGD; S000004993; ALG11.\n"
                        + "DR   eggNOG; fuNOG07615; -.\n"
                        + "DR   GeneTree; EFGT00050000003720; -.\n"
                        + "DR   HOGENOM; HBG525304; -.\n"
                        + "DR   OMA; VVEYMAS; -.\n"
                        + "DR   OrthoDB; EOG4NS6M8; -.\n"
                        + "DR   PhylomeDB; P53954; -.\n"
                        + "DR   BioCyc; MetaCyc:MONOMER-7187; -.\n"
                        +
                        // "DR NextBio; 979975; -.\n" +
                        "DR   ExpressionAtlas; P53954; -.\n"
                        +
                        // "DR Genevestigator; P53954; -.\n" +
                        // "DR GermOnline; YNL048W; Saccharomyces cerevisiae.\n" +
                        "DR   GO; GO:0005789; C:endoplasmic reticulum membrane; IDA:SGD.\n"
                        + "DR   GO; GO:0016021; C:integral to membrane; IEA:UniProtKB-KW.\n"
                        + "DR   GO; GO:0000026; F:alpha-1,2-mannosyltransferase activity; IDA:SGD.\n"
                        + "DR   GO; GO:0005515; F:protein binding; IPI:IntAct.\n"
                        + "DR   GO; GO:0006490; P:oligosaccharide-lipid intermediate metabolic"
                        + " process; IDA:SGD.\n"
                        + "DR   InterPro; IPR001296; Glyco_trans_1.\n"
                        + "DR   Pfam; PF00534; Glycos_transf_1; 1.";
        // "DR CYGD; YNL048w; -.\n" +
        //			"DR   NMPDR; fig|4932.3.peg.5426; -.";
        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createUniProtDBCrossReference("EMBL", "U12141", "AAA99664.1", "-", "Genomic_DNA"));
        xrefs.add(
                createUniProtDBCrossReference("EMBL", "U62941", "AAB49937.1", "-", "Genomic_DNA"));
        xrefs.add(
                createUniProtDBCrossReference("EMBL", "X94547", "CAA64234.1", "-", "Genomic_DNA"));
        xrefs.add(
                createUniProtDBCrossReference("EMBL", "Z71324", "CAA95916.1", "-", "Genomic_DNA"));
        xrefs.add(
                createUniProtDBCrossReference(
                        "EMBL", "BK006947", "DAA10497.1", "-", "Genomic_DNA"));
        xrefs.add(createUniProtDBCrossReference("PIR", "S61096", "S61096"));
        xrefs.add(createUniProtDBCrossReference("RefSeq", "NP_014350.1", "NM_001182887.1"));
        xrefs.add(createUniProtDBCrossReference("DIP", "DIP-7438N", "-"));
        xrefs.add(createUniProtDBCrossReference("IntAct", "P53954", "15"));
        xrefs.add(createUniProtDBCrossReference("MINT", "MINT-1356407", "-"));
        xrefs.add(createUniProtDBCrossReference("STRING", "P53954", "-"));
        xrefs.add(createUniProtDBCrossReference("CAZy", "GT4", "Glycosyltransferase Family 4"));
        xrefs.add(createUniProtDBCrossReference("EnsemblFungi", "YNL048W", "YNL048W", "YNL048W"));
        xrefs.add(createUniProtDBCrossReference("GeneID", "855679", "-"));
        xrefs.add(createUniProtDBCrossReference("KEGG", "sce:YNL048W", "-"));
        xrefs.add(createUniProtDBCrossReference("SGD", "S000004993", "ALG11"));
        xrefs.add(createUniProtDBCrossReference("eggNOG", "fuNOG07615", "-"));
        xrefs.add(createUniProtDBCrossReference("GeneTree", "EFGT00050000003720", "-"));
        xrefs.add(createUniProtDBCrossReference("HOGENOM", "HBG525304", "-"));
        xrefs.add(createUniProtDBCrossReference("OMA", "VVEYMAS", "-"));
        xrefs.add(createUniProtDBCrossReference("OrthoDB", "EOG4NS6M8", "-"));
        xrefs.add(createUniProtDBCrossReference("PhylomeDB", "P53954", "-"));
        xrefs.add(createUniProtDBCrossReference("BioCyc", "MetaCyc:MONOMER-7187", "-"));

        xrefs.add(createUniProtDBCrossReference("ExpressionAtlas", "P53954", "-"));
        xrefs.add(
                createUniProtDBCrossReference(
                        "GO", "GO:0005789", "C:endoplasmic reticulum membrane", "IDA:SGD"));

        xrefs.add(
                createUniProtDBCrossReference(
                        "GO", "GO:0016021", "C:integral to membrane", "IEA:UniProtKB-KW"));
        xrefs.add(
                createUniProtDBCrossReference(
                        "GO", "GO:0000026", "F:alpha-1,2-mannosyltransferase activity", "IDA:SGD"));
        xrefs.add(
                createUniProtDBCrossReference(
                        "GO", "GO:0005515", "F:protein binding", "IPI:IntAct"));
        xrefs.add(
                createUniProtDBCrossReference(
                        "GO",
                        "GO:0006490",
                        "P:oligosaccharide-lipid intermediate metabolic process",
                        "IDA:SGD"));
        xrefs.add(createUniProtDBCrossReference("InterPro", "IPR001296", "Glyco_trans_1"));

        xrefs.add(createUniProtDBCrossReference("Pfam", "PF00534", "Glycos_transf_1", "1"));
        //	xrefs.add(createUniProtDBCrossReference("NMPDR", "fig|4932.3.peg.5426", "-"));

        FFLine ffLine = builder.build(xrefs);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println("\n");
        System.out.println(drLine);
        assertEquals(drLine, resultString);
    }

    @Test
    void testWithIsoform() {

        String drLine =
                "DR   Gene3D; G3DSA:3.20.20.110; RuBisCO_large; 1.\n"
                        + "DR   Gene3D; G3DSA:3.30.70.150; RuBisCO_large; 1.\n"
                        + "DR   HAMAP; MF_03000; private_MF_03000; 1. [P21235-3]\n"
                        + "DR   InterPro; IPR000685; RuBisCO_lsu_C.\n"
                        + "DR   InterPro; IPR017443; RuBisCO_lsu_fd_N.\n"
                        + "DR   Pfam; PF00016; RuBisCO_large; 1.\n"
                        + "DR   Pfam; PF02788; RuBisCO_large_N; 1.\n"
                        + "DR   PROSITE; PS00157; RUBISCO_LARGE; 1. [P21235-2]";

        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createUniProtDBCrossReference("Gene3D", "G3DSA:3.20.20.110", "RuBisCO_large", "1"));
        xrefs.add(
                createUniProtDBCrossReference("Gene3D", "G3DSA:3.30.70.150", "RuBisCO_large", "1"));
        xrefs.add(
                createUniProtDBCrossReference(
                        "HAMAP", "MF_03000", "private_MF_03000", "1", null, "P21235-3"));

        xrefs.add(createUniProtDBCrossReference("InterPro", "IPR000685", "RuBisCO_lsu_C"));
        xrefs.add(createUniProtDBCrossReference("InterPro", "IPR017443", "RuBisCO_lsu_fd_N"));
        xrefs.add(createUniProtDBCrossReference("Pfam", "PF00016", "RuBisCO_large", "1"));
        xrefs.add(createUniProtDBCrossReference("Pfam", "PF02788", "RuBisCO_large_N", "1"));
        xrefs.add(
                createUniProtDBCrossReference(
                        "PROSITE", "PS00157", "RUBISCO_LARGE", "1", null, "P21235-2"));
        FFLine ffLine = builder.build(xrefs);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println("\n");
        System.out.println(drLine);
        assertEquals(drLine, resultString);
    }

    private UniProtKBCrossReference createUniProtDBCrossReference(
            String type, String id, String description) {
        return createUniProtDBCrossReference(type, id, description, null);
    }

    private UniProtKBCrossReference createUniProtDBCrossReference(
            String type, String id, String description, String thirdAttribute) {
        return createUniProtDBCrossReference(type, id, description, thirdAttribute, null);
    }

    private UniProtKBCrossReference createUniProtDBCrossReference(
            String type,
            String id,
            String description,
            String thirdAttribute,
            String fourthAttribute) {
        return createUniProtDBCrossReference(
                type, id, description, thirdAttribute, fourthAttribute, null);
    }

    private UniProtKBCrossReference createUniProtDBCrossReference(
            String databaseName,
            String id,
            String description,
            String thirdAttribute,
            String fourthAttribute,
            String isoformId) {

        return createUniProtDBCrossReference(
                databaseName,
                id,
                description,
                thirdAttribute,
                fourthAttribute,
                isoformId,
                Collections.emptyList());
    }

    private UniProtKBCrossReference createUniProtDBCrossReference(
            String databaseName,
            String id,
            String description,
            String thirdAttribute,
            String fourthAttribute,
            String isoformId,
            List<Evidence> evidences) {
        UniProtKBDatabase opType = new UniProtKBDatabaseImpl(databaseName);
        return new UniProtCrossReferenceBuilder()
                .database(opType)
                .id(id)
                .isoformId(isoformId)
                .evidencesSet(evidences)
                .propertiesAdd(opType.getUniProtDatabaseAttribute(0), description)
                .propertiesAdd(opType.getUniProtDatabaseAttribute(1), thirdAttribute)
                .propertiesAdd(opType.getUniProtDatabaseAttribute(2), fourthAttribute)
                .build();
    }
}
