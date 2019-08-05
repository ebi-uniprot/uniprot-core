package org.uniprot.core.uniprot.factory;

public class UniProtEntryBuilderTest {
//
//    @Test
//    public void testNewInstance() {
//        UniProtEntryBuilder builder1 = UniProtEntryBuilder.newInstance();
//        UniProtEntryBuilder builder2 = UniProtEntryBuilder.newInstance();
//        assertNotNull(builder1);
//        assertNotNull(builder2);
//        assertFalse(builder1 == builder2);
//    }
//
//
//    @Test
//    public void testSetEntryType() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry =
//                builder.entryType(UniProtEntryType.TREMBL)
//                        .build();
//
//        assertEquals(UniProtEntryType.TREMBL, entry.getEntryType());
//
//        builder = UniProtEntryBuilder.newInstance();
//        entry =
//                builder.entryType(UniProtEntryType.SWISSPROT)
//                        .build();
//        assertEquals(UniProtEntryType.SWISSPROT, entry.getEntryType());
//        assertTrue(entry.isActive());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetAccession() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNull(entry.getPrimaryAccession());
//
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .primaryAccession("P12345")
//                .build();
//        assertNotNull(entry.getPrimaryAccession());
//        assertEquals("P12345", entry.getPrimaryAccession().getValue());
//    }
//
//    @Test
//    public void testSetAccession2() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNull(entry.getPrimaryAccession());
//        UniProtAccession accession = UniProtFactory.INSTANCE.createUniProtAccession("P23456");
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .primaryAccession(accession)
//                .build();
//        assertNotNull(entry.getPrimaryAccession());
//        assertEquals(accession, entry.getPrimaryAccession());
//        assertEquals("P23456", entry.getPrimaryAccession().getValue());
//        TestHelper.verifyJson(entry);
//    }
//
//
//    @Test
//    public void testSetSecondaryAccessions() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertTrue(entry.getSecondaryAccessions().isEmpty());
//
//        List<UniProtAccession> secondaryAccessions = new ArrayList<>();
//        secondaryAccessions.add(UniProtFactory.INSTANCE.createUniProtAccession("P23456"));
//        secondaryAccessions.add(UniProtFactory.INSTANCE.createUniProtAccession("P23457"));
//
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .secondaryAccessions(secondaryAccessions)
//                .build();
//        assertEquals(2, entry.getSecondaryAccessions().size());
//        assertEquals(secondaryAccessions, entry.getSecondaryAccessions());
//        TestHelper.verifyJson(entry);
//
//    }
//
//    @Test
//    public void testSetUniProtId() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNull(entry.getUniProtId());
//
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .uniProtId("P12345_HUMAN")
//                .build();
//        assertNotNull(entry.getUniProtId());
//        assertEquals("P12345_HUMAN", entry.getUniProtId().getValue());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetUniProtId2() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNull(entry.getUniProtId());
//        UniProtId uniprotId = UniProtFactory.INSTANCE.createUniProtId("P12346_HUMAN");
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .uniProtId(uniprotId)
//                .build();
//        assertNotNull(entry.getUniProtId());
//        assertEquals("P12346_HUMAN", entry.getUniProtId().getValue());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetProteinExistence() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertEquals(ProteinExistence.UNKNOWN, entry.getProteinExistence());
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
//                .build();
//        assertEquals(ProteinExistence.PROTEIN_LEVEL, entry.getProteinExistence());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetEntryAudit() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertEquals(null, entry.getEntryAudit());
//        LocalDate firstPublicDate = LocalDate.of(2011, 10, 18);
//        LocalDate lastAnnotationUpdateDate = LocalDate.of(2013, 9, 2);
//        LocalDate lastSequenceUpdateDate = LocalDate.of(2014, 3, 22);
//        int entryVersion = 12;
//        int sequenceVersion = 5;
//        EntryAudit entryAudit = UniProtFactory.INSTANCE.createEntryAudit(firstPublicDate,
//                                                                         lastAnnotationUpdateDate, lastSequenceUpdateDate, entryVersion, sequenceVersion);
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .entryAudit(entryAudit)
//                .build();
//        assertEquals(entryAudit, entry.getEntryAudit());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetOrganelles() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertTrue(entry.getOrganelles().isEmpty());
//        List<Organelle> organelles = new ArrayList<>();
//        List<Evidence> evidences = createEvidences();
//        organelles.add(UniProtFactory.INSTANCE.createOrganelle(GeneEncodingType.APICOPLAST_PLASTID, null, evidences));
//        organelles.add(UniProtFactory.INSTANCE
//                               .createOrganelle(GeneEncodingType.CYANELLE_PLASTID, "some value", evidences));
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .organelles(organelles)
//                .build();
//        assertEquals(2, entry.getOrganelles().size());
//        assertEquals(organelles, entry.getOrganelles());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetKeywords() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertTrue(entry.getKeywords().isEmpty());
//        List<Keyword> keywords = new ArrayList<>();
//        List<Evidence> evidences = createEvidences();
//        keywords.add(UniProtFactory.INSTANCE.createKeyword("KW-001", "key1", evidences));
//        keywords.add(UniProtFactory.INSTANCE.createKeyword("KW-002", "key2", evidences));
//        keywords.add(UniProtFactory.INSTANCE.createKeyword("KW-003", "key3", evidences));
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .keywords(keywords)
//                .build();
//        assertEquals(3, entry.getKeywords().size());
//        assertEquals(keywords, entry.getKeywords());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetProteinDescription() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertEquals(null, entry.getProteinDescription());
//        ProteinDescription proteinDescription = createProteinDescription();
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .proteinDescription(proteinDescription)
//                .build();
//        assertEquals(proteinDescription, entry.getProteinDescription());
//        TestHelper.verifyJson(entry);
//    }
//
////    @Test
////    public void testSetComments() {
////        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
////        UniProtEntry entry = builder.build();
////        assertNotNull(entry.getComments());
////        assertTrue(entry.getComments().isEmpty());
////
////        List<Comment> comments = createComments();
////        builder = UniProtEntryBuilder.newInstance();
////        entry = builder.comments(comments).build();
////        assertNotNull(entry.getComments());
////        assertEquals(3, entry.getComments().size());
////        assertEquals(1, entry.getCommentByType(CommentType.FUNCTION).size());
////        assertEquals(1, entry.getCommentByType(CommentType.COFACTOR).size());
////        assertEquals(0, entry.getCommentByType(CommentType.DISEASE).size());
////        assertEquals(comments, entry.getComments());
////        TestHelper.verifyJson(entry);
////
////    }
//
//    @Test
//    public void testSetOrganism() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNull(entry.getOrganism());
//        long taxId = 9606;
//        String scientificName = "Homo sapiens";
//        String commonName = "Human";
//        Organism organism = new OrganismBuilder()
//                .taxonId(taxId)
//                .scientificName(scientificName)
//                .commonName(commonName).build();
//
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .organism(organism)
//                .build();
//        assertNotNull(entry.getOrganism());
//        assertEquals(organism, entry.getOrganism());
//        TestHelper.verifyJson(entry);
//
//    }
//
//    @Test
//    public void testSetOrganismHosts() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertTrue(entry.getOrganismHosts().isEmpty());
//        String scientificName = "Homo sapiens";
//        String commonName = "Human";
//        OrganismHost organismHost = new OrganismHostBuilder()
//                .scientificName(scientificName)
//                .commonName(commonName).build();
//        List<OrganismHost> organismHosts = new ArrayList<>();
//        organismHosts.add(
//                organismHost
//        );
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .organismHosts(organismHosts)
//                .build();
//        assertEquals(1, entry.getOrganismHosts().size());
//        assertEquals(organismHosts, entry.getOrganismHosts());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetUniProtDatabaseCrossReferences() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNotNull(entry.getDatabaseCrossReferences());
//        assertTrue(entry.getDatabaseCrossReferences().isEmpty());
//        List<UniProtDBCrossReference> xrefs = createDbXref();
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .uniProtDBCrossReferences(xrefs)
//                .build();
//        assertNotNull(entry.getDatabaseCrossReferences());
//        assertEquals(6, entry.getDatabaseCrossReferences().size());
//        assertEquals(2, entry.getDatabaseCrossReferencesByType(new UniProtXDbType("HPA")).size());
//        assertEquals(3, entry.getDatabaseCrossReferencesByType("EMBL").size());
//        assertEquals(1, entry.getDatabaseCrossReferencesByType("Ensembl").size());
//        TestHelper.verifyJson(entry);
//
//    }
//
//    public List<UniProtDBCrossReference> createDbXref() {
//        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
//        String type = "Ensembl";
//        String id = "ENST00000393119";
//        String description = "ENSP00000376827";
//        String thirdAttr = "ENSG00000011143";
//        String fourthAttr = null;
//        String isoform = "Q9NXB0-1";
//        List<UniProtDBCrossReference> xrefs = new ArrayList<>();
//        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
//                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
//
//        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.
//
//
//        type = "EMBL";
//        id = "DQ185029";
//        description = "AAZ94714.1";
//        thirdAttr = "-";
//        fourthAttr = "mRNA";
//        isoform = null;
//        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
//                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
//        // DR   EMBL; AK000352; BAA91105.1; ALT_INIT; mRNA.
//        type = "EMBL";
//        id = "AK000352";
//        description = "BAA91105.1";
//        thirdAttr = "ALT_INIT";
//        fourthAttr = "mRNA";
//        isoform = null;
//        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
//                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
//        // DR   EMBL; AK310815; -; NOT_ANNOTATED_CDS; mRNA.
//        type = "EMBL";
//        id = "AK310815";
//        description = "-";
//        thirdAttr = "NOT_ANNOTATED_CDS";
//        fourthAttr = "mRNA";
//        isoform = null;
//        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
//                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
//
//        //   DR   HPA; HPA021372; -.
//        type = "HPA";
//        id = "HPA021372";
//        description = "-";
//        thirdAttr = null;
//        fourthAttr = null;
//        isoform = null;
//        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
//                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
//        //  DR   HPA; HPA021812; -.
//        type = "HPA";
//        id = "HPA021812";
//        description = "-";
//        thirdAttr = null;
//        fourthAttr = null;
//        isoform = null;
//        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
//                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
//
//        return xrefs;
//
//    }
//
//    @Test
//    public void testSetSequence() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNull(entry.getSequence());
//        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
//        Sequence sequence = UniProtFactory.INSTANCE.createSequence(value);
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .sequence(sequence)
//                .build();
//        assertNotNull(entry.getSequence());
//        assertEquals(sequence, entry.getSequence());
//        assertEquals(value, entry.getSequence().getValue());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetSequence2() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNull(entry.getSequence());
//        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
//
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .sequence(value)
//                .build();
//        assertNotNull(entry.getSequence());
//        assertEquals(value, entry.getSequence().getValue());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetInternalSection() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNull(entry.getInternalSection());
//        InternalSection internalSection = createInternalSection();
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .internalSection(internalSection)
//                .build();
//        assertNotNull(entry.getInternalSection());
//        assertEquals(internalSection, entry.getInternalSection());
//        TestHelper.verifyJson(entry);
//    }
//
//    @Test
//    public void testSetFeatures() {
//        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
//        UniProtEntry entry = builder
//                .build();
//        assertNotNull(entry.getFeatures());
//        assertTrue(entry.getFeatures().isEmpty());
//        List<Feature> features = createFeatures();
//        builder = UniProtEntryBuilder.newInstance();
//        entry = builder
//                .features(features)
//                .build();
//        assertNotNull(entry.getFeatures());
//        assertEquals(4, entry.getFeatures().size());
//        assertEquals(features, entry.getFeatures());
//        TestHelper.verifyJson(entry);
//    }
//
//    private ProteinDescription createProteinDescription() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//        List<Name> shortNames = createShortNames();
//        List<EC> ecNumbers = createECNumbers();
//        ProteinName recommendedName = ProteinDescriptionFactory.INSTANCE
//                .createProteinName(fullName, shortNames, ecNumbers);
//        Name allergenName = ProteinDescriptionFactory.INSTANCE.createName("allergen", evidences);
//        Name biotechName = ProteinDescriptionFactory.INSTANCE.createName("biotech", evidences);
//        List<Name> antigenNames = new ArrayList<>();
//        antigenNames.add(ProteinDescriptionFactory.INSTANCE.createName("cd antigen", evidences));
//
//
//        List<ProteinName> proteinAltName = createAltName();
//        Name fullName1 = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
//
//        List<EC> ecNumbers1 = createECNumbers();
//        ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName1, null, ecNumbers1);
//        List<ProteinName> subNames = new ArrayList<>();
//        subNames.add(subName);
//        ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
//        return builder.recommendedName(recommendedName)
//                .alternativeNames(proteinAltName)
//                .submissionNames(subNames)
//                .allergenName(allergenName)
//                .biotechName(biotechName)
//                .cdAntigenNames(antigenNames).build();
//
//
//    }
//
//    private List<ProteinName> createAltName() {
//        List<Evidence> evidences = createEvidences();
//        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full alt Name", evidences);
//        List<Name> shortNames = new ArrayList<>();
//        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
//        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
//        List<EC> ecNumbers = new ArrayList<>();
//        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
//
//        ProteinName altName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
//
//        List<ProteinName> altNames = new ArrayList<>();
//        altNames.add(altName);
//        return altNames;
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
////    private List<Comment> createComments() {
////        CommentFactory commentFactory = UniProtFactory.INSTANCE.getCommentFactory();
////        List<Comment> comments = new ArrayList<>();
////        comments.add(new FreeTextCommentBuilder()
////                             .commentType(CommentType.ALLERGEN)
////                             .texts(createEvidenceValues()).build());
////        FreeTextCommentBuilder ftcBuilder = commentFactory.createFreeTextCommentBuilder();
////        ftcBuilder.commentType(CommentType.FUNCTION)
////                .texts(createEvidenceValues());
////        comments.add(commentFactory.createComment(ftcBuilder));
////        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
////        Cofactor cofactor = CofactorCommentBuilder.createCofactor("somename", reference, createEvidences());
////        List<Cofactor> cofactors = Arrays.asList(cofactor);
////        CofactorCommentBuilder builder = commentFactory.createCofactorCommentBuilder();
////        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
////        String molecule = "some mol";
////        CofactorComment cofactorComment =
////                builder.molecule(molecule)
////                        .cofactors(cofactors)
////                        .note(note)
////                        .build();
////        comments.add(cofactorComment);
////        return comments;
////    }
//
//    private List<EvidencedValue> createEvidenceValues() {
//        List<EvidencedValue> evidencedValues = new ArrayList<>();
//        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
//        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
//        return evidencedValues;
//    }
//
//
//    private Evidence createEvidence(String evidenceStr) {
//        return UniProtFactory.INSTANCE.createEvidence(evidenceStr);
//    }
//
//    private InternalSection createInternalSection() {
//        List<InternalLine> internalLines = new ArrayList<>();
//        internalLines.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.CP, "Val1"));
//        internalLines.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.HA, "Val2"));
//        List<SourceLine> sourceLines = new ArrayList<>();
//        sourceLines.add(UniProtFactory.INSTANCE.createSourceLine("source1"));
//        return UniProtFactory.INSTANCE.createInternalSection(internalLines, null, sourceLines);
//    }
//
//    private List<Feature> createFeatures() {
//        List<Feature> features = new ArrayList<>();
//        List<Evidence> evidences = createEvidences();
//        features.add(createVarSeqFeature());
//        features.add(new FeatureImpl(FeatureType.TURN,
//                                     new Range(12, 12),
//                                     "some desc1", evidences));
//
//        features.add(new FeatureImpl(FeatureType.TURN,
//                                     new Range(20, 23),
//                                     "some desc2", evidences));
//
//        features.add(
//                new FeatureImpl(FeatureType.CHAIN,
//                                new Range(200, 230),
//                                "some desc3", new FeatureIdImpl("PRO_123"), evidences));
//
//        return features;
//    }
//
//    private Feature createVarSeqFeature() {
//        Range location = new Range(65, 86);
//        AlternativeSequence as = new AlternativeSequenceImpl("RS", Arrays.asList("DB", "AA"));
//        FeatureId featureId = FeatureFactory.INSTANCE.createFeatureId("VSP_112");
//
//        return new FeatureImpl(FeatureType.VAR_SEQ, location, "Some description",
//                               featureId, as, null,
//                               createEvidences());
//
//
//    }
//
//    private List<Evidence> createEvidences() {
//        List<Evidence> evidences = new ArrayList<>();
//        evidences.add(createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
//        evidences.add(createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
//        return evidences;
//    }
}
