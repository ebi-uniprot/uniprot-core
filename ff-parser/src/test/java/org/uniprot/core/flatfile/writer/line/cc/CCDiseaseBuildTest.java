package org.uniprot.core.flatfile.writer.line.cc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.Disease;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.DiseaseDatabase;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseBuilder;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentBuilder;

class CCDiseaseBuildTest extends CCBuildTestAbstr {
    @Test
    void testNoEvidence() {
        String ccLine =
                ("CC   -!- DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease\n"
                        + "CC       characterized by malignant lesions arising from the inner wall of the\n"
                        + "CC       large intestine (the colon) and the rectum. Genetic alterations are\n"
                        + "CC       often associated with progression from premalignant lesion (adenoma) to\n"
                        + "CC       invasive adenocarcinoma. Risk factors for cancer of the colon and\n"
                        + "CC       rectum include colon polyps, long-standing ulcerative colitis, and\n"
                        + "CC       genetic family history. Note=The gene represented in this entry is\n"
                        + "CC       involved in disease pathogenesis.");

        String ccLineString =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "Note=The gene represented in this entry is involved in disease "
                        + "pathogenesis.");
        DiseaseCommentBuilder builder = new DiseaseCommentBuilder();
        List<String> evs = new ArrayList<>();
        String note = "The gene represented in this entry is involved in disease pathogenesis";
        List<String> noteEvs = new ArrayList<>();
        builder.note(buildNote(note, noteEvs));

        String diseaseId = "Colorectal cancer";
        String acronyn = "CRC";
        String description =
                "A complex disease characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history.";
        String refId = "114500";

        Disease disease =
                buildDisease(diseaseId, acronyn, description, DiseaseDatabase.MIM, refId, evs);

        builder.disease(disease);

