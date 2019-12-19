package org.uniprot.core.xml.uniprot;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;
import static org.uniprot.core.xml.uniprot.description.DescriptionHelper.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.gene.*;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.builder.*;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.CofactorBuilder;
import org.uniprot.core.uniprot.comment.builder.CofactorCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.FreeTextCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.description.*;
import org.uniprot.core.uniprot.description.builder.ProteinDescriptionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureId;
import org.uniprot.core.uniprot.feature.FeatureLocation;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.feature.builder.FeatureBuilder;
import org.uniprot.core.uniprot.feature.impl.AlternativeSequenceImpl;
import org.uniprot.core.uniprot.feature.impl.FeatureIdImpl;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismHostBuilder;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;
import org.uniprot.core.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;
import org.uniprot.core.xml.jaxb.uniprot.Entry;

class UniProtEntryConverterTest {

    @Test
    void test() {
        List<UniProtAccession> secondaryAccessions = new ArrayList<>();
        secondaryAccessions.add(new UniProtAccessionBuilder("P23456").build());
        secondaryAccessions.add(new UniProtAccessionBuilder("P23457").build());
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
                        .evidences(evidences)
                        .build());
        organelles.add(
                new GeneLocationBuilder()
                        .geneEncodingType(GeneEncodingType.PLASMID)
                        .evidences(evidences)
                        .value("some value")
                        .build());
        List<Keyword> keywords = new ArrayList<>();

