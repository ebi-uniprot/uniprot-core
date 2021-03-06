package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.de.DeLineConverter;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;

class DeLineConverterTest {
    private DeLineConverter converter = new DeLineConverter();

    @Test
    void test1() {
        /*
        *     val deLines = """DE   RecName: Full=Annexin A5;
                         |DE            Short=Annexin-5;
                         |DE   AltName: Full=Annexin V;
                         |DE   AltName: Full=Lipocortin V;
                         |DE   AltName: Full=Placental anticoagulant protein I;
                         |DE            Short=PAP-I;
                         |DE   AltName: Full=PP4;
                         |DE   AltName: Full=Thromboplastin inhibitor;
                         |DE   AltName: Full=Vascular anticoagulant-alpha;
                         |DE            Short=VAC-alpha;
                         |DE   AltName: Full=Anchorin CII;
                         |DE   Flags: Precursor;
        */
        DeLineObject deObject = new DeLineObject();
        deObject.setRecName(new DeLineObject.Name());
        deObject.getRecName().setFullName("Annexin A5");
        deObject.getRecName().getShortNames().add("Annexin-5");
        deObject.getAltNames().add(createName("Annexin V"));
        deObject.getAltNames().add(createName("Lipocortin V"));
        deObject.getAltNames().add(createName("Placental anticoagulant protein I", "PAP-I"));
        deObject.getAltNames().add(createName("PP4"));
        deObject.getAltNames().add(createName("Thromboplastin inhibitor"));
        deObject.getAltNames().add(createName("Vascular anticoagulant-alpha", "VAC-alpha"));
        deObject.getAltNames().add(createName("Anchorin CII"));
        deObject.getFlags().add(DeLineObject.FlagType.Precursor);
        ProteinDescription pDesc = converter.convert(deObject);
        ProteinName recName = pDesc.getRecommendedName();

        validate("Annexin A5", "Annexin-5", recName);
        ;
        List<ProteinName> altNames = pDesc.getAlternativeNames();
        assertEquals(7, altNames.size());
        validate("Annexin V", null, altNames.get(0));
        validate("Lipocortin V", null, altNames.get(1));
        validate("Placental anticoagulant protein I", "PAP-I", altNames.get(2));
        validate("PP4", null, altNames.get(3));
        validate("Thromboplastin inhibitor", null, altNames.get(4));
        validate("Vascular anticoagulant-alpha", "VAC-alpha", altNames.get(5));
        validate("Anchorin CII", null, altNames.get(6));
        Flag flag = pDesc.getFlag();
        assertEquals(FlagType.PRECURSOR, flag.getType());
    }

    @Test
    void test2() {
        DeLineObject deObject = new DeLineObject();
        deObject.setRecName(new DeLineObject.Name());
        deObject.getRecName().setFullName("Annexin A5");
        deObject.getRecName().getShortNames().add("Annexin-5");
        deObject.getAltNames().add(createName("Annexin V"));
        deObject.getAltNames().add(createName("Lipocortin V"));
        deObject.getAltNames().add(createName("Placental anticoagulant protein I", "PAP-I"));
        deObject.getAltNames().add(createName("PP4"));
        deObject.getAltNames().add(createName("Thromboplastin inhibitor"));
        deObject.getAltNames().add(createName("Vascular anticoagulant-alpha", "VAC-alpha"));
        // |DE            EC=1.1.1.1;
        //  |DE            EC=1.1.1.2;
        List<String> ecs = new ArrayList<>();
        ecs.add("1.1.1.1");
        ecs.add("1.1.1.2");
        deObject.getAltNames().add(createName("Anchorin CII", new ArrayList<String>(), ecs));
        ProteinDescription pDesc = converter.convert(deObject);
        ProteinName recName = pDesc.getRecommendedName();

        validate("Annexin A5", "Annexin-5", recName);
        List<ProteinName> altNames = pDesc.getAlternativeNames();
        assertEquals(7, altNames.size());
        validate("Annexin V", null, altNames.get(0));
        validate("Lipocortin V", null, altNames.get(1));
        validate("Placental anticoagulant protein I", "PAP-I", altNames.get(2));
        validate("PP4", null, altNames.get(3));
        validate("Thromboplastin inhibitor", null, altNames.get(4));
        validate("Vascular anticoagulant-alpha", "VAC-alpha", altNames.get(5));
        validate("Anchorin CII", null, ecs, altNames.get(6));
        Flag flag = pDesc.getFlag();
        assertNull(flag);
    }

