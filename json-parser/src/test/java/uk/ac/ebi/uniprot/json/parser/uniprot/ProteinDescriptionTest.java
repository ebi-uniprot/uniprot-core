package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.NameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinSectionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class ProteinDescriptionTest {

    @Test
    public void testProteinDescriptionSimple() {
        Name recommendedName = new NameImpl("protein name", Collections.emptyList());

        ProteinDescriptionBuilder descriptionBuilder = ProteinDescriptionBuilder.newInstance();
        descriptionBuilder.recommendedName(ProteinDescriptionFactory.INSTANCE.createProteinName(recommendedName,Collections.emptyList(),Collections.emptyList()));

        ProteinDescription proteinDescription = descriptionBuilder.build();
        ValidateJson.verifyJsonRoundTripParser(proteinDescription);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(proteinDescription);
        assertNotNull(jsonNode.get("recommendedName"));
        assertNotNull(jsonNode.get("recommendedName").get("fullName"));
        assertNotNull(jsonNode.get("recommendedName").get("fullName").get("value"));
        assertEquals("protein name",jsonNode.get("recommendedName").get("fullName").get("value").asText());

    }

    @Test
    public void testProteinDescriptionComplete() {
        ProteinDescription proteinDescription = getProteinDescription();
        ValidateJson.verifyJsonRoundTripParser(proteinDescription);
        ValidateJson.verifyEmptyFields(proteinDescription);


        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(proteinDescription);

        validateRecommendedName(jsonNode, "");
        validateAlternativeNames(jsonNode,"");

        assertNotNull(jsonNode.get("contains"));
        assertEquals(1,jsonNode.get("contains").size());
        validateRecommendedName(jsonNode.get("contains").get(0), "contains");
        validateAlternativeNames(jsonNode.get("contains").get(0), "contains");

        assertNotNull(jsonNode.get("includes"));
        assertEquals(1,jsonNode.get("includes").size());
        validateRecommendedName(jsonNode.get("includes").get(0), "includes");
        validateAlternativeNames(jsonNode.get("includes").get(0), "includes");

        assertNotNull(jsonNode.get("submissionNames"));
        JsonNode names = jsonNode.get("submissionNames");
        assertEquals(1,names.size());
        names = names.get(0);
        assertNotNull(names.get("fullName"));
        JsonNode fullName = names.get("fullName");
        ValidateJson.validateValueEvidence(fullName,"sub full Name","ECO:0000255","PROSITE-ProRule","PRU10027");

        assertNotNull(names.get("shortNames"));
        JsonNode shortNames = names.get("shortNames");
        assertEquals(1,shortNames.size());
        ValidateJson.validateValueEvidence(shortNames.get(0),"submission short name","ECO:0000255","PROSITE-ProRule","PRU10020");

        assertNotNull(names.get("ecNumbers"));
        JsonNode ecNumbers = names.get("ecNumbers");
        assertEquals(1,ecNumbers.size());
        ValidateJson.validateValueEvidence(ecNumbers.get(0),"1.2.3.5","ECO:0000255","PROSITE-ProRule","PRU100211");


        assertNotNull(jsonNode.get("allergenName"));
        ValidateJson.validateValueEvidence(jsonNode.get("allergenName"),"allergen","ECO:0000255","PROSITE-ProRule","PRU10023");

        assertNotNull(jsonNode.get("biotechName"));
        ValidateJson.validateValueEvidence(jsonNode.get("biotechName"),"biotech","ECO:0000255","PROSITE-ProRule","PRU10024");

        assertNotNull(jsonNode.get("cdAntigenNames"));
        JsonNode cdAntigenNames = jsonNode.get("cdAntigenNames");
        assertEquals(1,cdAntigenNames.size());
        ValidateJson.validateValueEvidence(cdAntigenNames.get(0),"cd antigen","ECO:0000255","PROSITE-ProRule","PRU10025");

        assertNotNull(jsonNode.get("innNames"));
        JsonNode innNames = jsonNode.get("innNames");
        assertEquals(1,innNames.size());
        ValidateJson.validateValueEvidence(innNames.get(0),"inn antigen","ECO:0000255","PROSITE-ProRule","PRU100212");

        assertNotNull(jsonNode.get("flag"));
        assertEquals("Fragment",jsonNode.get("flag").asText());


    }

    private void validateAlternativeNames(JsonNode jsonNode, String from) {
        assertNotNull(jsonNode.get("alternativeNames"));
        JsonNode names = jsonNode.get("alternativeNames");
        assertEquals(1,names.size());
        names = names.get(0);
        assertNotNull(names.get("fullName"));
        JsonNode fullName = names.get("fullName");
        ValidateJson.validateValueEvidence(fullName,from+"a full alt Name","ECO:0000255","PROSITE-ProRule","PRU10022");

        assertNotNull(names.get("shortNames"));
        JsonNode shortNames = names.get("shortNames");
        assertEquals(1,shortNames.size());
        ValidateJson.validateValueEvidence(shortNames.get(0),from+"short alt name1","ECO:0000255","PROSITE-ProRule","PRU10028");

        assertNotNull(names.get("ecNumbers"));
        JsonNode ecNumbers = names.get("ecNumbers");
        assertEquals(1,ecNumbers.size());
        ValidateJson.validateValueEvidence(ecNumbers.get(0),"1.2.3.3","ECO:0000255","PROSITE-ProRule","PRU10029");
    }

    private void validateRecommendedName(JsonNode jsonNode, String from) {
        assertNotNull(jsonNode.get("recommendedName"));
        assertNotNull(jsonNode.get("recommendedName").get("fullName"));
        JsonNode fullName = jsonNode.get("recommendedName").get("fullName");
        ValidateJson.validateValueEvidence(fullName,from+"rec full Name","ECO:0000255","PROSITE-ProRule","PRU10026");

        assertNotNull(jsonNode.get("recommendedName").get("shortNames"));
        JsonNode shortNames = jsonNode.get("recommendedName").get("shortNames");
        assertEquals(1,shortNames.size());
        ValidateJson.validateValueEvidence(shortNames.get(0),from+"recommended short name","ECO:0000255","PROSITE-ProRule","PRU10020");

        assertNotNull(jsonNode.get("recommendedName").get("ecNumbers"));
        JsonNode ecNumbers = jsonNode.get("recommendedName").get("ecNumbers");
        assertEquals(1,ecNumbers.size());
        ValidateJson.validateValueEvidence(ecNumbers.get(0),"1.2.3.4","ECO:0000255","PROSITE-ProRule","PRU100210");
    }


    static ProteinDescription getProteinDescription(){

        Name allergenName = ProteinDescriptionFactory.INSTANCE.createName("allergen", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10023"));

        Name biotechName = ProteinDescriptionFactory.INSTANCE.createName("biotech", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10024"));

        List<Name> antigenNames = new ArrayList<>();
        antigenNames.add(ProteinDescriptionFactory.INSTANCE.createName("cd antigen", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10025")));

        ProteinName recommendedName = getRecommendedName("");
        List<ProteinName> proteinAltNames = createAltName("");
        List<ProteinName> subNames = getSubmissionName();

        List<Name> innNames = new ArrayList<>();
        innNames.add(ProteinDescriptionFactory.INSTANCE.createName("inn antigen", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU100212")));

        List<ProteinSection> includes = new ArrayList<>();
        ProteinSectionImpl sectionInclude = new ProteinSectionImpl(getRecommendedName("includes"), createAltName("includes"));
        includes.add(sectionInclude);

        List<ProteinSection> contains = new ArrayList<>();
        ProteinSectionImpl sectionContains = new ProteinSectionImpl(getRecommendedName("contains"), createAltName("contains"));
        contains.add(sectionContains);

        ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();

        return builder.allergenName(allergenName)
                .alternativeNames(proteinAltNames)
                .biotechName(biotechName)
                .cdAntigenNames(antigenNames)
                .flag(new FlagImpl(FlagType.FRAGMENT))
                .includes(includes)
                .contains(contains)
                .innNames(innNames)
                .recommendedName(recommendedName)
                .submissionNames(subNames)
                .build();

    }

    private static ProteinName getRecommendedName(String from) {
        Name fullName = ProteinDescriptionFactory.INSTANCE.createName(from+"rec full Name", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10026"));
        List<Name> shortNames = createShortNames(from+"recommended");
        List<EC> ecNumbers = createECNumbers("1.2.3.4",10);
        return ProteinDescriptionFactory.INSTANCE
                .createProteinName(fullName, shortNames, ecNumbers);
    }

    private static List<ProteinName> getSubmissionName() {
        Name fullName1 = ProteinDescriptionFactory.INSTANCE.createName("sub full Name", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10027"));

        List<EC> ecNumbers1 = createECNumbers("1.2.3.5",11);
        List<Name> shortNames1 = createShortNames("submission");
        ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName1, shortNames1, ecNumbers1);
        List<ProteinName> subNames = new ArrayList<>();
        subNames.add(subName);
        return subNames;
    }

    private static List<ProteinName> createAltName(String from) {
        List<ProteinName> alternativeNames = new ArrayList<>();
        Name fullName = ProteinDescriptionFactory.INSTANCE.createName(from+"a full alt Name", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10022"));
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName(from+"short alt name1", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10028")));
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.3", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10029")));

        alternativeNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers));

        return alternativeNames;
    }

    private static List<Name> createShortNames(String from) {
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName(from+" short name", CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10020")));
        return shortNames;
    }

    private static List<EC> createECNumbers(String ec,int index) {
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber(ec, CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU1002"+index)));
        return ecNumbers;
    }
}
