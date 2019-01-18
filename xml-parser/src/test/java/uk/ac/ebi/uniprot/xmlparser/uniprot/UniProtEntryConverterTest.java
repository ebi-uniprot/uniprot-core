package uk.ac.ebi.uniprot.xmlparser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismHostBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescriptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.GeneFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtDBCrossReferenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.Entry;

class UniProtEntryConverterTest {

	@Test
	void test() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        List<UniProtAccession> secondaryAccessions = new ArrayList<>();
        secondaryAccessions.add(UniProtFactory.INSTANCE.createUniProtAccession("P23456"));
        secondaryAccessions.add(UniProtFactory.INSTANCE.createUniProtAccession("P23457"));
        LocalDate firstPublicDate = LocalDate.of(2011, 10, 18);
        LocalDate lastAnnotationUpdateDate = LocalDate.of(2013, 9, 2);
        LocalDate lastSequenceUpdateDate = LocalDate.of(2014, 3, 22);
        int entryVersion = 12;
        int sequenceVersion = 5;
        EntryAudit entryAudit = UniProtFactory.INSTANCE.createEntryAudit(firstPublicDate,
                                                                         lastAnnotationUpdateDate, lastSequenceUpdateDate, entryVersion, sequenceVersion);
        List<Organelle> organelles = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        organelles.add(UniProtFactory.INSTANCE.createOrganelle(GeneEncodingType.APICOPLAST_PLASTID, null, evidences));
        organelles.add(UniProtFactory.INSTANCE
                               .createOrganelle(GeneEncodingType.PLASMID, "some value", evidences));
        List<Keyword> keywords = new ArrayList<>();