        DiseaseComment comment = builder.build();

        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineString, comment);
    }

    @Test
    void testEvidence1() {
        String ccLine =
                ("CC   -!- DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease\n"
                        + "CC       characterized by malignant lesions arising from the inner wall of the\n"
                        + "CC       large intestine (the colon) and the rectum. Genetic alterations are\n"
                        + "CC       often associated with progression from premalignant lesion (adenoma) to\n"
                        + "CC       invasive adenocarcinoma. Risk factors for cancer of the colon and\n"
                        + "CC       rectum include colon polyps, long-standing ulcerative colitis, and\n"
                        + "CC       genetic family history. {ECO:0000269|PubMed:10433554,\n"
                        + "CC       ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1}. Note=The gene\n"
                        + "CC       represented in this entry is involved in disease pathogenesis.");

        String ccLineStringEvidence =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6, "
                        + "ECO:0000313|EMBL:BAG16761.1}. Note=The gene represented in this "
                        + "entry is involved in disease pathogenesis.");
        String ccLineString =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "Note=The gene represented in this "
                        + "entry is involved in disease pathogenesis.");
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        //	String ev4 ="ECO:0000313|PDB:3OW2";
        //	String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        evs.add(ev3);
        String note = "The gene represented in this entry is involved in disease pathogenesis";
        List<String> noteEvs = new ArrayList<>();
        DiseaseCommentBuilder builder = new DiseaseCommentBuilder();
        builder.note(buildNote(note, noteEvs));
        String diseaseId = "Colorectal cancer";
        String acronyn = "CRC";
        String description =
                "A complex disease characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history.";
        String refId = "114500";
        Disease disease =
                buildDisease(diseaseId, acronyn, description, DiseaseDatabase.MIM, refId, evs);
        builder.disease(disease);

        DiseaseComment comment = builder.build();

        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void testEvidence2() {
        String ccLine =
                ("CC   -!- DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease\n"
                        + "CC       characterized by malignant lesions arising from the inner wall of the\n"
                        + "CC       large intestine (the colon) and the rectum. Genetic alterations are\n"
                        + "CC       often associated with progression from premalignant lesion (adenoma) to\n"
                        + "CC       invasive adenocarcinoma. Risk factors for cancer of the colon and\n"
                        + "CC       rectum include colon polyps, long-standing ulcerative colitis, and\n"
                        + "CC       genetic family history. {ECO:0000269|PubMed:10433554,\n"
                        + "CC       ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1}. Note=The gene\n"
                        + "CC       represented in this entry is involved in disease pathogenesis.\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000303|Ref.6,\n"
                        + "CC       ECO:0000313|PDB:3OW2}.");
        String ccLineStringEvidence =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6, "
                        + "ECO:0000313|EMBL:BAG16761.1}. Note=The gene represented in this "
                        + "entry is involved in disease pathogenesis. {ECO:0000256|HAMAP-"
                        + "Rule:MF_00205, ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.");
        String ccLineString =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "Note=The gene represented in this "
                        + "entry is involved in disease pathogenesis.");
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        evs.add(ev3);
        String note = "The gene represented in this entry is involved in disease pathogenesis";
        List<String> noteEvs = new ArrayList<>();
        noteEvs.add(ev3);
        noteEvs.add(ev4);
        noteEvs.add(ev5);
        DiseaseCommentBuilder builder = new DiseaseCommentBuilder();
        builder.note(buildNote(note, noteEvs));
        String diseaseId = "Colorectal cancer";
        String acronyn = "CRC";
        String description =
                "A complex disease characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history.";
        String refId = "114500";
        Disease disease =
                buildDisease(diseaseId, acronyn, description, DiseaseDatabase.MIM, refId, evs);
        builder.disease(disease);

        DiseaseComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void testEvidence3() {
        String ccLine =
                ("CC   -!- DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease\n"
                        + "CC       characterized by malignant lesions arising from the inner wall of the\n"
                        + "CC       large intestine (the colon) and the rectum. Genetic alterations are\n"
                        + "CC       often associated with progression from premalignant lesion (adenoma) to\n"
                        + "CC       invasive adenocarcinoma. Risk factors for cancer of the colon and\n"
                        + "CC       rectum include colon polyps, long-standing ulcerative colitis, and\n"
                        + "CC       genetic family history. {ECO:0000269|PubMed:10433554,\n"
                        + "CC       ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1}. Note=The gene\n"
                        + "CC       represented in this entry is involved in disease pathogenesis.\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000303|Ref.6,\n"
                        + "CC       ECO:0000313|PDB:3OW2}. Another note. {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000303|Ref.6}.");
        String ccLineStringEvidence =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6, "
                        + "ECO:0000313|EMBL:BAG16761.1}. Note=The gene represented in this "
                        + "entry is involved in disease pathogenesis. {ECO:0000256|HAMAP-"
                        + "Rule:MF_00205, ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}. Another"
                        + " note. {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000303|Ref.6}.");
        String ccLineString =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "Note=The gene represented in this "
                        + "entry is involved in disease pathogenesis. Another note.");
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        evs.add(ev3);
        String note = "The gene represented in this entry is involved in disease pathogenesis";
        List<String> noteEvs = new ArrayList<>();
        noteEvs.add(ev3);
        noteEvs.add(ev4);
        noteEvs.add(ev5);
        DiseaseCommentBuilder builder = new DiseaseCommentBuilder();
        List<Map.Entry<String, List<String>>> notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, noteEvs));
        String note2 = "Another note";
        List<String> noteEvs2 = new ArrayList<>();
        noteEvs2.add(ev5);
        noteEvs2.add(ev3);
        notes.add(new AbstractMap.SimpleEntry<>(note2, noteEvs2));

        builder.note(buildNote(notes));

        String diseaseId = "Colorectal cancer";
        String acronyn = "CRC";
        String description =
                "A complex disease characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history.";
        String refId = "114500";
        Disease disease =
                buildDisease(diseaseId, acronyn, description, DiseaseDatabase.MIM, refId, evs);
        builder.disease(disease);

        DiseaseComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    private Disease buildDisease(
            String diseaseId,
            String acronym,
            String description,
            DiseaseDatabase type,
            String refId,
            List<String> evs) {
        DiseaseBuilder builder = new DiseaseBuilder();
        builder.diseaseId(diseaseId);
        builder.acronym(acronym);
        builder.description(description).evidencesSet(createEvidence(evs));
        builder.diseaseCrossReference(
                new CrossReferenceBuilder<DiseaseDatabase>().database(type).id(refId).build());
        return builder.build();
    }
}
