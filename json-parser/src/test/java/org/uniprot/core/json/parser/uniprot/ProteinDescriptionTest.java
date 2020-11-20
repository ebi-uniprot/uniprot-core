package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.description.impl.*;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class ProteinDescriptionTest {

    @Test
    void testProteinDescriptionSimple() {
        Name fullName = new NameBuilder().value("protein name").build();
        ProteinName recommendedName = new ProteinNameBuilder().fullName(fullName).build();
        ProteinDescription proteinDescription =
                new ProteinDescriptionBuilder().recommendedName(recommendedName).build();
        ValidateJson.verifyJsonRoundTripParser(proteinDescription);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(proteinDescription);
        assertNotNull(jsonNode.get("recommendedName"));
        assertNotNull(jsonNode.get("recommendedName").get("fullName"));
        assertNotNull(jsonNode.get("recommendedName").get("fullName").get("value"));
        assertEquals(
                "protein name",
                jsonNode.get("recommendedName").get("fullName").get("value").asText());
    }

    @Test
    void testProteinDescriptionComplete() {
        ProteinDescription proteinDescription = getProteinDescription();
        ValidateJson.verifyJsonRoundTripParser(proteinDescription);
        ValidateJson.verifyEmptyFields(proteinDescription);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(proteinDescription);

        validateRecommendedName(jsonNode, "");
        validateAlternativeNames(jsonNode, "");

        assertNotNull(jsonNode.get("contains"));
        assertEquals(1, jsonNode.get("contains").size());
        validateRecommendedName(jsonNode.get("contains").get(0), "contains");
        validateAlternativeNames(jsonNode.get("contains").get(0), "contains");
        validateAllergenBiotechCdAntigenAndInnNames(jsonNode.get("contains").get(0));
        assertNotNull(jsonNode.get("includes"));
        assertEquals(1, jsonNode.get("includes").size());
        validateRecommendedName(jsonNode.get("includes").get(0), "includes");
        validateAlternativeNames(jsonNode.get("includes").get(0), "includes");
        validateAllergenBiotechCdAntigenAndInnNames(jsonNode.get("includes").get(0));

        assertNotNull(jsonNode.get("submissionNames"));
        JsonNode names = jsonNode.get("submissionNames");
        assertEquals(1, names.size());
        names = names.get(0);
        assertNotNull(names.get("fullName"));
        JsonNode fullName = names.get("fullName");
        ValidateJson.validateValueEvidence(
                fullName, "sub full Name", "ECO:0000255", "PROSITE-ProRule", "PRU10027");

        assertNotNull(names.get("ecNumbers"));
        JsonNode ecNumbers = names.get("ecNumbers");
        assertEquals(1, ecNumbers.size());
        ValidateJson.validateValueEvidence(
                ecNumbers.get(0), "1.2.3.5", "ECO:0000255", "PROSITE-ProRule", "PRU100211");

        validateAllergenBiotechCdAntigenAndInnNames(jsonNode);

        assertNotNull(jsonNode.get("flag"));
        assertEquals("Fragment", jsonNode.get("flag").asText());
    }

    private void validateAllergenBiotechCdAntigenAndInnNames(JsonNode jsonNode) {
        assertNotNull(jsonNode.get("allergenName"));
        ValidateJson.validateValueEvidence(
                jsonNode.get("allergenName"),
                "allergen",
                "ECO:0000255",
                "PROSITE-ProRule",
                "PRU10023");

        assertNotNull(jsonNode.get("biotechName"));
        ValidateJson.validateValueEvidence(
                jsonNode.get("biotechName"),
                "biotech",
                "ECO:0000255",
                "PROSITE-ProRule",
                "PRU10024");

        assertNotNull(jsonNode.get("cdAntigenNames"));
        JsonNode cdAntigenNames = jsonNode.get("cdAntigenNames");
        assertEquals(1, cdAntigenNames.size());
        ValidateJson.validateValueEvidence(
                cdAntigenNames.get(0), "cd antigen", "ECO:0000255", "PROSITE-ProRule", "PRU10025");

        assertNotNull(jsonNode.get("innNames"));
        JsonNode innNames = jsonNode.get("innNames");
        assertEquals(1, innNames.size());
        ValidateJson.validateValueEvidence(
                innNames.get(0), "inn antigen", "ECO:0000255", "PROSITE-ProRule", "PRU100212");
    }

    private void validateAlternativeNames(JsonNode jsonNode, String from) {
        assertNotNull(jsonNode.get("alternativeNames"));
        JsonNode names = jsonNode.get("alternativeNames");
        assertEquals(1, names.size());
        names = names.get(0);
        assertNotNull(names.get("fullName"));
        JsonNode fullName = names.get("fullName");
        ValidateJson.validateValueEvidence(
                fullName, from + "a full alt Name", "ECO:0000255", "PROSITE-ProRule", "PRU10022");

        assertNotNull(names.get("shortNames"));
        JsonNode shortNames = names.get("shortNames");
        assertEquals(1, shortNames.size());
        ValidateJson.validateValueEvidence(
                shortNames.get(0),
                from + "short alt name1",
                "ECO:0000255",
                "PROSITE-ProRule",
                "PRU10028");

        assertNotNull(names.get("ecNumbers"));
        JsonNode ecNumbers = names.get("ecNumbers");
        assertEquals(1, ecNumbers.size());
        ValidateJson.validateValueEvidence(
                ecNumbers.get(0), "1.2.3.3", "ECO:0000255", "PROSITE-ProRule", "PRU10029");
    }

    private void validateRecommendedName(JsonNode jsonNode, String from) {
        assertNotNull(jsonNode.get("recommendedName"));
        assertNotNull(jsonNode.get("recommendedName").get("fullName"));
        JsonNode fullName = jsonNode.get("recommendedName").get("fullName");
        ValidateJson.validateValueEvidence(
                fullName, from + "rec full Name", "ECO:0000255", "PROSITE-ProRule", "PRU10026");

        assertNotNull(jsonNode.get("recommendedName").get("shortNames"));
        JsonNode shortNames = jsonNode.get("recommendedName").get("shortNames");
        assertEquals(1, shortNames.size());
        ValidateJson.validateValueEvidence(
                shortNames.get(0),
                from + "recommended short name",
                "ECO:0000255",
                "PROSITE-ProRule",
                "PRU10020");

        assertNotNull(jsonNode.get("recommendedName").get("ecNumbers"));
        JsonNode ecNumbers = jsonNode.get("recommendedName").get("ecNumbers");
        assertEquals(1, ecNumbers.size());
        ValidateJson.validateValueEvidence(
                ecNumbers.get(0), "1.2.3.4", "ECO:0000255", "PROSITE-ProRule", "PRU100210");
    }

    public static ProteinDescription getProteinDescription() {
        Name allergenName = createName("allergen", "PRU10023");
        Name biotechName = createName("biotech", "PRU10024");
        Name antigenName = createName("cd antigen", "PRU10025");

        ProteinName recommendedName = getRecommendedName("");
        List<ProteinName> proteinAltNames = createAltName("");
        List<ProteinSubName> subNames = getSubmissionName();

        Name innName = createName("inn antigen", "PRU100212");

        ProteinSection include =
                new ProteinSectionBuilder()
                        .recommendedName(getRecommendedName("includes"))
                        .alternativeNamesSet(createAltName("includes"))
                        .innNamesAdd(innName)
                        .allergenName(allergenName)
                        .biotechName(biotechName)
                        .cdAntigenNamesAdd(antigenName)
                        .build();

        ProteinSection contain =
                new ProteinSectionBuilder()
                        .recommendedName(getRecommendedName("contains"))
                        .alternativeNamesSet(createAltName("contains"))
                        .innNamesAdd(innName)
                        .allergenName(allergenName)
                        .biotechName(biotechName)
                        .cdAntigenNamesAdd(antigenName)
                        .build();

        return new ProteinDescriptionBuilder()
                .allergenName(allergenName)
                .alternativeNamesSet(proteinAltNames)
                .biotechName(biotechName)
                .cdAntigenNamesAdd(antigenName)
                .flag(FlagType.FRAGMENT)
                .includesAdd(include)
                .containsAdd(contain)
                .innNamesAdd(innName)
                .recommendedName(recommendedName)
                .submissionNamesSet(subNames)
                .build();
    }

    private static ProteinName getRecommendedName(String from) {
        Name fullName = createName(from + "rec full Name", "PRU10026");
        List<Name> shortNames = createNameList(from + "recommended short name", "PRU10020");
        List<EC> ecNumbers = createECNumbers("1.2.3.4", 10);

        return new ProteinNameBuilder()
                .fullName(fullName)
                .shortNamesSet(shortNames)
                .ecNumbersSet(ecNumbers)
                .build();
    }

    private static List<ProteinSubName> getSubmissionName() {
        Name fullName1 = createName("sub full Name", "PRU10027");
        List<EC> ecNumbers1 = createECNumbers("1.2.3.5", 11);

        ProteinSubName subName =
                new ProteinSubNameBuilder().fullName(fullName1).ecNumbersSet(ecNumbers1).build();
        return Collections.singletonList(subName);
    }

    private static List<ProteinName> createAltName(String from) {
        Name fullName = createName(from + "a full alt Name", "PRU10022");
        List<Name> shortNames = createNameList(from + "short alt name1", "PRU10028");
        List<EC> ecNumbers = createECNumbers("1.2.3.3", 9);

        ProteinName alternativeName =
                new ProteinNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(shortNames)
                        .ecNumbersSet(ecNumbers)
                        .build();
        return Collections.singletonList(alternativeName);
    }

    private static List<Name> createNameList(String value, String id) {
        return Collections.singletonList(createName(value, id));
    }

    private static Name createName(String value, String id) {
        return new NameBuilder()
                .value(value)
                .evidencesAdd(CreateUtils.createEvidence("ECO:0000255|PROSITE-ProRule:" + id))
                .build();
    }

    private static List<EC> createECNumbers(String ec, int index) {
        return Collections.singletonList(
                new ECBuilder()
                        .value(ec)
                        .evidencesAdd(
                                CreateUtils.createEvidence(
                                        "ECO:0000255|PROSITE-ProRule:PRU1002" + index))
                        .build());
    }
}
