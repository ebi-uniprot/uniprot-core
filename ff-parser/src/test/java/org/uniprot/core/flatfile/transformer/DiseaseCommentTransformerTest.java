package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.Disease;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.DiseaseDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.List;

class DiseaseCommentTransformerTest {
    private final DiseaseCommentTransformer transformer = new DiseaseCommentTransformer();

    @Test
    void testNoEvidence1() {
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
        DiseaseComment comment = transformer.transform(CommentType.DISEASE, ccLineString);
        String description =
                "A complex disease characterized by malignant lesions arising from the inner wall"
                    + " of the large intestine (the colon) and the rectum. Genetic alterations are"
                    + " often associated with progression from premalignant lesion (adenoma) to"
                    + " invasive adenocarcinoma. Risk factors for cancer of the colon and rectum"
                    + " include colon polyps, long-standing ulcerative colitis, and genetic family"
                    + " history.";
        String note =
                "The gene represented in this "
                        + "entry is involved in disease pathogenesis. Another note";
        String diseaseId = "Colorectal cancer";
        String acronyn = "CRC";
        assertNotNull(comment);
        assertEquals(description, comment.getDisease().getDescription());
        assertEquals(0, comment.getDisease().getEvidences().size());
        assertEquals(1, comment.getNote().getTexts().size());
        assertEquals(note, comment.getNote().getTexts().get(0).getValue());
        assertEquals(0, comment.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(diseaseId, comment.getDisease().getDiseaseId());
        assertEquals(acronyn, comment.getDisease().getAcronym());
        assertEquals("114500", comment.getDisease().getDiseaseCrossReference().getId());
        assertEquals(
                DiseaseDatabase.MIM, comment.getDisease().getDiseaseCrossReference().getDatabase());
    }

    @Test
    void testEvidence1() {
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
        DiseaseComment comment = transformer.transform(CommentType.DISEASE, ccLineStringEvidence);
        String description =
                "A complex disease characterized by malignant lesions arising from the inner wall"
                    + " of the large intestine (the colon) and the rectum. Genetic alterations are"
                    + " often associated with progression from premalignant lesion (adenoma) to"
                    + " invasive adenocarcinoma. Risk factors for cancer of the colon and rectum"
                    + " include colon polyps, long-standing ulcerative colitis, and genetic family"
                    + " history.";
        String note1 =
                "The gene represented in this " + "entry is involved in disease pathogenesis";
        String note2 = "Another note";
        String diseaseId = "Colorectal cancer";
        String acronyn = "CRC";
        assertNotNull(comment);
        assertEquals(description, comment.getDisease().getDescription());
        assertEquals(3, comment.getDisease().getEvidences().size());
        assertEquals("ECO:0000303|Ref.6", comment.getDisease().getEvidences().get(1).getValue());
        assertEquals(2, comment.getNote().getTexts().size());
        assertEquals(note1, comment.getNote().getTexts().get(0).getValue());
        assertEquals(3, comment.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000313|PDB:3OW2",
                comment.getNote().getTexts().get(0).getEvidences().get(2).getValue());
        assertEquals(note2, comment.getNote().getTexts().get(1).getValue());
        assertEquals(2, comment.getNote().getTexts().get(1).getEvidences().size());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                comment.getNote().getTexts().get(1).getEvidences().get(0).getValue());
        assertEquals(diseaseId, comment.getDisease().getDiseaseId());
        assertEquals(acronyn, comment.getDisease().getAcronym());
        assertEquals("114500", comment.getDisease().getDiseaseCrossReference().getId());
        assertEquals(
                DiseaseDatabase.MIM, comment.getDisease().getDiseaseCrossReference().getDatabase());
    }

    @Test
    void testStructuredDisease1() {
        String diseaseId = "Deafness, autosomal recessive, 12";
        String diseaseAcronym = "DFNB12";
        String diseaseReferenceType = "MIM";
        String diseaseReferenceId = "601386";
        String diseaseDescription =
                "A form of non-syndromic sensorineural hearing loss. Sensorineural deafness"
                        + " results from damage to the neural receptors of the inner ear, the nerve"
                        + " pathways to the brain, or the area of the brain that receives sound"
                        + " information";
        String diseaseNote =
                "The disease is caused by mutations affecting the gene represented in this entry";

        String structuredDiseaseString =
                diseaseId
                        + " ("
                        + diseaseAcronym
                        + ") ["
                        + diseaseReferenceType
                        + ":"
                        + diseaseReferenceId
                        + "]: "
                        + diseaseDescription
                        + " Note="
                        + diseaseNote
                        + ".";

        DiseaseComment comment =
                transformer.transform(CommentType.DISEASE, structuredDiseaseString);

        assertEquals(comment.getCommentType(), CommentType.DISEASE);

        Disease disease = comment.getDisease();

        assertEquals(disease.getDiseaseId(), diseaseId);
        assertEquals(disease.getAcronym(), diseaseAcronym);
        assertEquals(disease.getDescription(), diseaseDescription);

        CrossReference<DiseaseDatabase> diseaseReference = disease.getDiseaseCrossReference();
        assertEquals(diseaseReference.getDatabase().getName(), diseaseReferenceType);
        assertEquals(diseaseReference.getId(), diseaseReferenceId);

        assertEquals(comment.getNote().getTexts().get(0).getValue(), diseaseNote);

        DiseaseComment diseaseCommentStructured =
                CommentTransformerHelper.transform(structuredDiseaseString, CommentType.DISEASE);
        assertEquals(comment, diseaseCommentStructured);
    }

