package uk.ac.ebi.uniprot.xml.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.builder.SequenceBuilder;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismHostBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.builder.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinDescriptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.Entry;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtEntryConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;
import static uk.ac.ebi.uniprot.xml.uniprot.description.DescriptionHelper.*;

class UniProtEntryConverterTest {

    @Test
    void test() {
        UniProtEntryBuilder builder = new UniProtEntryBuilder();
        List<UniProtAccession> secondaryAccessions = new ArrayList<>();
        secondaryAccessions.add(new UniProtAccessionBuilder("P23456").build());
        secondaryAccessions.add(new UniProtAccessionBuilder("P23457").build());
        LocalDate firstPublicDate = LocalDate.of(2011, 10, 18);
        LocalDate lastAnnotationUpdateDate = LocalDate.of(2013, 9, 2);
        LocalDate lastSequenceUpdateDate = LocalDate.of(2014, 3, 22);
        int entryVersion = 12;
        int sequenceVersion = 5;
        EntryAudit entryAudit = new EntryAuditBuilder().firstPublic(firstPublicDate)
                .lastAnnotationUpdate(lastAnnotationUpdateDate).lastSequenceUpdate(lastSequenceUpdateDate)
                .entryVersion(entryVersion).sequenceVersion(sequenceVersion).build();
        List<GeneLocation> organelles = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        organelles.add(new GeneLocationBuilder(GeneEncodingType.APICOPLAST_PLASTID, null, evidences).build());
        organelles.add(new GeneLocationBuilder(GeneEncodingType.PLASMID, "some value", evidences).build());
        List<Keyword> keywords = new ArrayList<>();

        keywords.add(new KeywordBuilder("KW-001", "key1", evidences).build());
        keywords.add(new KeywordBuilder("KW-002", "key2", evidences).build());
        keywords.add(new KeywordBuilder("KW-003", "key3", evidences).build());
        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
        Sequence sequence = new SequenceBuilder(value).build();
        UniProtEntry entry =
                builder.primaryAccession(new UniProtAccessionBuilder("P12345").build())
                        .uniProtId(new UniProtIdBuilder("P12345_HUMAN").build())
                        .active()
                        .entryType(UniProtEntryType.TREMBL)
                        .secondaryAccessions(secondaryAccessions)
                        .organism(createOrganism())
                        .organismHosts(createOrganismHosts())
                        .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                        .entryAudit(entryAudit)
                        .geneLocations(organelles)
                        .databaseCrossReferences(createDbXref())
                        .keywords(keywords)
                        .proteinDescription(createProteinDescription())
                        .genes(singletonList(createGene()))
                        .comments(createComments())
                        .features(createFeatures())
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
                .lineage(Arrays.asList("Eukaryota", "Metazoa", "Chordata",
                                       "Craniata", "Vertebrata", "Euteleostomi",
                                       "Mammalia", "Eutheria", "Euarchontoglires", "Primates", "Haplorrhini",
                                       "Catarrhini", "Hominidae", "Homo"
                ))
                .evidences(createEvidences());

        return builder.build();
    }

    private List<OrganismHost> createOrganismHosts() {
        List<OrganismHost> hosts = new ArrayList<>();
        hosts.add(new OrganismHostBuilder().taxonId(29095l)
                          .scientificName("Akodon azarae")
                          .commonName("Azara's grass mouse")
                          .build());
        //56211; Calomys laucha (Small vesper mouse).
        hosts.add(new OrganismHostBuilder().taxonId(56211l)
                          .scientificName("Calomys laucha")
                          .commonName("Small vesper mouse")
                          .build());

        return hosts;

    }

