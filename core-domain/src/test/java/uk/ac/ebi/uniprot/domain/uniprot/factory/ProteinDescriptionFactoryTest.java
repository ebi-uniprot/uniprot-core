package uk.ac.ebi.uniprot.domain.uniprot.factory;

public class ProteinDescriptionFactoryTest {

//
//    @Test
//    public void testCreateECNumber() {
//        String val = "1.2.3.4";
//        List<Evidence> evidences = createEvidences();
//        EC ecNumber = ProteinDescriptionFactory.INSTANCE.createECNumber(val, evidences);
//        assertEquals(val, ecNumber.getValue());
//        assertEquals(evidences, ecNumber.getEvidences());
//        TestHelper.verifyJson(ecNumber);
//    }
//
//    @Test
//    public void testCreateName() {
//        String val = "some value";
//        List<Evidence> evidences = createEvidences();
//        Name proteinName = ProteinDescriptionFactory.INSTANCE.createName(val, evidences);
//        assertEquals(val, proteinName.getValue());
//        assertEquals(evidences, proteinName.getEvidences());
//        TestHelper.verifyJson(proteinName);
//    }
//
//    @Test
//    public void testCreateRecName() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//        List<Name> shortNames = createShortNames();
//        List<EC> ecNumbers = createECNumbers();
//        ProteinName recName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
//        assertEquals(fullName, recName.getFullName());
//        assertEquals(shortNames, recName.getShortNames());
//        assertEquals(ecNumbers, recName.getEcNumbers());
//        assertTrue(recName.isValid());
//
//        TestHelper.verifyJson(recName);
//
//    }
//
//    @Test
//    public void testRecNameInValid() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//        List<Name> shortNames = createShortNames();
//        List<EC> ecNumbers = createECNumbers();
//        ProteinName recName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
//        assertTrue(recName.isValid());
//        fullName = ProteinDescriptionFactory.INSTANCE.createName("", evidences);
//        recName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
//        assertFalse(recName.isValid());
//        recName = ProteinDescriptionFactory.INSTANCE.createProteinName(null, shortNames, ecNumbers);
//        assertFalse(recName.isValid());
//        TestHelper.verifyJson(recName);
//
//    }
//
//    @Test
//    public void testCreateProteinName() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full alt Name", evidences);
//        List<Name> shortNames = createShortNames();
//        List<EC> ecNumbers = createECNumbers();
//        ProteinName altName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
//        assertEquals(fullName, altName.getFullName());
//        assertEquals(shortNames, altName.getShortNames());
//        assertEquals(ecNumbers, altName.getEcNumbers());
//
//        TestHelper.verifyJson(altName);
//    }
//
//
//    @Test
//    public void testCreateProteinSubmissionName() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//
//        List<EC> ecNumbers = createECNumbers();
//        ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, null, ecNumbers);
//        assertEquals(fullName, subName.getFullName());
//        assertEquals(ecNumbers, subName.getEcNumbers());
//        assertTrue(subName.isValid());
//        TestHelper.verifyJson(subName);
//    }
//
//    @Test
//    public void testCreateProteinDescriptionWithFlag() {
//        List<Evidence> evidences = createEvidences();
//        Name allergenName = ProteinDescriptionFactory.INSTANCE.createName("allergen", evidences);
//        Name biotechName = ProteinDescriptionFactory.INSTANCE.createName("biotech", evidences);
//        List<Name> antigenNames = new ArrayList<>();
//        antigenNames.add(ProteinDescriptionFactory.INSTANCE.createName("cd antigen", evidences));
//
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//        List<Name> shortNames = createShortNames();
//        List<EC> ecNumbers = createECNumbers();
//        ProteinName recommendedName = ProteinDescriptionFactory.INSTANCE
//                .createProteinName(fullName, shortNames, ecNumbers);
//
//        List<ProteinName> proteinAltNames = createAltName();
//        Name fullName1 = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//
//        List<EC> ecNumbers1 = createECNumbers();
//        ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName1, null, ecNumbers1);
//        List<ProteinName> subNames = new ArrayList<>();
//        subNames.add(subName);
//        ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
//        ProteinDescription description =
//                builder.recommendedName(recommendedName)
//                        .submissionNames(subNames)
//                        .cdAntigenNames(antigenNames)
//                        .alternativeNames(proteinAltNames)
//                        .flag(new FlagImpl(FlagType.PRECURSOR))
//                        .build();
//
//        assertEquals(recommendedName, description.getRecommendedName());
//        assertEquals(subNames, description.getSubmissionNames());
//        assertEquals(proteinAltNames, description.getAlternativeNames());
//        assertEquals(antigenNames, description.getCdAntigenNames());
//
//        assertEquals(FlagType.PRECURSOR, description.getFlag().getType());
//
//        TestHelper.verifyJson(description);
//
//    }
//
//
//    @Test
//    public void testCreateProteinDescription() {
//        List<Evidence> evidences = createEvidences();
//        Name allergenName = ProteinDescriptionFactory.INSTANCE.createName("allergen", evidences);
//        Name biotechName = ProteinDescriptionFactory.INSTANCE.createName("biotech", evidences);
//        List<Name> antigenNames = new ArrayList<>();
//        antigenNames.add(ProteinDescriptionFactory.INSTANCE.createName("cd antigen", evidences));
//
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//        List<Name> shortNames = createShortNames();
//        List<EC> ecNumbers = createECNumbers();
//        ProteinName recommendedName = ProteinDescriptionFactory.INSTANCE
//                .createProteinName(fullName, shortNames, ecNumbers);
//
//        List<ProteinName> proteinAltNames = createAltName();
//        Name fullName1 = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//
//        List<EC> ecNumbers1 = createECNumbers();
//        ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName1, null, ecNumbers1);
//        List<ProteinName> subNames = new ArrayList<>();
//        subNames.add(subName);
//        ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
//        ProteinDescription description =
//                builder.recommendedName(recommendedName)
//                        .submissionNames(subNames)
//                        .alternativeNames(proteinAltNames)
//                        .allergenName(allergenName)
//                        .biotechName(biotechName)
//                        .cdAntigenNames(antigenNames)
//                        .build();
//
//        assertEquals(recommendedName, description.getRecommendedName());
//        assertEquals(subNames, description.getSubmissionNames());
//        assertEquals(proteinAltNames, description.getAlternativeNames());
//
//        assertTrue(description.getIncludes().isEmpty());
//        assertTrue(description.getContains().isEmpty());
//        assertTrue(description.isValid());
//        assertEquals(allergenName, description.getAllergenName());
//        assertEquals(biotechName, description.getBiotechName());
//        assertEquals(antigenNames, description.getCdAntigenNames());
//        assertTrue(description.getInnNames().isEmpty());
//        assertNull(description.getFlag());
//
//        TestHelper.verifyJson(description);
//
//    }
//
//
//    @Test
//    public void testCreateProteinDescriptionFull() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//        List<Name> shortNames = createShortNames();
//        List<EC> ecNumbers = createECNumbers();
//        ProteinName recommendedName = ProteinDescriptionFactory.INSTANCE
//                .createProteinName(fullName, shortNames, ecNumbers);
//        List<ProteinName> proteinAltNames = createAltName();
//        Name fullName1 = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//        List<EC> ecNumbers1 = createECNumbers();
//        ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName1, null,
//                                                                                   ecNumbers1);
//
//        ProteinSection included1 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName,
//                                                                                               null);
//        ProteinSection contain1 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName,
//                                                                                              null);
//        ProteinSection contain2 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName,
//                                                                                              proteinAltNames);
//        List<ProteinSection> includes = new ArrayList<>();
//        includes.add(included1);
//        List<ProteinSection> contains = new ArrayList<>();
//        contains.add(contain1);
//        contains.add(contain2);
//        List<ProteinName> subNames = new ArrayList<>();
//        subNames.add(subName);
//        ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
//        builder.recommendedName(recommendedName)
//                .submissionNames(subNames)
//                .alternativeNames(proteinAltNames)
//                .includes(includes)
//                .contains(contains);
//
//
//        ProteinDescription description = ProteinDescriptionFactory.INSTANCE.createProteinDescription(builder);
//        assertEquals(recommendedName, description.getRecommendedName());
//        assertEquals(subNames, description.getSubmissionNames());
//        assertEquals(proteinAltNames, description.getAlternativeNames());
//        assertTrue(description.isValid());
//        assertEquals(includes, description.getIncludes());
//        assertEquals(contains, description.getContains());
//        assertTrue(description.isValid());
//
//        TestHelper.verifyJson(description);
//
//    }
//
//    private List<ProteinName> createAltName() {
//        List<ProteinName> alternativeNames = new ArrayList<>();
//        List<Evidence> evidences = createEvidences();
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full alt Name", evidences);
//        List<Name> shortNames = new ArrayList<>();
//        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
//        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
//        List<EC> ecNumbers = new ArrayList<>();
//        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
//
//        alternativeNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers));
//
//        return alternativeNames;
//    }
//
//    private List<Name> createShortNames() {
//        List<Evidence> evidences = createEvidences();
//        List<Name> shortNames = new ArrayList<>();
//        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
//        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
//        return shortNames;
//    }
//
//    private List<EC> createECNumbers() {
//        List<Evidence> evidences = createEvidences();
//        List<EC> ecNumbers = new ArrayList<>();
//        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
//        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.3.4.3", evidences));
//        return ecNumbers;
//    }
//
//    private List<Evidence> createEvidences() {
//        List<Evidence> evidences = new ArrayList<>();
//        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
//        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
//        return evidences;
//    }

}