    @Test
    void testStructuredDisease2() {
        String diseaseId = "Bartter syndrome 4B";
        String diseaseAcronym = "BS4B";
        String diseaseReferenceType = "MIM";
        String diseaseReferenceId = "613090";
        String diseaseDescription =
                "A digenic, "
                        + "recessive disorder characterized by impaired salt reabsorption in "
                        + "the thick ascending loop of Henle with pronounced salt wasting, "
                        + "hypokalemic metabolic alkalosis, and varying degrees of "
                        + "hypercalciuria etc";
        String diseaseNote =
                "The disease is caused by mutations "
                        + "affecting distinct genetic loci, including the gene represented in "
                        + "this entry. Loss-of-function of both CLCNKA and CLCNKB results in "
                        + "the disease phenotype (PubMed:18310267)";

        String structuredDiseaseString =
                diseaseId
                        + " ("
                        + diseaseAcronym
                        + ") ["
                        + diseaseReferenceType
                        + ":"
                        + diseaseReferenceId
                        + "]: "
                        + diseaseDescription
                        + " Note="
                        + diseaseNote
                        + ".";

        DiseaseComment comment =
                transformer.transform(CommentType.DISEASE, structuredDiseaseString);

        assertEquals(comment.getCommentType(), CommentType.DISEASE);

        Disease disease = comment.getDisease();

        assertEquals(disease.getDiseaseId(), diseaseId);
        assertEquals(disease.getAcronym(), diseaseAcronym);
        assertEquals(disease.getDescription(), diseaseDescription);

        CrossReference<DiseaseDatabase> diseaseReference = disease.getDiseaseCrossReference();
        assertEquals(diseaseReference.getDatabase().getName(), diseaseReferenceType);
        assertEquals(diseaseReference.getId(), diseaseReferenceId);

        assertEquals(comment.getNote().getTexts().get(0).getValue(), diseaseNote);

        DiseaseComment diseaseCommentStructured =
                CommentTransformerHelper.transform(structuredDiseaseString, CommentType.DISEASE);
        assertEquals(comment, diseaseCommentStructured);
    }

    @Test
    void testStructuredDisease2WithEvidence() {
        String diseaseId = "Bartter syndrome 4B";
        String diseaseAcronym = "BS4B";
        String diseaseReferenceType = "MIM";
        String diseaseReferenceId = "613090";
        String diseaseDescription =
                "A digenic, "
                        + "recessive disorder characterized by impaired salt reabsorption in "
                        + "the thick ascending loop of Henle with pronounced salt wasting, "
                        + "hypokalemic metabolic alkalosis, and varying degrees of "
                        + "hypercalciuria etc";
        String diseaseNote =
                "The disease is caused by mutations "
                        + "affecting distinct genetic loci, including the gene represented in "
                        + "this entry. Loss-of-function of both CLCNKA and CLCNKB results in "
                        + "the disease phenotype (PubMed:18310267)";

        String evidenceIdString = "ECO:0000269|PubMed:10433554";

        String structuredDiseaseString =
                diseaseId
                        + " ("
                        + diseaseAcronym
                        + ") ["
                        + diseaseReferenceType
                        + ":"
                        + diseaseReferenceId
                        + "]: "
                        + diseaseDescription
                        + " Note="
                        + diseaseNote
                        + "{"
                        + evidenceIdString
                        + "}.";

        DiseaseComment comment =
                transformer.transform(CommentType.DISEASE, structuredDiseaseString);

        assertEquals(comment.getCommentType(), CommentType.DISEASE);

        List<Evidence> evidenceIds = comment.getDisease().getEvidences();
        assertEquals(evidenceIds.size(), 0);

        Disease disease = comment.getDisease();

        assertEquals(disease.getDiseaseId(), diseaseId);
        assertEquals(disease.getAcronym(), diseaseAcronym);
        assertEquals(disease.getDescription(), diseaseDescription);

        CrossReference<DiseaseDatabase> diseaseReference = disease.getDiseaseCrossReference();
        assertEquals(diseaseReference.getDatabase().getName(), diseaseReferenceType);
        assertEquals(diseaseReference.getId(), diseaseReferenceId);

        assertEquals(comment.getNote().getTexts().get(0).getValue(), diseaseNote);
        evidenceIds = comment.getNote().getTexts().get(0).getEvidences();
        assertEquals(evidenceIds.size(), 1);

        Evidence evidenceId = evidenceIds.get(0);
        assertEquals(evidenceId.getValue(), evidenceIdString);

        DiseaseComment diseaseCommentStructured =
                CommentTransformerHelper.transform(structuredDiseaseString, CommentType.DISEASE);
        assertEquals(comment, diseaseCommentStructured);
    }

