package org.uniprot.core.parser.tsv.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.parser.tsv.uniprot.comment.DiseaseMap;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.DiseaseComment;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DiseaseMapTest {

    @Test
    void testNoteOnlyDiseaseMapping() {
        String diseaseLine = "CC   -!- DISEASE: Note=Lysosomal acid phosphatase has been shown to be\n" +
                "CC       deficient in cultured fibroblasts from patients manifesting\n" +
                "CC       intermittent vomiting, hypotonia, lethargy, opisthotonos, terminal\n" +
                "CC       bleeding and death in early infancy. {ECO:0000269|PubMed:5410815}.";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(diseaseLine);

        List<DiseaseComment> diseaseComments = entry.getCommentByType(CommentType.DISEASE);
        assertNotNull(entry);
        DiseaseMap diseaseMap = new DiseaseMap(diseaseComments);
        Map<String,String> mappedDisease = diseaseMap.attributeValues();
        assertNotNull(mappedDisease);
        String value = mappedDisease.get("cc:disease");
        String expectedValue = "DISEASE: Note=Lysosomal acid phosphatase has been shown to be deficient in " +
                "cultured fibroblasts from patients manifesting intermittent vomiting, hypotonia, lethargy, " +
                "opisthotonos, terminal bleeding and death in early infancy. {ECO:0000269|PubMed:5410815}.";
        assertEquals(expectedValue,value);
    }



    @Test
    void testFullDiseaseMapping() {
        String diseaseLine = "CC   -!- DISEASE: Hypophosphatemic rickets, X-linked dominant (XLHR)\n" +
                "CC       [MIM:307800]: A disorder characterized by impaired phosphate\n" +
                "CC       uptake in the kidney, which is likely to be caused by abnormal\n" +
                "CC       regulation of sodium phosphate cotransport in the proximal\n" +
                "CC       tubules. Clinical manifestations include skeletal deformities,\n" +
                "CC       growth failure, craniosynostosis, paravertebral calcifications,\n" +
                "CC       pseudofractures in lower extremities, and muscular hypotonia with\n" +
                "CC       onset in early childhood. X-linked hypophosphatemic rickets is the\n" +
                "CC       most common form of hypophosphatemia with an incidence of 1 in\n" +
                "CC       20000. {ECO:0000269|PubMed:10439971, ECO:0000269|PubMed:10737991,\n" +
                "CC       ECO:0000269|PubMed:11004247, ECO:0000269|PubMed:9097956,\n" +
                "CC       ECO:0000269|PubMed:9106524, ECO:0000269|PubMed:9199930,\n" +
                "CC       ECO:0000269|PubMed:9768646, ECO:0000269|PubMed:9768674}. Note=The\n" +
                "CC       disease is caused by mutations affecting the gene represented in\n" +
                "CC       this entry.";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(diseaseLine);

        List<DiseaseComment> diseaseComments = entry.getCommentByType(CommentType.DISEASE);
        assertNotNull(entry);
        DiseaseMap diseaseMap = new DiseaseMap(diseaseComments);
        Map<String,String> mappedDisease = diseaseMap.attributeValues();
        assertNotNull(mappedDisease);
        String value = mappedDisease.get("cc:disease");
        String expectedValue = "DISEASE: Hypophosphatemic rickets, X-linked dominant (XLHR) [MIM:307800]: " +
                "A disorder characterized by impaired phosphate uptake in the kidney, which is likely to be caused by " +
                "abnormal regulation of sodium phosphate cotransport in the proximal tubules. Clinical " +
                "manifestations include skeletal deformities, growth failure, craniosynostosis, paravertebral " +
                "calcifications, pseudofractures in lower extremities, and muscular hypotonia with onset in early " +
                "childhood. X-linked hypophosphatemic rickets is the most common form of hypophosphatemia with " +
                "an incidence of 1 in 20000. {ECO:0000269|PubMed:10439971, ECO:0000269|PubMed:10737991, " +
                "ECO:0000269|PubMed:11004247, ECO:0000269|PubMed:9097956, ECO:0000269|PubMed:9106524, " +
                "ECO:0000269|PubMed:9199930, ECO:0000269|PubMed:9768646, ECO:0000269|PubMed:9768674}. " +
                "Note=The disease is caused by mutations affecting the gene represented in this entry.";
        assertEquals(expectedValue,value);
    }

    @Test
    void testManyDiseasesMapping() {
        String diseaseLine = "CC   -!- DISEASE: Note=Phosphoribosyl pyrophosphate synthetase I deficiency\n" +
                "CC       is a rare condition caused by mutations in PRPS1 that lead to\n" +
                "CC       variable disease phenotypes including optic atrophy, retinitis\n" +
                "CC       pigmentosa, ataxia, peripheral neuropathy and hearing loss.\n" +
                "CC       {ECO:0000269|PubMed:25491489}.\n" +
                "CC   -!- DISEASE: Phosphoribosylpyrophosphate synthetase superactivity\n" +
                "CC       (PRPS1 superactivity) [MIM:300661]: Familial disorder\n" +
                "CC       characterized by excessive purine production, gout and uric acid\n" +
                "CC       urolithiasis. {ECO:0000269|PubMed:7593598, ECO:0000269|Ref.12}.\n" +
                "CC       Note=The disease is caused by mutations affecting the gene\n" +
                "CC       represented in this entry.";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(diseaseLine);

        List<DiseaseComment> diseaseComments = entry.getCommentByType(CommentType.DISEASE);
        assertNotNull(entry);
        DiseaseMap diseaseMap = new DiseaseMap(diseaseComments);
        Map<String,String> mappedDisease = diseaseMap.attributeValues();
        assertNotNull(mappedDisease);
        String value = mappedDisease.get("cc:disease");
        String expectedValue = "DISEASE: Note=Phosphoribosyl pyrophosphate synthetase I deficiency is a rare condition " +
                "caused by mutations in PRPS1 that lead to variable disease phenotypes including optic atrophy, " +
                "retinitis pigmentosa, ataxia, peripheral neuropathy and hearing loss. {ECO:0000269|PubMed:25491489}.; " +
                "DISEASE: Phosphoribosylpyrophosphate synthetase superactivity (PRPS1 superactivity) [MIM:300661]: " +
                "Familial disorder characterized by excessive purine production, gout and uric acid urolithiasis. " +
                "{ECO:0000269|PubMed:7593598, ECO:0000269|Ref.12}. " +
                "Note=The disease is caused by mutations affecting the gene represented in this entry.";
        assertEquals(expectedValue,value);
    }


}