    @Test
    void test3() {
        /**
         * "DE RecName: Full=Arginine biosynthesis bifunctional protein argJ; |DE Includes: |DE
         * RecName: Full=Glutamate N-acetyltransferase; |DE EC=2.3.1.35; |DE AltName: Full=Ornithine
         * acetyltransferase; |DE Short=OATase; |DE AltName: Full=Ornithine transacetylase; |DE
         * Includes: |DE RecName: Full=Amino-acid acetyltransferase; |DE EC=2.3.1.1; |DE AltName:
         * Full=N-acetylglutamate synthase; |DE Short=AGS; |DE Contains: |DE RecName: Full=Arginine
         * biosynthesis bifunctional protein argJ alpha chain; |DE Contains: |DE RecName:
         * Full=Arginine biosynthesis bifunctional protein argJ beta chain;
         */
        DeLineObject deObject = new DeLineObject();
        deObject.setRecName(new DeLineObject.Name());
        deObject.getRecName().setFullName("Arginine biosynthesis bifunctional protein argJ");
        DeLineObject.NameBlock incName1 = new DeLineObject.NameBlock();
        List<String> ecs = new ArrayList<>();
        ecs.add("2.3.1.35");
        incName1.setRecName(
                createName("Glutamate N-acetyltransferase", new ArrayList<String>(), ecs));
        incName1.getAltNames().add(createName("Ornithine acetyltransferase", "OATase"));
        incName1.getAltNames().add(createName("Ornithine transacetylase"));
        deObject.getIncludedNames().add(incName1);
        DeLineObject.NameBlock incName2 = new DeLineObject.NameBlock();
        List<String> ecs2 = new ArrayList<>();
        ecs2.add("2.3.1.1");
        incName2.setRecName(
                createName("Amino-acid acetyltransferase", new ArrayList<String>(), ecs2));
        incName2.getAltNames().add(createName("N-acetylglutamate synthase", "AGS"));
        deObject.getIncludedNames().add(incName2);

        DeLineObject.NameBlock conName1 = new DeLineObject.NameBlock();
        conName1.setRecName(
                createName("Arginine biosynthesis bifunctional protein argJ alpha chain"));

        DeLineObject.NameBlock conName2 = new DeLineObject.NameBlock();
        conName2.setRecName(
                createName("Arginine biosynthesis bifunctional protein argJ beta chain"));
        deObject.getContainedNames().add(conName1);
        deObject.getContainedNames().add(conName2);
        ProteinDescription pDesc = converter.convert(deObject);

        ProteinName recName = pDesc.getRecommendedName();

        validate("Arginine biosynthesis bifunctional protein argJ", null, recName);
        List<ProteinName> altNames = pDesc.getAlternativeNames();
        assertTrue(altNames.isEmpty());
        List<ProteinSection> included = pDesc.getIncludes();
        assertEquals(2, included.size());
        ProteinSection included1 = included.get(0);
        validate("Glutamate N-acetyltransferase", null, ecs, included1.getRecommendedName());
        altNames = included1.getAlternativeNames();
        assertEquals(2, altNames.size());
        validate("Ornithine acetyltransferase", "OATase", altNames.get(0));
        validate("Ornithine transacetylase", null, altNames.get(1));

        ProteinSection included2 = included.get(1);
        altNames = included2.getAlternativeNames();
        assertEquals(1, altNames.size());
        validate("Amino-acid acetyltransferase", null, ecs2, included2.getRecommendedName());
        validate("N-acetylglutamate synthase", "AGS", altNames.get(0));

        List<ProteinSection> contained = pDesc.getContains();
        assertEquals(2, contained.size());

        ProteinSection contained1 = contained.get(0);
        validate(
                "Arginine biosynthesis bifunctional protein argJ alpha chain",
                null,
                contained1.getRecommendedName());
        ProteinSection contained2 = contained.get(1);

        validate(
                "Arginine biosynthesis bifunctional protein argJ beta chain",
                null,
                contained2.getRecommendedName());
    }

