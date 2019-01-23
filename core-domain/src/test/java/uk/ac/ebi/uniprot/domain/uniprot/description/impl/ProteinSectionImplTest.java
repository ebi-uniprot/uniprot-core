package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

class ProteinSectionImplTest {

//    @Test
//    void testFull() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = new NameImpl("a full Name", evidences);
//        List<Name> shortNames = createShortNames();
//        ProteinName recName = new ProteinNameImpl(fullName, shortNames, null);
//        List<ProteinName> altNames = createAltName();
//        ProteinSectionImpl section = new ProteinSectionImpl(recName, altNames);
//        assertEquals(recName, section.getRecommendedName());
//        assertEquals(altNames, section.getAlternativeNames());
//
//        TestHelper.verifyJson(section);
//    }
//
//    @Test
//    void testOnlyRecName() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = new NameImpl("a full Name", evidences);
//        List<Name> shortNames = createShortNames();
//        ProteinName recName = new ProteinNameImpl(fullName, shortNames, null);
//        List<ProteinName> altNames = null;
//        ProteinSectionImpl section = new ProteinSectionImpl(recName, altNames);
//        assertEquals(recName, section.getRecommendedName());
//        assertEquals(0, section.getAlternativeNames().size());
//
//        TestHelper.verifyJson(section);
//    }
//
//    private List<Name> createShortNames() {
//        List<Evidence> evidences = createEvidences();
//        List<Name> shortNames = new ArrayList<>();
//        shortNames.add(new NameImpl("short name1", evidences));
//        shortNames.add(new NameImpl("short name2", evidences));
//        return shortNames;
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
}