        keywords.add(UniProtFactory.INSTANCE.createKeyword("KW-001", "key1", evidences));
        keywords.add(UniProtFactory.INSTANCE.createKeyword("KW-002", "key2", evidences));
        keywords.add(UniProtFactory.INSTANCE.createKeyword("KW-003", "key3", evidences));
        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
        Sequence sequence = UniProtFactory.INSTANCE.createSequence(value);
        UniProtEntry entry =
                builder.entryType(UniProtEntryType.TREMBL)
                .primaryAccession("P12345")
                .secondaryAccessions(secondaryAccessions)
                .uniProtId("P12345_HUMAN")
                .organism(createOrganism())
                .organismHosts(createOrganismHosts())
                .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                .entryAudit(entryAudit)
                .organelles(organelles)
                .uniProtDBCrossReferences(createDbXref())
                .keywords(keywords)
                .proteinDescription(createProteinDescription())
                .genes(Arrays.asList(createGene()))
                .comments(createComments())
                .features(createFeatures())
                .sequence(sequence)
                        .build();
        UniProtEntryConverter converter = new UniProtEntryConverter();
        Entry xmlEntry =converter.toXml(entry);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlEntry, Entry.class, "entry"));
        UniProtEntry converted = converter.fromXml(xmlEntry);
        assertEquals(entry, converted);
	      
	}
	private Organism createOrganism() {
		OrganismBuilder builder =new OrganismBuilder();
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
	private List<OrganismHost> createOrganismHosts(){
		List<OrganismHost> hosts = new ArrayList<>();
		hosts.add  (new OrganismHostBuilder().taxonId(29095l)
		.scientificName("Akodon azarae")
		.commonName("Azara's grass mouse")
		.build());
		//56211; Calomys laucha (Small vesper mouse).
		hosts.add  (new OrganismHostBuilder().taxonId(56211l)
				.scientificName("Calomys laucha")
				.commonName("Small vesper mouse")
				.build());
		
		return hosts;
		
	}
	private Gene createGene() {
		String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PROSITE:PS51004")
        });
        GeneName geneName = GeneFactory.INSTANCE.createGeneName(val, evidences);
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        String valSyn = "someSyn";
        List<Evidence> synEvidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        GeneNameSynonym synonym = GeneFactory.INSTANCE.createGeneNameSynonym(valSyn, synEvidences);
        
     
        synonyms.add(synonym);
        String valSyn2 = "Syn 2";
        List<Evidence> synEvidences2 = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001364"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        GeneNameSynonym synonym2 = GeneFactory.INSTANCE.createGeneNameSynonym(valSyn2, synEvidences2);
        synonyms.add(synonym2);
        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();
        
        String locusName = "some locus name";
        List<Evidence> olnEvidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        OrderedLocusName olnName = GeneFactory.INSTANCE.createOrderedLocusName(locusName, olnEvidences);
        olnNames.add(olnName);
        Gene gene = GeneFactory.INSTANCE.createGene(geneName, synonyms, olnNames, orfNames);
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

	    private Feature createVarSeqFeature(){
	    	Range location = new Range(65, 86);
			AlternativeSequence as =new AlternativeSequenceImpl("RS", Arrays.asList("DB", "AA"));
			FeatureId featureId = 	FeatureFactory.INSTANCE.createFeatureId("VSP_112"); 
			  
			return new FeatureImpl(FeatureType.VAR_SEQ, location, "some description",
					featureId, as, null,
					createEvidences());
		

	    }
    private List<Comment> createComments() {
        CommentFactory commentFactory = UniProtFactory.INSTANCE.getCommentFactory();
        List<Comment> comments = new ArrayList<>();
        comments.add(FreeTextCommentBuilder.buildFreeTextComment(CommentType.ALLERGEN, createEvidenceValues()));
        FreeTextCommentBuilder ftcBuilder = commentFactory.createFreeTextCommentBuilder();
        ftcBuilder.commentType(CommentType.FUNCTION)
                .texts(createEvidenceValues());
        comments.add(commentFactory.createComment(ftcBuilder));
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor = CofactorCommentBuilder.createCofactor("somename", reference, createEvidences());
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = commentFactory.createCofactorCommentBuilder();
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
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
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
    private ProteinDescription createProteinDescription() {
        List<Evidence> evidences = createEvidences();
        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinName recommendedName = ProteinDescriptionFactory.INSTANCE
                .createProteinName(fullName, shortNames, ecNumbers);
        Name allergenName = ProteinDescriptionFactory.INSTANCE.createName("allergen", evidences);
        Name biotechName = ProteinDescriptionFactory.INSTANCE.createName("biotech", evidences);
        List<Name> antigenNames = new ArrayList<>();
        antigenNames.add(ProteinDescriptionFactory.INSTANCE.createName("cd antigen", evidences));


        List<ProteinName> proteinAltName = createAltName();
        Name fullName1 = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);

        List<EC> ecNumbers1 = createECNumbers();
        ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName1, null, ecNumbers1);
        List<ProteinName> subNames = new ArrayList<>();
        subNames.add(subName);
        ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
        return builder.recommendedName(recommendedName)
                .alternativeNames(proteinAltName)
                .submissionNames(subNames)
                .allergenName(allergenName)
                .biotechName(biotechName)
                .cdAntigenNames(antigenNames).build();


    }

    private List<ProteinName> createAltName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full alt Name", evidences);
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));

        ProteinName altName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);

        List<ProteinName> altNames = new ArrayList<>();
        altNames.add(altName);
        return altNames;
    }

    private List<Name> createShortNames() {
        List<Evidence> evidences = createEvidences();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
        return shortNames;
    }

    private List<EC> createECNumbers() {
        List<Evidence> evidences = createEvidences();
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.3.4.3", evidences));
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
        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));

        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.


        type = "EMBL";
        id = "DQ185029";
        description = "AAZ94714.1";
        thirdAttr = "-";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
        // DR   EMBL; AK000352; BAA91105.1; ALT_INIT; mRNA.
        type = "EMBL";
        id = "AK000352";
        description = "BAA91105.1";
        thirdAttr = "ALT_INIT";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
        // DR   EMBL; AK310815; -; NOT_ANNOTATED_CDS; mRNA.
        type = "EMBL";
        id = "AK310815";
        description = "-";
        thirdAttr = "NOT_ANNOTATED_CDS";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));

        //   DR   HPA; HPA021372; -.
        type = "HPA";
        id = "HPA021372";
        description = "-";
        thirdAttr = null;
        fourthAttr = null;
        isoform = null;
        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
        //  DR   HPA; HPA021812; -.
        type = "HPA";
        id = "HPA021812";
        description = "-";
        thirdAttr = null;
        fourthAttr = null;
        isoform = null;
        xrefs.add(UniProtDBCrossReferenceFactory.INSTANCE
                          .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));

        return xrefs;

    }
	  private List<Evidence> createEvidences() {
	        List<Evidence> evidences = new ArrayList<>();
	        evidences.add(createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
	        evidences.add(createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
	        return evidences;
	    }
	  private Evidence createEvidence(String evidenceStr) {
	        return UniProtFactory.INSTANCE.createEvidence(evidenceStr);
	    }
}