    @Test
    void testStructuredDisease3() {
        String diseaseId = "Herpes simplex encephalitis 2";
        String diseaseAcronym = "HSE2";
        String diseaseReferenceType = "MIM";
        String diseaseReferenceId = "613002";
        String diseaseDescription =
                "A rare "
                        + "complication of human herpesvirus 1 (HHV-1) infection, occurring "
                        + "in only a small minority of HHV-1 infected individuals. HSE is "
                        + "characterized by hemorrhagic necrosis of parts of the temporal and "
                        + "frontal lobes etc";
        String diseaseNote =
                "DiseaseEntry susceptibility is associated with "
                        + "variations affecting the gene represented in this entry. TLR3 "
                        + "mutations predispose otherwise healthy individuals to isolated "
                        + "herpes simplex encephalitis through a mechanism that involves "
                        + "impaired IFNs production and reduced immune defense against viral "
                        + "infection in the central nervous system";

        String structuredDiseaseString =
                diseaseId
                        + " ("
                        + diseaseAcronym
                        + ") ["
                        + diseaseReferenceType
                        + ":"
                        + diseaseReferenceId
                        + "]: "
                        + diseaseDescription
                        + " Note="
                        + diseaseNote
                        + ".";

        DiseaseComment comment =
                transformer.transform(CommentType.DISEASE, structuredDiseaseString);

        assertEquals(comment.getCommentType(), CommentType.DISEASE);

        Disease disease = comment.getDisease();

        assertEquals(disease.getDiseaseId(), diseaseId);
        assertEquals(disease.getAcronym(), diseaseAcronym);
        assertEquals(disease.getDescription(), diseaseDescription);

        CrossReference<DiseaseDatabase> diseaseReference = disease.getDiseaseCrossReference();
        assertEquals(diseaseReference.getDatabase().getName(), diseaseReferenceType);
        assertEquals(diseaseReference.getId(), diseaseReferenceId);

        assertEquals(comment.getNote().getTexts().get(0).getValue(), diseaseNote);
        DiseaseComment diseaseCommentStructured =
                CommentTransformerHelper.transform(structuredDiseaseString, CommentType.DISEASE);
        assertEquals(comment, diseaseCommentStructured);
    }

    @Test
    void testFailed() {
        String val =
                "DISEASE: Juvenile polyposis/hereditary hemorrhagic telangiectasia syndrome"
                    + " (JP/HHT) [MIM:175050]: JP/HHT syndrome phenotype consists of the"
                    + " coexistence of juvenile polyposis (JIP) and hereditary hemorrhagic"
                    + " telangiectasia (HHT) [MIM:187300] in a single individual. JIP and HHT are"
                    + " autosomal dominant disorders with distinct and non-overlapping clinical"
                    + " features. The former, an inherited gastrointestinal malignancy"
                    + " predisposition, is caused by mutations in SMAD4 or BMPR1A, and the latter"
                    + " is a vascular malformation disorder caused by mutations in ENG or ACVRL1."
                    + " All four genes encode proteins involved in the"
                    + " transforming-growth-factor-signaling pathway. Although there are reports"
                    + " of patients and families with phenotypes of both disorders combined, the"
                    + " genetic etiology of this association is unknown."
                    + " {ECO:0000269|PubMed:15031030}. Note=The disease is caused by mutations"
                    + " affecting the gene represented in this entry.";
        DiseaseComment comment = transformer.transform(CommentType.DISEASE, val);
        Disease disease = comment.getDisease();
        String diseaseId = "Juvenile polyposis/hereditary hemorrhagic telangiectasia syndrome";
        String diseaseAcronym = "JP/HHT";
        String diseaseReferenceType = "MIM";
        String diseaseReferenceId = "175050";
        String diseaseDescription =
                "JP/HHT syndrome phenotype consists of the coexistence of juvenile polyposis (JIP)"
                    + " and hereditary hemorrhagic telangiectasia (HHT) [MIM:187300] in a single"
                    + " individual. JIP and HHT are autosomal dominant disorders with distinct and"
                    + " non-overlapping clinical features. The former, an inherited"
                    + " gastrointestinal malignancy predisposition, is caused by mutations in"
                    + " SMAD4 or BMPR1A, and the latter is a vascular malformation disorder caused"
                    + " by mutations in ENG or ACVRL1. All four genes encode proteins involved in"
                    + " the transforming-growth-factor-signaling pathway. Although there are"
                    + " reports of patients and families with phenotypes of both disorders"
                    + " combined, the genetic etiology of this association is unknown.";
        String diseaseNote =
                "The disease is caused by mutations affecting the gene represented in this entry";
        assertEquals(disease.getDiseaseId(), diseaseId);
        assertEquals(disease.getAcronym(), diseaseAcronym);
        assertEquals(disease.getDescription(), diseaseDescription);

        CrossReference<DiseaseDatabase> diseaseReference = disease.getDiseaseCrossReference();
        assertEquals(diseaseReference.getDatabase().getName(), diseaseReferenceType);
        assertEquals(diseaseReference.getId(), diseaseReferenceId);

        assertEquals(comment.getNote().getTexts().get(0).getValue(), diseaseNote);
    }
}