    private Gene createGene() {
        String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                parseEvidenceLine("ECO:0000256|PROSITE:PS51004")
        });
        GeneName geneName = new GeneNameBuilder(val, evidences).build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        String valSyn = "someSyn";
        List<Evidence> synEvidences = Arrays.asList(new Evidence[]{
                parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"),
                parseEvidenceLine("ECO:0000269|PubMed:11389730")
        });
        GeneNameSynonym synonym = new GeneNameSynonymBuilder(valSyn, synEvidences).build();


        synonyms.add(synonym);
        String valSyn2 = "Syn 2";
        List<Evidence> synEvidences2 = Arrays.asList(new Evidence[]{
                parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001364"),
                parseEvidenceLine("ECO:0000269|PubMed:11389730")
        });
        GeneNameSynonym synonym2 = new GeneNameSynonymBuilder(valSyn2, synEvidences2).build();
        synonyms.add(synonym2);
        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();

        String locusName = "some locus name";
        List<Evidence> olnEvidences = Arrays.asList(new Evidence[]{
                parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"),
                parseEvidenceLine("ECO:0000269|PubMed:11389730")
        });
        OrderedLocusName olnName = new OrderedLocusNameBuilder(locusName, olnEvidences).build();
        olnNames.add(olnName);
        Gene gene = new GeneBuilder().geneName(geneName).synonyms(synonyms).orderedLocusNames(olnNames)
                .orfNames(orfNames).build();
        return gene;

    }

    private List<Feature> createFeatures() {
        List<Feature> features = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        features.add(createVarSeqFeature());
        features.add(new FeatureImpl(FeatureType.TURN,
                                     new Range(12, 12),
                                     "some desc1", evidences));

        features.add(new FeatureImpl(FeatureType.TURN,
                                     new Range(20, 23),
                                     "some desc2", evidences));

        features.add(
                new FeatureImpl(FeatureType.CHAIN,
                                new Range(200, 230),
                                "some desc3", new FeatureIdImpl("PRO_123"), evidences));

        return features;
    }

    private Feature createVarSeqFeature() {
        Range location = new Range(65, 86);
        AlternativeSequence as = new AlternativeSequenceImpl("RS", Arrays.asList("DB", "AA"));
        FeatureId featureId = new FeatureIdImpl("VSP_112");

        return new FeatureImpl(FeatureType.VAR_SEQ, location, "some description",
                               featureId, as, null,
                               createEvidences());
    }

    private List<Comment> createComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new FreeTextCommentBuilder()
                             .commentType(CommentType.ALLERGEN)
                             .texts(createEvidenceValues())
                             .build());
        comments.add(new FreeTextCommentBuilder()
                             .commentType(CommentType.FUNCTION)
                             .texts(createEvidenceValues()).build());
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor = new CofactorBuilder().name("somename").reference(reference).evidences(createEvidences())
                .build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        Note note = new NoteBuilder(createEvidenceValues()).build();
        String molecule = "some mol";
        CofactorComment cofactorComment =
                builder.molecule(molecule)
                        .cofactors(cofactors)
                        .note(note)
                        .build();
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
                .cdAntigenNames(antigenNames).build();


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


    public List<UniProtDBCrossReference> createDbXref() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String type = "Ensembl";
        String id = "ENST00000393119";
        String description = "ENSP00000376827";
        String thirdAttr = "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        List<UniProtDBCrossReference> xrefs = new ArrayList<>();
        UniProtXDbType uniProtXDbType = new UniProtXDbType(type);
        xrefs.add(new UniProtDBCrossReferenceBuilder()
                          .databaseType(uniProtXDbType)
                          .id(id)
                          .isoformId(isoform)
                          .addProperty(uniProtXDbType.getAttribute(0), description)
                          .addProperty(uniProtXDbType.getAttribute(1), thirdAttr)
                          .addProperty(uniProtXDbType.getAttribute(2), fourthAttr)
                          .build());

        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.


        type = "EMBL";
        uniProtXDbType = new UniProtXDbType(type);
        id = "DQ185029";
        description = "AAZ94714.1";
        thirdAttr = "-";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(new UniProtDBCrossReferenceBuilder()
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
        xrefs.add(new UniProtDBCrossReferenceBuilder()
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
        xrefs.add(new UniProtDBCrossReferenceBuilder()
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
        xrefs.add(new UniProtDBCrossReferenceBuilder()
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
        xrefs.add(new UniProtDBCrossReferenceBuilder()
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
