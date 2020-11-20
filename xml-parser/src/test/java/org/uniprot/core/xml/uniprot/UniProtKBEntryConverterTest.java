package org.uniprot.core.xml.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Sequence;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.gene.*;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.comment.impl.CofactorBuilder;
import org.uniprot.core.uniprotkb.comment.impl.CofactorCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.FeatureIdBuilder;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureBuilder;
import org.uniprot.core.uniprotkb.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismHostBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.core.xml.jaxb.uniprot.Entry;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.*;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

public class UniProtKBEntryConverterTest {

    @Test
    void test() {
        List<UniProtKBAccession> secondaryAccessions = new ArrayList<>();
        secondaryAccessions.add(new UniProtKBAccessionBuilder("P23456").build());
        secondaryAccessions.add(new UniProtKBAccessionBuilder("P23457").build());
        LocalDate firstPublicDate = LocalDate.of(2011, 10, 18);
        LocalDate lastAnnotationUpdateDate = LocalDate.of(2013, 9, 2);
        LocalDate lastSequenceUpdateDate = LocalDate.of(2014, 3, 22);
        int entryVersion = 12;
        int sequenceVersion = 5;
        EntryAudit entryAudit =
                new EntryAuditBuilder()
                        .firstPublic(firstPublicDate)
                        .lastAnnotationUpdate(lastAnnotationUpdateDate)
                        .lastSequenceUpdate(lastSequenceUpdateDate)
                        .entryVersion(entryVersion)
                        .sequenceVersion(sequenceVersion)
                        .build();
        List<GeneLocation> organelles = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        organelles.add(
                new GeneLocationBuilder()
                        .geneEncodingType(GeneEncodingType.APICOPLAST)
                        .evidencesSet(evidences)
                        .build());
        organelles.add(
                new GeneLocationBuilder()
                        .geneEncodingType(GeneEncodingType.PLASMID)
                        .evidencesSet(evidences)
                        .value("some value")
                        .build());
        List<Keyword> keywords = new ArrayList<>();

        keywords.add(
                new KeywordBuilder()
                        .id("KW-001")
                        .name("key1")
                        .category(KeywordCategory.UNKNOWN)
                        .evidencesSet(evidences)
                        .build());
        keywords.add(
                new KeywordBuilder()
                        .id("KW-002")
                        .name("key2")
                        .category(KeywordCategory.UNKNOWN)
                        .evidencesSet(evidences)
                        .build());
        keywords.add(
                new KeywordBuilder()
                        .id("KW-003")
                        .name("key3")
                        .category(KeywordCategory.UNKNOWN)
                        .evidencesSet(evidences)
                        .build());
        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
        Sequence sequence = new SequenceBuilder(value).build();
        UniProtKBEntry entry =
                new UniProtKBEntryBuilder("P12345", "P12345_HUMAN", UniProtKBEntryType.TREMBL)
                        .secondaryAccessionsSet(secondaryAccessions)
                        .organism(createOrganism())
                        .organismHostsSet(createOrganismHosts())
                        .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                        .entryAudit(entryAudit)
                        .geneLocationsSet(organelles)
                        .uniProtCrossReferencesSet(createDbXref())
                        .keywordsSet(keywords)
                        .proteinDescription(createProteinDescription())
                        .genesSet(singletonList(createGene()))
                        .commentsSet(createComments())
                        .featuresSet(createFeatures())
                        .sequence(sequence)
                        .build();
        UniProtEntryConverter converter = new UniProtEntryConverter();
        Entry xmlEntry = converter.toXml(entry);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlEntry, Entry.class, "entry"));
        UniProtKBEntry converted = converter.fromXml(xmlEntry);
        assertEquals(entry, converted);
    }

    private Organism createOrganism() {
        OrganismBuilder builder = new OrganismBuilder();
        builder.scientificName("Homo sapiens")
                .commonName("Human")
                .taxonId(9606l)
                .lineagesSet(
                        Arrays.asList(
                                "Eukaryota",
                                "Metazoa",
                                "Chordata",
                                "Craniata",
                                "Vertebrata",
                                "Euteleostomi",
                                "Mammalia",
                                "Eutheria",
                                "Euarchontoglires",
                                "Primates",
                                "Haplorrhini",
                                "Catarrhini",
                                "Hominidae",
                                "Homo"))
                .evidencesSet(createEvidences());

        return builder.build();
    }

    private List<OrganismHost> createOrganismHosts() {
        List<OrganismHost> hosts = new ArrayList<>();
        hosts.add(
                new OrganismHostBuilder()
                        .taxonId(29095l)
                        .scientificName("Akodon azarae")
                        .commonName("Azara's grass mouse")
                        .build());
        // 56211; Calomys laucha (Small vesper mouse).
        hosts.add(
                new OrganismHostBuilder()
                        .taxonId(56211l)
                        .scientificName("Calomys laucha")
                        .commonName("Small vesper mouse")
                        .build());

        return hosts;
    }

    private Gene createGene() {
        String val = "someGene";
        List<Evidence> evidences =
                Arrays.asList(new Evidence[] {parseEvidenceLine("ECO:0000256|PROSITE:PS51004")});
        GeneName geneName = new GeneNameBuilder(val, evidences).build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        String valSyn = "someSyn";
        List<Evidence> synEvidences =
                Arrays.asList(
                        new Evidence[] {
                            parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"),
                            parseEvidenceLine("ECO:0000269|PubMed:11389730")
                        });
        GeneNameSynonym synonym = new GeneNameSynonymBuilder(valSyn, synEvidences).build();

        synonyms.add(synonym);
        String valSyn2 = "Syn 2";
        List<Evidence> synEvidences2 =
                Arrays.asList(
                        new Evidence[] {
                            parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001364"),
                            parseEvidenceLine("ECO:0000269|PubMed:11389730")
                        });
        GeneNameSynonym synonym2 = new GeneNameSynonymBuilder(valSyn2, synEvidences2).build();
        synonyms.add(synonym2);
        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();

        String locusName = "some locus name";
        List<Evidence> olnEvidences =
                Arrays.asList(
                        new Evidence[] {
                            parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"),
                            parseEvidenceLine("ECO:0000269|PubMed:11389730")
                        });
        OrderedLocusName olnName = new OrderedLocusNameBuilder(locusName, olnEvidences).build();
        olnNames.add(olnName);
        Gene gene =
                new GeneBuilder()
                        .geneName(geneName)
                        .synonymsSet(synonyms)
                        .orderedLocusNamesSet(olnNames)
                        .orfNamesSet(orfNames)
                        .build();
        return gene;
    }

    private List<UniProtKBFeature> createFeatures() {
        List<UniProtKBFeature> features = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        UniProtKBFeature featureLocation12 =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.TURN)
                        .location(new FeatureLocation(12, 12))
                        .description("some desc1")
                        .evidencesSet(evidences)
                        .build();
        UniProtKBFeature featureLocation20 =
                UniProtKBFeatureBuilder.from(featureLocation12)
                        .location(new FeatureLocation(20, 23))
                        .description("some desc2")
                        .build();
        UniProtKBFeature featureLocation200 =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.CHAIN)
                        .location(new FeatureLocation(200, 230))
                        .description("some desc3")
                        .featureId("PRO_123")
                        .evidencesSet(evidences)
                        .build();

        features.add(createVarSeqFeature());
        features.add(featureLocation12);
        features.add(featureLocation20);
        features.add(featureLocation200);

        return features;
    }

    private UniProtKBFeature createVarSeqFeature() {
        FeatureLocation location = new FeatureLocation(65, 86);
        AlternativeSequence as =
                new AlternativeSequenceBuilder()
                        .original("RS")
                        .alternativeSequencesSet(Arrays.asList("DB", "AA"))
                        .build();
        FeatureId featureId = new FeatureIdBuilder("VSP_112").build();
        return new UniProtKBFeatureBuilder()
                .type(UniprotKBFeatureType.VAR_SEQ)
                .location(location)
                .description("some description")
                .featureId(featureId)
                .alternativeSequence(as)
                .evidencesSet(createEvidences())
                .build();
    }

    private List<Comment> createComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(
                new FreeTextCommentBuilder()
                        .commentType(CommentType.ALLERGEN)
                        .textsSet(createEvidenceValues())
                        .build());
        comments.add(
                new FreeTextCommentBuilder()
                        .commentType(CommentType.FUNCTION)
                        .textsSet(createEvidenceValues())
                        .build());
        CrossReference<CofactorDatabase> reference =
                new CrossReferenceBuilder<CofactorDatabase>()
                        .database(CofactorDatabase.CHEBI)
                        .id("CHEBI:324")
                        .build();
        Cofactor cofactor =
                new CofactorBuilder()
                        .name("somename")
                        .cofactorCrossReference(reference)
                        .evidencesSet(createEvidences())
                        .build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        Note note = new NoteBuilder(createEvidenceValues()).build();
        String molecule = "some mol";
        CofactorComment cofactorComment =
                builder.molecule(molecule).cofactorsSet(cofactors).note(note).build();
        comments.add(cofactorComment);
        return comments;
    }

    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(new EvidencedValueBuilder("value1", Collections.emptyList()).build());
        evidencedValues.add(new EvidencedValueBuilder("value2", Collections.emptyList()).build());
        return evidencedValues;
    }

    private ProteinDescription createProteinDescription() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinName recommendedName = createProteinRecName(fullName, shortNames, ecNumbers);
        Name allergenName = createName("allergen", evidences);
        Name biotechName = createName("biotech", evidences);
        List<Name> antigenNames = new ArrayList<>();
        antigenNames.add(createName("cd antigen", evidences));

        List<ProteinName> proteinAltName = createAltName();
        Name fullName1 = createName("a full Name", evidences);

        List<EC> ecNumbers1 = createECNumbers();
        ProteinSubName subName = createProteinSubName(fullName1, ecNumbers1);
        List<ProteinSubName> subNames = new ArrayList<>();
        subNames.add(subName);
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        return builder.recommendedName(recommendedName)
                .alternativeNamesSet(proteinAltName)
                .submissionNamesSet(subNames)
                .allergenName(allergenName)
                .biotechName(biotechName)
                .cdAntigenNamesSet(antigenNames)
                .build();
    }

    private List<ProteinName> createAltName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full alt Name", evidences);
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(createName("short name1", evidences));
        shortNames.add(createName("short name2", evidences));
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(createEC("1.2.3.4", evidences));

        ProteinName altName = createProteinAltName(fullName, shortNames, ecNumbers);

        List<ProteinName> altNames = new ArrayList<>();
        altNames.add(altName);
        return altNames;
    }

    private List<Name> createShortNames() {
        List<Evidence> evidences = createEvidences();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(createName("short name1", evidences));
        shortNames.add(createName("short name2", evidences));
        return shortNames;
    }

    private List<EC> createECNumbers() {
        List<Evidence> evidences = createEvidences();
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(createEC("1.2.3.4", evidences));
        ecNumbers.add(createEC("1.3.4.3", evidences));
        return ecNumbers;
    }

    List<UniProtKBCrossReference> createDbXref() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String type = "Ensembl";
        String id = "ENST00000393119";
        String description = "ENSP00000376827";
        String thirdAttr = "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        UniProtKBDatabase uniProtkbDatabase = new UniProtKBDatabaseImpl(type);
        xrefs.add(
                new UniProtCrossReferenceBuilder()
                        .database(uniProtkbDatabase)
                        .id(id)
                        .isoformId(isoform)
                        .propertiesAdd(
                                uniProtkbDatabase.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(2), fourthAttr)
                        .build());

        // DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.

        type = "EMBL";
        uniProtkbDatabase = new UniProtKBDatabaseImpl(type);
        id = "DQ185029";
        description = "AAZ94714.1";
        thirdAttr = "-";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(
                new UniProtCrossReferenceBuilder()
                        .database(uniProtkbDatabase)
                        .id(id)
                        .isoformId(isoform)
                        .propertiesAdd(
                                uniProtkbDatabase.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(2), fourthAttr)
                        .build());

        // DR   EMBL; AK000352; BAA91105.1; ALT_INIT; mRNA.
        type = "EMBL";
        uniProtkbDatabase = new UniProtKBDatabaseImpl(type);
        id = "AK000352";
        description = "BAA91105.1";
        thirdAttr = "ALT_INIT";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(
                new UniProtCrossReferenceBuilder()
                        .database(uniProtkbDatabase)
                        .id(id)
                        .isoformId(isoform)
                        .propertiesAdd(
                                uniProtkbDatabase.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(2), fourthAttr)
                        .build());

        // DR   EMBL; AK310815; -; NOT_ANNOTATED_CDS; mRNA.
        type = "EMBL";
        uniProtkbDatabase = new UniProtKBDatabaseImpl(type);
        id = "AK310815";
        description = "-";
        thirdAttr = "NOT_ANNOTATED_CDS";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(
                new UniProtCrossReferenceBuilder()
                        .database(uniProtkbDatabase)
                        .id(id)
                        .isoformId(isoform)
                        .propertiesAdd(
                                uniProtkbDatabase.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(2), fourthAttr)
                        .build());

        //   DR   HPA; HPA021372; -.
        type = "HPA";
        uniProtkbDatabase = new UniProtKBDatabaseImpl(type);
        id = "HPA021372";
        description = "-";
        thirdAttr = null;
        fourthAttr = null;
        isoform = null;
        xrefs.add(
                new UniProtCrossReferenceBuilder()
                        .database(uniProtkbDatabase)
                        .id(id)
                        .isoformId(isoform)
                        .propertiesAdd(
                                uniProtkbDatabase.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(2), fourthAttr)
                        .build());

        //  DR   HPA; HPA021812; -.
        type = "HPA";
        uniProtkbDatabase = new UniProtKBDatabaseImpl(type);
        id = "HPA021812";
        description = "-";
        thirdAttr = null;
        fourthAttr = null;
        isoform = null;
        xrefs.add(
                new UniProtCrossReferenceBuilder()
                        .database(uniProtkbDatabase)
                        .id(id)
                        .isoformId(isoform)
                        .propertiesAdd(
                                uniProtkbDatabase.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(2), fourthAttr)
                        .build());

        return xrefs;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(parseEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    public static UniProtKBCrossReference createUniProtKBCrossReference() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String type = "Ensembl";
        String id = "ENST00000393119";
        String description = "ENSP00000376827";
        String thirdAttr = "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        UniProtKBDatabase uniProtkbDatabase = new UniProtKBDatabaseImpl(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(uniProtkbDatabase)
                        .id(id)
                        .isoformId(isoform)
                        .propertiesAdd(
                                uniProtkbDatabase.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(uniProtkbDatabase.getUniProtDatabaseAttribute(2), fourthAttr)
                        .build();
        return xref;
    }
}