        keywords.add(
                new KeywordBuilder()
                        .id("KW-001")
                        .value("key1")
                        .category(KeywordCategory.UNKNOWN)
                        .evidences(evidences)
                        .build());
        keywords.add(
                new KeywordBuilder()
                        .id("KW-002")
                        .value("key2")
                        .category(KeywordCategory.UNKNOWN)
                        .evidences(evidences)
                        .build());
        keywords.add(
                new KeywordBuilder()
                        .id("KW-003")
                        .value("key3")
                        .category(KeywordCategory.UNKNOWN)
                        .evidences(evidences)
                        .build());
        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
        Sequence sequence = new SequenceBuilder(value).build();
        UniProtEntry entry =
                new UniProtEntryBuilder("P12345", "P12345_HUMAN", UniProtEntryType.TREMBL)
                        .secondaryAccessionsSet(secondaryAccessions)
                        .organism(createOrganism())
                        .organismHostsSet(createOrganismHosts())
                        .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                        .entryAudit(entryAudit)
                        .geneLocationsSet(organelles)
                        .databaseCrossReferencesSet(createDbXref())
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
        UniProtEntry converted = converter.fromXml(xmlEntry);
        assertEquals(entry, converted);
    }

    private Organism createOrganism() {
        OrganismBuilder builder = new OrganismBuilder();
        builder.scientificName("Homo sapiens")
                .commonName("Human")
                .taxonId(9606l)
                .lineage(
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
                .evidences(createEvidences());

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
                        .synonyms(synonyms)
                        .orderedLocusNames(olnNames)
                        .orfNames(orfNames)
                        .build();
        return gene;
    }

    private List<Feature> createFeatures() {
        List<Feature> features = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        Feature featureLocation12 =
                new FeatureBuilder()
                        .type(FeatureType.TURN)
                        .location(new FeatureLocation(12, 12))
                        .description("some desc1")
                        .evidences(evidences)
                        .build();
        Feature featureLocation20 =
                new FeatureBuilder()
                        .from(featureLocation12)
                        .location(new FeatureLocation(20, 23))
                        .description("some desc2")
                        .build();
        Feature featureLocation200 =
                new FeatureBuilder()
                        .type(FeatureType.CHAIN)
                        .location(new FeatureLocation(200, 230))
                        .description("some desc3")
                        .featureId("PRO_123")
                        .evidences(evidences)
                        .build();

        features.add(createVarSeqFeature());
        features.add(featureLocation12);
        features.add(featureLocation20);
        features.add(featureLocation200);

        return features;
    }

    private Feature createVarSeqFeature() {
        FeatureLocation location = new FeatureLocation(65, 86);
        AlternativeSequence as = new AlternativeSequenceImpl("RS", Arrays.asList("DB", "AA"));
        FeatureId featureId = new FeatureIdImpl("VSP_112");

        return new FeatureBuilder()
                .type(FeatureType.VAR_SEQ)
                .location(location)
                .description("some description")
                .featureId(featureId)
                .alternativeSequence(as)
                .evidences(createEvidences())
                .build();
    }

    private List<Comment> createComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(
                new FreeTextCommentBuilder()
                        .commentType(CommentType.ALLERGEN)
                        .texts(createEvidenceValues())
                        .build());
        comments.add(
                new FreeTextCommentBuilder()
                        .commentType(CommentType.FUNCTION)
                        .texts(createEvidenceValues())
                        .build());
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =
                new CofactorBuilder()
                        .name("somename")
                        .reference(reference)
                        .evidences(createEvidences())
                        .build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        Note note = new NoteBuilder(createEvidenceValues()).build();
        String molecule = "some mol";
        CofactorComment cofactorComment =
                builder.molecule(molecule).cofactors(cofactors).note(note).build();
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
        ProteinRecName recommendedName = createProteinRecName(fullName, shortNames, ecNumbers);
        Name allergenName = createName("allergen", evidences);
        Name biotechName = createName("biotech", evidences);
        List<Name> antigenNames = new ArrayList<>();
        antigenNames.add(createName("cd antigen", evidences));

        List<ProteinAltName> proteinAltName = createAltName();
        Name fullName1 = createName("a full Name", evidences);

        List<EC> ecNumbers1 = createECNumbers();
        ProteinSubName subName = createProteinSubName(fullName1, ecNumbers1);
        List<ProteinSubName> subNames = new ArrayList<>();
        subNames.add(subName);
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        return builder.recommendedName(recommendedName)
                .alternativeNames(proteinAltName)
                .submissionNames(subNames)
                .allergenName(allergenName)
                .biotechName(biotechName)
                .cdAntigenNames(antigenNames)
                .build();
    }

    private List<ProteinAltName> createAltName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = createName("a full alt Name", evidences);
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(createName("short name1", evidences));
        shortNames.add(createName("short name2", evidences));
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(createEC("1.2.3.4", evidences));

        ProteinAltName altName = createProteinAltName(fullName, shortNames, ecNumbers);

        List<ProteinAltName> altNames = new ArrayList<>();
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

    List<UniProtDBCrossReference> createDbXref() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String type = "Ensembl";
        String id = "ENST00000393119";
        String description = "ENSP00000376827";
        String thirdAttr = "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        List<UniProtDBCrossReference> xrefs = new ArrayList<>();
        UniProtXDbType uniProtXDbType = new UniProtXDbType(type);
        xrefs.add(
                new UniProtDBCrossReferenceBuilder()
                        .databaseType(uniProtXDbType)
                        .id(id)
                        .isoformId(isoform)
                        .addProperty(uniProtXDbType.getAttribute(0), description)
                        .addProperty(uniProtXDbType.getAttribute(1), thirdAttr)
                        .addProperty(uniProtXDbType.getAttribute(2), fourthAttr)
                        .build());

        // DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.

        type = "EMBL";
        uniProtXDbType = new UniProtXDbType(type);
        id = "DQ185029";
        description = "AAZ94714.1";
        thirdAttr = "-";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(
                new UniProtDBCrossReferenceBuilder()
                        .databaseType(uniProtXDbType)
                        .id(id)
                        .isoformId(isoform)
                        .addProperty(uniProtXDbType.getAttribute(0), description)
                        .addProperty(uniProtXDbType.getAttribute(1), thirdAttr)
                        .addProperty(uniProtXDbType.getAttribute(2), fourthAttr)
                        .build());

        // DR   EMBL; AK000352; BAA91105.1; ALT_INIT; mRNA.
        type = "EMBL";
        uniProtXDbType = new UniProtXDbType(type);
        id = "AK000352";
        description = "BAA91105.1";
        thirdAttr = "ALT_INIT";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(
                new UniProtDBCrossReferenceBuilder()
                        .databaseType(uniProtXDbType)
                        .id(id)
                        .isoformId(isoform)
                        .addProperty(uniProtXDbType.getAttribute(0), description)
                        .addProperty(uniProtXDbType.getAttribute(1), thirdAttr)
                        .addProperty(uniProtXDbType.getAttribute(2), fourthAttr)
                        .build());

        // DR   EMBL; AK310815; -; NOT_ANNOTATED_CDS; mRNA.
        type = "EMBL";
        uniProtXDbType = new UniProtXDbType(type);
        id = "AK310815";
        description = "-";
        thirdAttr = "NOT_ANNOTATED_CDS";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(
                new UniProtDBCrossReferenceBuilder()
                        .databaseType(uniProtXDbType)
                        .id(id)
                        .isoformId(isoform)
                        .addProperty(uniProtXDbType.getAttribute(0), description)
                        .addProperty(uniProtXDbType.getAttribute(1), thirdAttr)
                        .addProperty(uniProtXDbType.getAttribute(2), fourthAttr)
                        .build());

        //   DR   HPA; HPA021372; -.
        type = "HPA";
        uniProtXDbType = new UniProtXDbType(type);
        id = "HPA021372";
        description = "-";
        thirdAttr = null;
        fourthAttr = null;
        isoform = null;
        xrefs.add(
                new UniProtDBCrossReferenceBuilder()
                        .databaseType(uniProtXDbType)
                        .id(id)
                        .isoformId(isoform)
                        .addProperty(uniProtXDbType.getAttribute(0), description)
                        .addProperty(uniProtXDbType.getAttribute(1), thirdAttr)
                        .addProperty(uniProtXDbType.getAttribute(2), fourthAttr)
                        .build());

        //  DR   HPA; HPA021812; -.
        type = "HPA";
        uniProtXDbType = new UniProtXDbType(type);
        id = "HPA021812";
        description = "-";
        thirdAttr = null;
        fourthAttr = null;
        isoform = null;
        xrefs.add(
                new UniProtDBCrossReferenceBuilder()
                        .databaseType(uniProtXDbType)
                        .id(id)
                        .isoformId(isoform)
                        .addProperty(uniProtXDbType.getAttribute(0), description)
                        .addProperty(uniProtXDbType.getAttribute(1), thirdAttr)
                        .addProperty(uniProtXDbType.getAttribute(2), fourthAttr)
                        .build());

        return xrefs;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(parseEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
