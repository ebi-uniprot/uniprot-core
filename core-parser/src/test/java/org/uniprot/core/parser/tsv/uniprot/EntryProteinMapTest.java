package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.*;
import org.uniprot.core.uniprot.description.builder.*;

class EntryProteinMapTest {

    @Test
    void testFields() {
        List<String> fields = EntryProteinMap.FIELDS;
        List<String> expected = Arrays.asList("protein_name", "ec", "fragment");
        assertEquals(expected, fields);
    }

    @Test
    void testRecName() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> shortNames = Arrays.asList("short1", "short2");
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        builder.recommendedName(createProteinRecName("some full name", shortNames, ecs));
        builder.flag(FlagType.FRAGMENTS);
        ProteinDescription proteinDescription = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(proteinDescription);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected = "some full name, short1, short2, EC 1.1.2.3, EC 1.2.22.2";
        assertEquals(expected, value);
        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.2.22.2", ec);

        String fragment = result.get(EntryProteinMap.FIELDS.get(2));
        assertEquals("fragment", fragment);
    }

    @Test
    void testRecNameWithAltNames() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> shortNames = Arrays.asList("short1", "short2");
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        builder.recommendedName(createProteinRecName("some full name", shortNames, ecs));
        List<ProteinAltName> alternativeName = new ArrayList<>();
        alternativeName.add(createProteinAltName("alter name1", Collections.emptyList(), ecs));
        List<String> shortNames2 = Arrays.asList("short11", "short12");
        alternativeName.add(
                createProteinAltName("altr name 2", shortNames2, Collections.emptyList()));
        builder.alternativeNamesSet(alternativeName);
        builder.flag(FlagType.PRECURSOR);
        ProteinDescription protein = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(protein);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected =
                "some full name, short1, short2, EC 1.1.2.3, EC 1.2.22.2"
                        + " (alter name1, EC 1.1.2.3, EC 1.2.22.2) (altr name 2, short11, short12)";

        assertEquals(expected, value);

        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.2.22.2", ec);
        String fragment = result.get(EntryProteinMap.FIELDS.get(2));
        assertEquals("", fragment);
    }

    @Test
    void testRecNameWithAltNamesAllergen() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> shortNames = Arrays.asList("short1", "short2");
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        builder.recommendedName(createProteinRecName("some full name", shortNames, ecs));
        List<ProteinAltName> alternativeName = new ArrayList<>();
        alternativeName.add(createProteinAltName("alter name1", Collections.emptyList(), ecs));
        List<String> shortNames2 = Arrays.asList("short11", "short12");
        alternativeName.add(
                createProteinAltName("altr name 2", shortNames2, Collections.emptyList()));
        builder.alternativeNamesSet(alternativeName);
        builder.allergenName(createName("someAller"));
        builder.flag(FlagType.PRECURSOR);
        ProteinDescription protein = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(protein);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected =
                "some full name, short1, short2, EC 1.1.2.3, EC 1.2.22.2"
                        + " (alter name1, EC 1.1.2.3, EC 1.2.22.2) (altr name 2, short11, short12)"
                        + " (allergen someAller)";

        assertEquals(expected, value);
        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.2.22.2", ec);

        String fragment = result.get(EntryProteinMap.FIELDS.get(2));
        assertEquals("", fragment);
    }

    @Test
    void testRecNameWithAltNamesAllergenBiotech() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> shortNames = Arrays.asList("short1", "short2");
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        builder.recommendedName(createProteinRecName("some full name", shortNames, ecs));
        List<ProteinAltName> alternativeName = new ArrayList<>();
        alternativeName.add(createProteinAltName("alter name1", Collections.emptyList(), ecs));
        List<String> shortNames2 = Arrays.asList("short11", "short12");
        alternativeName.add(
                createProteinAltName("altr name 2", shortNames2, Collections.emptyList()));
        builder.alternativeNamesSet(alternativeName);
        builder.allergenName(createName("someAller"));
        builder.biotechName(createName("some biote"));
        builder.flag(FlagType.FRAGMENTS_PRECURSOR);
        ProteinDescription protein = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(protein);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected =
                "some full name, short1, short2, EC 1.1.2.3, EC 1.2.22.2"
                        + " (alter name1, EC 1.1.2.3, EC 1.2.22.2) (altr name 2, short11, short12)"
                        + " (allergen someAller) (biotech some biote)";

        assertEquals(expected, value);
        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.2.22.2", ec);
    }

    @Test
    void testRecNameWithAltNamesAllergenCdAntigen() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> shortNames = Arrays.asList("short1", "short2");
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        builder.recommendedName(createProteinRecName("some full name", shortNames, ecs));
        List<ProteinAltName> alternativeName = new ArrayList<>();
        alternativeName.add(createProteinAltName("alter name1", Collections.emptyList(), ecs));
        List<String> shortNames2 = Arrays.asList("short11", "short12");
        alternativeName.add(
                createProteinAltName("altr name 2", shortNames2, Collections.emptyList()));
        builder.alternativeNamesSet(alternativeName);
        builder.allergenName(createName("someAller"));
        List<Name> cdAntigenName = new ArrayList<>();
        cdAntigenName.add(createName("some antig1"));
        cdAntigenName.add(createName("some antig2"));
        builder.cdAntigenNamesSet(cdAntigenName);
        builder.flag(FlagType.FRAGMENTS_PRECURSOR);
        ProteinDescription protein = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(protein);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected =
                "some full name, short1, short2, EC 1.1.2.3, EC 1.2.22.2"
                        + " (alter name1, EC 1.1.2.3, EC 1.2.22.2) (altr name 2, short11, short12)"
                        + " (allergen someAller)"
                        + " (CD antigen some antig1) (CD antigen some antig2)";

        assertEquals(expected, value);
        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.2.22.2", ec);
    }

    @Test
    void testRecNameWithAltNamesAllergenInn() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> shortNames = Arrays.asList("short1", "short2");
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        builder.recommendedName(createProteinRecName("some full name", shortNames, ecs));
        List<ProteinAltName> alternativeName = new ArrayList<>();
        alternativeName.add(createProteinAltName("alter name1", Collections.emptyList(), ecs));
        List<String> shortNames2 = Arrays.asList("short11", "short12");
        alternativeName.add(
                createProteinAltName("altr name 2", shortNames2, Collections.emptyList()));
        builder.alternativeNamesSet(alternativeName);
        builder.allergenName(createName("someAller"));
        List<Name> inns = new ArrayList<>();
        inns.add(createName("some antig1"));
        inns.add(createName("some antig2"));
        builder.innNamesSet(inns);
        builder.flag(FlagType.FRAGMENTS_PRECURSOR);
        ProteinDescription protein = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(protein);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected =
                "some full name, short1, short2, EC 1.1.2.3, EC 1.2.22.2"
                        + " (alter name1, EC 1.1.2.3, EC 1.2.22.2) (altr name 2, short11, short12)"
                        + " (allergen someAller)"
                        + " (some antig1) (some antig2)";

        assertEquals(expected, value);
        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.2.22.2", ec);
    }

    @Test
    void testSubnames() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        List<ProteinSubName> subnames = new ArrayList<>();
        subnames.add(createProteinSubName("subname name1", ecs));
        subnames.add(createProteinSubName("subname name 2", Collections.emptyList()));
        builder.submissionNamesSet(subnames);
        builder.flag(FlagType.FRAGMENTS_PRECURSOR);
        ProteinDescription protein = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(protein);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected = "subname name1, EC 1.1.2.3, EC 1.2.22.2 (subname name 2)";
        assertEquals(expected, value);
        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.2.22.2", ec);

        String fragment = result.get(EntryProteinMap.FIELDS.get(2));
        assertEquals("fragment", fragment);
    }

    @Test
    void testRecNameWithContain() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> shortNames = Arrays.asList("short1", "short2");
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        builder.recommendedName(createProteinRecName("some full name", shortNames, ecs));
        List<ProteinSection> containsList = new ArrayList<>();
        containsList.add(createProteinSection("some contains1", true));
        containsList.add(createProteinSection("some contains 2", false));
        builder.containsSet(containsList);
        builder.flag(FlagType.FRAGMENT);
        ProteinDescription protein = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(protein);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected =
                "some full name, short1, short2, EC 1.1.2.3, EC 1.2.22.2"
                        + " [Cleaved into: some contains1, sh1, sh2, EC 1.1.22.3, EC 1.2.34.2 (new Altname1, EC 1.1.22.3, EC 1.2.34.2) (new Altname 2, short11, short12);"
                        + " some contains 2, sh1, sh2, EC 1.1.22.3, EC 1.2.34.2 ]";
        assertEquals(expected, value);
        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.1.22.3; 1.2.22.2; 1.2.34.2", ec);

        String fragment = result.get(EntryProteinMap.FIELDS.get(2));
        assertEquals("fragment", fragment);
    }

    @Test
    void testRecNameWithContainInclude() {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        List<String> shortNames = Arrays.asList("short1", "short2");
        List<String> ecs = Arrays.asList("1.1.2.3", "1.2.22.2");
        builder.recommendedName(createProteinRecName("some full name", shortNames, ecs));
        List<ProteinSection> includeList = new ArrayList<>();
        includeList.add(createProteinSection("some domain1", false));
        includeList.add(createProteinSection("some domain 2", true));
        builder.includesSet(includeList);
        List<ProteinSection> containsList = new ArrayList<>();
        containsList.add(createProteinSection("some contains1", true));
        containsList.add(createProteinSection("some contains 2", false));
        builder.containsSet(containsList);
        builder.flag(FlagType.FRAGMENT_PRECURSOR);
        ProteinDescription protein = builder.build();
        EntryProteinMap downloadable = new EntryProteinMap(protein);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(3, result.size());
        String value = result.get(EntryProteinMap.FIELDS.get(0));
        assertNotNull(value);
        String expected =
                "some full name, short1, short2, EC 1.1.2.3, EC 1.2.22.2"
                        + " [Cleaved into: some contains1, sh1, sh2, EC 1.1.22.3, EC 1.2.34.2 (new Altname1, EC 1.1.22.3, EC 1.2.34.2) (new Altname 2, short11, short12);"
                        + " some contains 2, sh1, sh2, EC 1.1.22.3, EC 1.2.34.2 ]"
                        + " [Includes: some domain1, sh1, sh2, EC 1.1.22.3, EC 1.2.34.2; some domain 2, sh1, sh2, EC 1.1.22.3, EC 1.2.34.2"
                        + " (new Altname1, EC 1.1.22.3, EC 1.2.34.2) (new Altname 2, short11, short12) ]";
        assertEquals(expected, value);
        String ec = result.get(EntryProteinMap.FIELDS.get(1));
        assertEquals("1.1.2.3; 1.1.22.3; 1.2.22.2; 1.2.34.2", ec);

        String fragment = result.get(EntryProteinMap.FIELDS.get(2));
        assertEquals("fragment", fragment);
    }

    private ProteinSection createProteinSection(String fullName, boolean hasAltName) {
        List<String> shortNames = Arrays.asList("sh1", "sh2");
        List<String> ecs = Arrays.asList("1.1.22.3", "1.2.34.2");
        ProteinRecName recName = createProteinRecName(fullName, shortNames, ecs);
        List<ProteinAltName> alternativeName = new ArrayList<>();
        if (hasAltName) {
            alternativeName.add(createProteinAltName("new Altname1", Collections.emptyList(), ecs));
            List<String> shortNames2 = Arrays.asList("short11", "short12");
            alternativeName.add(
                    createProteinAltName("new Altname 2", shortNames2, Collections.emptyList()));
        }
        return new ProteinSectionBuilder()
                .recommendedName(recName)
                .alternativeNamesSet(alternativeName)
                .build();
    }

    private ProteinRecName createProteinRecName(
            String fullname, List<String> shortNames, List<String> ecs) {
        List<Name> shortNameList = null;
        if (shortNames != null) {
            shortNameList = shortNames.stream().map(this::createName).collect(Collectors.toList());
        }
        List<EC> ecList = null;
        if (ecs != null) {
            ecList =
                    ecs.stream()
                            .map(ec -> new ECBuilder().value(ec).build())
                            .collect(Collectors.toList());
        }
        return new ProteinRecNameBuilder()
                .fullName(createName(fullname))
                .shortNamesSet(shortNameList)
                .ecNumbersSet(ecList)
                .build();
    }

    private ProteinSubName createProteinSubName(String fullname, List<String> ecs) {
        List<Name> shortNameList = null;

        List<EC> ecList = null;
        if (ecs != null) {
            ecList =
                    ecs.stream()
                            .map(ec -> new ECBuilder().value(ec).build())
                            .collect(Collectors.toList());
        }
        return new ProteinSubNameBuilder()
                .fullName(createName(fullname))
                .ecNumbersSet(ecList)
                .build();
    }

    private ProteinAltName createProteinAltName(
            String fullname, List<String> shortNames, List<String> ecs) {
        List<Name> shortNameList = null;
        if (shortNames != null) {
            shortNameList = shortNames.stream().map(this::createName).collect(Collectors.toList());
        }
        List<EC> ecList = null;
        if (ecs != null) {
            ecList =
                    ecs.stream()
                            .map(ec -> new ECBuilder().value(ec).build())
                            .collect(Collectors.toList());
        }
        return new ProteinAltNameBuilder()
                .fullName(createName(fullname))
                .shortNamesSet(shortNameList)
                .ecNumbersSet(ecList)
                .build();
    }

    private Name createName(String name) {
        return new NameBuilder().value(name).build();
    }
}
