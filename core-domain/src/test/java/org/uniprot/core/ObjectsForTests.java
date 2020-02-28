package org.uniprot.core;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.builder.AbstractCitationBuilder;
import org.uniprot.core.citation.builder.JournalArticleBuilder;
import org.uniprot.core.citation.builder.LiteratureBuilder;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.LiteratureStoreEntry;
import org.uniprot.core.literature.builder.LiteratureEntryBuilder;
import org.uniprot.core.literature.builder.LiteratureMappedReferenceBuilder;
import org.uniprot.core.literature.builder.LiteratureStatisticsBuilder;
import org.uniprot.core.literature.builder.LiteratureStoreEntryBuilder;
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.impl.*;
import org.uniprot.core.taxonomy.*;
import org.uniprot.core.taxonomy.builder.*;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;
import org.uniprot.core.uniparc.builder.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.builder.UniParcDBCrossReferenceBuilder;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.*;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.builder.ECBuilder;
import org.uniprot.core.uniprot.description.impl.NameImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;

public class ObjectsForTests {
    public static Reaction createReaction() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        List<DBCrossReference<ReactionDatabase>> references = new ArrayList<>();
        references.add(new DBCrossReferenceImpl<>(ReactionDatabase.RHEA, "RHEA:123"));
        references.add(new DBCrossReferenceImpl<>(ReactionDatabase.RHEA, "RHEA:323"));
        references.add(new DBCrossReferenceImpl<>(ReactionDatabase.CHEBI, "ChEBI:3243"));
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
        return new ReactionBuilder()
                .name(name)
                .reactionReferencesSet(references)
                .ecNumber(ecNumber)
                .evidencesSet(evidences)
                .build();
    }

    public static PhysiologicalReaction createPhyReaction() {
        return createPhyReactions().get(0);
    }

    public static List<PhysiologicalReaction> createPhyReactions() {
        List<PhysiologicalReaction> phyReactions = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        phyReactions.add(
                new PhysiologicalReactionBuilder()
                        .directionType(PhysiologicalDirectionType.LEFT_TO_RIGHT)
                        .reactionReference(
                                new DBCrossReferenceBuilder<ReactionDatabase>()
                                        .databaseType(ReactionDatabase.RHEA)
                                        .id("RHEA:123")
                                        .build())
                        .evidencesSet(evidences)
                        .build());
        phyReactions.add(
                new PhysiologicalReactionBuilder()
                        .directionType(PhysiologicalDirectionType.RIGHT_TO_LEFT)
                        .reactionReference(
                                new DBCrossReferenceBuilder<ReactionDatabase>()
                                        .databaseType(ReactionDatabase.RHEA)
                                        .id("RHEA:313")
                                        .build())
                        .evidencesSet(evidences)
                        .build());
        return phyReactions;
    }

    public static Note createNote() {
        List<EvidencedValue> texts = new ArrayList<>();
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("Ensembl")
                        .databaseId("ENSP0001324")
                        .evidenceCode(EvidenceCode.ECO_0000313)
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("PIRNR")
                        .databaseId("PIRNR001361")
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .build());
        texts.add(new EvidencedValueBuilder("value 1", evidences).build());
        texts.add(new EvidencedValueBuilder("value2", emptyList()).build());
        return new NoteBuilder(texts).build();
    }

    public static List<IsoformName> createSynonyms() {
        List<IsoformName> synonyms = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        synonyms.add(new IsoformNameBuilder("Syn 1", evidences).build());
        synonyms.add(new IsoformNameBuilder("Syn 2", evidences).build());
        return synonyms;
    }

    public static Evidence createEvidence() {
        return createEvidences().get(0);
    }

    public static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("PROSITE-ProRule")
                        .databaseId("PRU10028")
                        .evidenceCode(EvidenceCode.ECO_0000255)
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("PIRNR")
                        .databaseId("PIRNR001361")
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .build());
        return evidences;
    }

    public static List<EvidencedValue> evidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(
                new EvidencedValueBuilder(
                                "value1",
                                asList(
                                        new EvidenceBuilder()
                                                .databaseId("ENSP0001324")
                                                .databaseName("Ensembl")
                                                .evidenceCode(EvidenceCode.ECO_0000313)
                                                .build(),
                                        new EvidenceBuilder()
                                                .databaseId("PIRNR001361")
                                                .databaseName("PIRNR")
                                                .evidenceCode(EvidenceCode.ECO_0000256)
                                                .build()))
                        .build());
        evidencedValues.add(
                new EvidencedValueBuilder(
                                "value2",
                                singletonList(
                                        new EvidenceBuilder()
                                                .databaseId("ENSP0001324")
                                                .databaseName("Ensembl")
                                                .evidenceCode(EvidenceCode.ECO_0000313)
                                                .build()))
                        .build());
        return evidencedValues;
    }

    public static List<EvidencedValue> createEvidenceValuesWithoutEvidences() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(new EvidencedValueBuilder("value1", emptyList()).build());
        evidencedValues.add(new EvidencedValueBuilder("value2", emptyList()).build());
        return evidencedValues;
    }

    public static List<EvidencedValue> createEvidenceValuesWithEvidences() {
        List<Evidence> evidences1 = new ArrayList<>();
        evidences1.add(
                new EvidenceBuilder()
                        .databaseName("Ensembl")
                        .databaseId("ENSP0001324")
                        .evidenceCode(EvidenceCode.ECO_0000313)
                        .build());
        evidences1.add(
                new EvidenceBuilder()
                        .databaseName("PIRNR")
                        .databaseName("PIRNR001361")
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .build());

        List<Evidence> evidences2 = new ArrayList<>();
        evidences1.add(
                new EvidenceBuilder()
                        .databaseName("Ensembl")
                        .databaseId("ENSP0001325")
                        .evidenceCode(EvidenceCode.ECO_0000313)
                        .build());

        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(new EvidencedValueBuilder("value1", evidences1).build());
        evidencedValues.add(new EvidencedValueBuilder("value2", evidences2).build());
        return evidencedValues;
    }

    public static List<SequenceFeature> sequenceFeatures() {
        List<Location> locations = Arrays.asList(new Location(12, 23), new Location(45, 89));
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId2")
                        .locationsSet(locations)
                        .build();
        SequenceFeature sf3 =
                SequenceFeatureBuilder.from(sf).signatureDbType(SignatureDbType.PROSITE).build();
        return Arrays.asList(sf, sf3);
    }

    public static List<UniParcDBCrossReference> uniParcDBCrossReferences() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "some gname"));
        UniParcDBCrossReference xref =
                new UniParcDBCrossReferenceBuilder()
                        .versionI(3)
                        .databaseType(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 5, 17))
                        .lastUpdated(LocalDate.of(2017, 2, 27))
                        .propertiesSet(properties)
                        .build();

        List<Property> properties2 = new ArrayList<>();
        properties2.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        properties2.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));

        UniParcDBCrossReference xref2 =
                new UniParcDBCrossReferenceBuilder()
                        .versionI(1)
                        .databaseType(UniParcDatabase.TREMBL)
                        .id("P52345")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 2, 12))
                        .lastUpdated(LocalDate.of(2017, 4, 23))
                        .propertiesSet(properties2)
                        .build();

        return Arrays.asList(xref, xref2);
    }

    public static APIsoform createAPIsoform() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(new IsoformNameBuilder(syn1, evidences).build());
        isoformSynonyms.add(new IsoformNameBuilder(syn2, emptyList()).build());

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        return isoformBuilder
                .name(new IsoformNameBuilder(name, evidences).build())
                .synonymsSet(isoformSynonyms)
                .isoformIdsSet(asList("isoID1", "isoID2", "isoID3"))
                .sequenceStatus(IsoformSequenceStatus.DISPLAYED)
                .sequenceIdsSet(singletonList("someSeqId"))
                .build();
    }

    public static List<Name> shortNames() {
        List<Evidence> evidences = createEvidences();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(new NameImpl("short name1", evidences));
        shortNames.add(new NameImpl("short name2", evidences));
        return shortNames;
    }

    public static List<EC> eCNumbers() {
        List<Evidence> evidences = createEvidences();
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(new ECBuilder().value("1.2.3.4").evidencesSet(evidences).build());
        ecNumbers.add(new ECBuilder().value("1.3.4.3").evidencesSet(evidences).build());
        return ecNumbers;
    }

    public static Absorption createAbsorption() {
        Note note = createNote();
        List<Evidence> evidences = createEvidences();
        return new AbsorptionBuilder().evidencesSet(evidences).note(note).max(32).build();
    }

    public static KineticParameters createKineticParameters() {
        List<MaximumVelocity> velocities = maximumVelocitys();

        List<MichaelisConstant> mConstants = michaelisConstants();
        Note note = createNote();

        return new KineticParametersBuilder()
                .maximumVelocitiesSet(velocities)
                .michaelisConstantsSet(mConstants)
                .note(note)
                .build();
    }

    public static List<MaximumVelocity> maximumVelocitys() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(
                new MaximumVelocityBuilder()
                        .velocity(1.0)
                        .unit("unit1")
                        .enzyme("enzyme1")
                        .evidencesSet(createEvidences())
                        .build());
        velocities.add(
                new MaximumVelocityBuilder()
                        .velocity(1.32)
                        .unit("unit2")
                        .enzyme("enzyme2")
                        .evidencesSet(createEvidences())
                        .build());
        return velocities;
    }

    public static List<MichaelisConstant> michaelisConstants() {
        List<MichaelisConstant> mConstants = new ArrayList<>();
        double constant = 2.13;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        MichaelisConstant mconstant =
                new MichaelisConstantBuilder()
                        .constant(constant)
                        .unit(unit)
                        .substrate(substrate)
                        .evidencesSet(createEvidences())
                        .build();
        mConstants.add(mconstant);
        return mConstants;
    }

    public static LiteratureMappedReference createCompleteLiteratureMappedReference() {
        return getBasicFields()
                .sourceCategoriesSet(singletonList("source category"))
                .uniprotAccession("P12345")
                .build();
    }

    public static LiteratureMappedReference createCompleteLiteratureMappedReferenceWithAdd() {
        return getBasicFields()
                .sourceCategoriesAdd("source category")
                .uniprotAccession(new UniProtAccessionImpl("P12345"))
                .build();
    }

    private static LiteratureMappedReferenceBuilder getBasicFields() {
        return new LiteratureMappedReferenceBuilder()
                .annotation("annotation value")
                .source("source value")
                .sourceId("source Id");
    }

    public static LiteratureStoreEntry createCompleteLiteratureStoreEntry() {
        return new LiteratureStoreEntryBuilder()
                .literatureEntry(createCompleteLiteratureEntry())
                .literatureMappedReferencesSet(
                        Collections.singletonList(createCompleteLiteratureMappedReference()))
                .build();
    }

    public static LiteratureStoreEntry createCompleteLiteratureStoreEntryWithAdd() {
        return new LiteratureStoreEntryBuilder()
                .literatureEntry(createCompleteLiteratureEntry())
                .literatureMappedReferencesAdd(createCompleteLiteratureMappedReference())
                .build();
    }

    public static LiteratureEntry createCompleteLiteratureEntry() {
        return new LiteratureEntryBuilder()
                .citation(createCompleteLiteratureCitation())
                .statistics(createCompleteLiteratureStatistics())
                .build();
    }

    public static Citation createCompleteLiteratureCitation() {
        DBCrossReference<CitationDatabase> xref =
                new DBCrossReferenceBuilder<CitationDatabase>()
                        .databaseType(CitationDatabase.PUBMED)
                        .id("id1")
                        .build();

        return new LiteratureBuilder()
                .literatureAbstract("literature abstract")
                .completeAuthorList(true)
                .firstPage("first page")
                .lastPage("last page")
                .volume("the volume")
                .journalName("The journal name")
                .authorsAdd("John")
                .authoringGroupsAdd("the author group")
                .citationXrefsAdd(xref)
                .publicationDate("2015-MAY")
                .title("the big title")
                .build();
    }

    public static LiteratureStatistics createCompleteLiteratureStatistics() {
        return new LiteratureStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .mappedProteinCount(30)
                .build();
    }

    public static List<Taxonomy> taxonomies() {
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        Taxonomy taxonomy2 = new TaxonomyBuilder().taxonId(10090).scientificName("MOUSE").build();
        return Arrays.asList(taxonomy, taxonomy2);
    }

    public static TaxonomyEntry getCompleteTaxonomyEntryUsingAdd() {
        TaxonomyEntryBuilder builder = getTaxonomyEntryBuilderWithBasicData();

        builder.synonymsAdd("synonym");
        builder.otherNamesAdd("otherName");
        builder.lineagesAdd(getCompleteTaxonomyLineage());
        builder.strainsAdd(getCompleteTaxonomyStrain());
        builder.hostsAdd(getCompleteTaxonomy());
        builder.linksAdd("link");

        return builder.build();
    }

    public static TaxonomyEntry getCompleteTaxonomyEntry() {
        TaxonomyEntryBuilder builder = getTaxonomyEntryBuilderWithBasicData();

        builder.synonymsSet(singletonList("synonym"));
        builder.otherNamesSet(singletonList("otherName"));
        builder.lineagesSet(singletonList(getCompleteTaxonomyLineage()));
        builder.strainsSet(singletonList(getCompleteTaxonomyStrain()));
        builder.hostsSet(singletonList(getCompleteTaxonomy()));
        builder.linksSet(singletonList("link"));

        return builder.build();
    }

    private static TaxonomyEntryBuilder getTaxonomyEntryBuilderWithBasicData() {
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();
        builder.taxonId(9606L);
        builder.scientificName("scientificName");
        builder.commonName("commonName");
        builder.mnemonic("mnemonic");
        builder.parentId(9605L);
        builder.rank(TaxonomyRank.KINGDOM);
        builder.hidden(true);
        builder.active(true);
        builder.inactiveReason(getCompleteTaxonomyInactiveReason());
        builder.statistics(getCompleteTaxonomyStatistics());
        return builder;
    }

    public static Taxonomy getCompleteTaxonomy() {
        return new TaxonomyBuilder()
                .taxonId(9606)
                .scientificName("Homo sapiens")
                .commonName("Human")
                .synonymsSet(singletonList("Some name"))
                .mnemonic("HUMAN")
                .build();
    }

    public static TaxonomyLineage getCompleteTaxonomyLineage() {
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606L)
                .scientificName("Scientific Name")
                .hidden(true)
                .rank(TaxonomyRank.FAMILY);
        return builder.build();
    }

    public static TaxonomyStrain getCompleteTaxonomyStrain() {
        TaxonomyStrainBuilder builder = new TaxonomyStrainBuilder();
        builder.synonymsSet(singletonList("synonym"));
        builder.name("name");
        return builder.build();
    }

    public static TaxonomyInactiveReason getCompleteTaxonomyInactiveReason() {
        return new TaxonomyInactiveReasonBuilder()
                .inactiveReasonType(TaxonomyInactiveReasonType.MERGED)
                .mergedTo(9604L)
                .build();
    }

    public static TaxonomyStatistics getCompleteTaxonomyStatistics() {
        return new TaxonomyStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .referenceProteomeCount(1)
                .proteomeCount(2)
                .build();
    }

    public static List<Cofactor> cofactors() {
        DBCrossReference<CofactorDatabase> reference =
                new DBCrossReferenceImpl<>(CofactorDatabase.CHEBI, "CHEBI:324");
        Cofactor cofactor =
                new CofactorBuilder()
                        .name("cofactor 1")
                        .reference(reference)
                        .evidencesSet(createEvidences())
                        .build();
        DBCrossReference<CofactorDatabase> reference2 =
                new DBCrossReferenceImpl<>(CofactorDatabase.CHEBI, "CHEBI:31324");
        Cofactor cofactor2 =
                new CofactorBuilder()
                        .name("cofactor 2")
                        .reference(reference2)
                        .evidencesSet(createEvidences())
                        .build();
        return Arrays.asList(cofactor, cofactor2);
    }

    public static List<DBCrossReference<ProteomeDatabase>> proteomeXReferenceTypes() {
        DBCrossReference<ProteomeDatabase> xref1 =
                new DBCrossReferenceBuilder<ProteomeDatabase>()
                        .databaseType(ProteomeDatabase.GENOME_ACCESSION)
                        .id("ACA121")
                        .build();
        DBCrossReference<ProteomeDatabase> xref2 =
                new DBCrossReferenceBuilder<ProteomeDatabase>()
                        .databaseType(ProteomeDatabase.GENOME_ANNOTATION)
                        .id("ADFDA121")
                        .build();
        return Arrays.asList(xref1, xref2);
    }

    public static ProteomeEntry createProteomeEntry() {
        ComponentImpl component =
                new ComponentImpl(
                        "name",
                        "desc",
                        5,
                        ComponentType.SEGMENTED_GENOME,
                        proteomeXReferenceTypes());
        JournalArticleBuilder builder = new JournalArticleBuilder();
        RedundantProteome rp = new RedundantProteomeImpl(new ProteomeIdImpl("id"), 4.5F);
        updateCitationBuilderWithCommonAttributes(builder);
        new UniProtAccessionImpl("val");
        ProteinImpl protein =
                new ProteinImpl(
                        new UniProtAccessionImpl("val"),
                        UniProtEntryType.INACTIVE,
                        20L,
                        "gene",
                        GeneNameType.ORF);
        CanonicalProteinImpl canonicalProtein =
                new CanonicalProteinImpl(protein, singletonList(protein));
        return new ProteomeEntryImpl(
                new ProteomeIdImpl("id"),
                taxonomies().get(0),
                "description",
                LocalDate.now(),
                ProteomeType.NORMAL,
                new ProteomeIdImpl("id1"),
                "strain",
                "isolate",
                proteomeXReferenceTypes(),
                Collections.singletonList(component),
                Collections.singletonList(builder.build()),
                Collections.singletonList(rp),
                new ProteomeIdImpl("panProteome"),
                5,
                Superkingdom.EUKARYOTA,
                90,
                Collections.singletonList(getCompleteTaxonomyLineage()),
                Collections.singletonList(canonicalProtein),
                "db");
    }

    public static void updateCitationBuilderWithCommonAttributes(
            AbstractCitationBuilder<?, ?> builder) {
        final String TITLE = "Some title";
        final String PUBLICATION_DATE = "2015-MAY";
        final List<String> GROUPS = asList("T1", "T2");
        final List<String> AUTHORS = asList("Tom", "John");
        builder.title(TITLE)
                .publicationDate(PUBLICATION_DATE)
                .authoringGroupsSet(GROUPS)
                .authorsSet(AUTHORS)
                .citationXrefsSet(
                        asList(
                                new DBCrossReferenceBuilder<CitationDatabase>()
                                        .databaseType(CitationDatabase.PUBMED)
                                        .id("id1")
                                        .build(),
                                new DBCrossReferenceBuilder<CitationDatabase>()
                                        .databaseType(CitationDatabase.AGRICOLA)
                                        .id("id2")
                                        .build()));
    }
}