    @Test
    void testEvidence() {
        /*
        *   val deLines = """DE   RecName: Full=Annexin A5{EI1};
                         |DE            Short=Annexin-5{EI1, EI2};
                         |DE   AltName: Full=Annexin V{EI1};
                         |DE   AltName: Full=Lipocortin V{EI1};
                         |DE   AltName: Full=Placental anticoagulant protein I{EI1};
                         |DE            Short=PAP-I{EI2};
                         |DE   AltName: Full=PP4{EI1};
                         |DE   AltName: Full=Thromboplastin inhibitor{EI3};
                         |DE   Flags: Precursor{EI1, EI2, EI3};
        */
        DeLineObject deObject = new DeLineObject();
        deObject.setRecName(new DeLineObject.Name());
        deObject.getRecName().setFullName("Annexin A5");

        Map<String, List<String>> evidences = new TreeMap<>();
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208021");
        deObject.getEvidenceInfo().getEvidences().put("Annexin A5", evs);
        evidences.put("Annexin A5", evs);

        deObject.getRecName().getShortNames().add("Annexin-5");
        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208022");
        deObject.getEvidenceInfo().getEvidences().put("Annexin-5", evs);
        evidences.put("Annexin-5", evs);
        deObject.getAltNames().add(createName("Annexin V"));
        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208021");
        deObject.getEvidenceInfo().getEvidences().put("Annexin V", evs);
        evidences.put("Annexin V", evs);
        deObject.getAltNames().add(createName("Lipocortin V"));
        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208021");
        deObject.getEvidenceInfo().getEvidences().put("Lipocortin V", evs);
        evidences.put("Lipocortin V", evs);

        deObject.getAltNames().add(createName("Placental anticoagulant protein I", "PAP-I"));
        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208021");
        deObject.getEvidenceInfo().getEvidences().put("Placental anticoagulant protein I", evs);
        evidences.put("Placental anticoagulant protein I", evs);
        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208022");
        deObject.getEvidenceInfo().getEvidences().put("PAP-I", evs);

        evidences.put("PAP-I", evs);

        deObject.getAltNames().add(createName("PP4"));
        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208021");
        deObject.getEvidenceInfo().getEvidences().put("PP4", evs);
        evidences.put("PP4", evs);
        deObject.getAltNames().add(createName("Thromboplastin inhibitor"));
        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208023");
        deObject.getEvidenceInfo().getEvidences().put("Thromboplastin inhibitor", evs);
        evidences.put("Thromboplastin inhibitor", evs);

        deObject.getFlags().add(DeLineObject.FlagType.Precursor);
        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:15208021");
        evs.add("ECO:0000269|PubMed:15208022");
        evs.add("ECO:0000269|PubMed:15208023");
        deObject.getEvidenceInfo().getEvidences().put(DeLineObject.FlagType.Precursor, evs);
        ProteinDescription pDesc = converter.convert(deObject);
        ProteinName recName = pDesc.getRecommendedName();

        List<String> ecs = new ArrayList<>();

        validate("Annexin A5", "Annexin-5", ecs, recName, evidences);

        List<ProteinName> altNames = pDesc.getAlternativeNames();
        validate("Annexin V", null, ecs, altNames.get(0), evidences);
        validate("Lipocortin V", null, ecs, altNames.get(1), evidences);
        validate("Placental anticoagulant protein I", "PAP-I", ecs, altNames.get(2), evidences);
        validate("PP4", null, ecs, altNames.get(3), evidences);
        validate("Thromboplastin inhibitor", null, ecs, altNames.get(4), evidences);

        Flag flag = pDesc.getFlag();

        assertEquals(FlagType.PRECURSOR, flag.getType());
    }

    private void validate(String fullName, String shortName, ProteinName proteinName) {
        List<String> ecs = new ArrayList<>();
        validate(fullName, shortName, ecs, proteinName);
    }

    private void validate(
            String fullName,
            String shortName,
            List<String> ecs,
            ProteinName proteinName,
            Map<String, List<String>> evidences) {
        if (fullName != null) {
            assertEquals(fullName, proteinName.getFullName().getValue());
            validateEvidence(evidences.get(fullName), proteinName.getFullName().getEvidences());
        }
        if (shortName != null) {
            assertEquals(1, proteinName.getShortNames().size());
            assertEquals(shortName, proteinName.getShortNames().get(0).getValue());
            validateEvidence(
                    evidences.get(shortName), proteinName.getShortNames().get(0).getEvidences());
        }

        assertEquals(ecs.size(), proteinName.getEcNumbers().size());
        for (EC ecNumber : proteinName.getEcNumbers()) {
            validateEvidence(evidences.get(ecNumber.getValue()), ecNumber.getEvidences());
            assertTrue(ecs.contains(ecNumber.getValue()));
        }
    }

    private void validate(
            String fullName, String shortName, List<String> ecs, ProteinName proteinName) {
        validate(fullName, shortName, ecs, proteinName, new TreeMap<String, List<String>>());
    }

    private void validateEvidence(List<String> expected, List<Evidence> vals) {
        if ((expected == null) || (expected.size() == 0)) return;
        assertEquals(expected.size(), vals.size());
        for (Evidence val : vals) {
            assertTrue(expected.contains(val.getValue()));
        }
    }

    private DeLineObject.Name createName(String fullName) {
        List<String> shortNames = new ArrayList<>();
        return createName(fullName, shortNames);
    }

    private DeLineObject.Name createName(String fullName, String shortName) {
        List<String> shortNames = new ArrayList<>();
        shortNames.add(shortName);
        return createName(fullName, shortNames);
    }

    private DeLineObject.Name createName(String fullName, List<String> shortNames) {
        return createName(fullName, shortNames, new ArrayList<String>());
    }

    private DeLineObject.Name createName(
            String fullName, List<String> shortNames, List<String> ecs) {
        DeLineObject.Name name = new DeLineObject.Name();
        name.setFullName(fullName);
        name.getShortNames().addAll(shortNames);
        name.setEcs(ecs);
        return name;
    }
}
