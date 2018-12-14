package uk.ac.ebi.uniprot.domain.uniprot.factory;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.*;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class UniProtEntryBuilderTest {

    @Test
    public void testNewInstance() {
        UniProtEntryBuilder builder1 = UniProtEntryBuilder.newInstance();
        UniProtEntryBuilder builder2 = UniProtEntryBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1==builder2);
    }


    @Test
    public void testSetEntryType() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =
                builder.entryType(UniProtEntryType.TREMBL)
                .build();
        
        assertEquals(UniProtEntryType.TREMBL, entry.getEntryType());
        
        builder = UniProtEntryBuilder.newInstance();
         entry =
                builder.entryType(UniProtEntryType.SWISSPROT)
                .build();
         assertEquals(UniProtEntryType.SWISSPROT, entry.getEntryType());
         assertTrue(entry.isActive());
         TestHelper.verifyJson(entry);
    }

    @Test
    public void testSetAccession() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertNull(entry.getPrimaryAccession());
        
        builder = UniProtEntryBuilder.newInstance();
        entry = builder   
                .primaryAccession("P12345")
                .build();
        assertNotNull(entry.getPrimaryAccession());
        assertEquals("P12345", entry.getPrimaryAccession().getValue());
    }

    @Test
    public void testSetAccession2() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertNull(entry.getPrimaryAccession());
        UniProtAccession accession =UniProtFactory.INSTANCE.createUniProtAccession("P23456");
        builder = UniProtEntryBuilder.newInstance();
        entry = builder   
                .primaryAccession(accession)
                .build();
        assertNotNull(entry.getPrimaryAccession());
        assertEquals(accession, entry.getPrimaryAccession());
        assertEquals("P23456", entry.getPrimaryAccession().getValue());
        TestHelper.verifyJson(entry);
    }

    
    @Test
    public void testSetSecondaryAccessions() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertTrue(entry.getSecondaryAccessions().isEmpty());
        
        List<UniProtAccession> secondaryAccessions = new ArrayList<>();
        secondaryAccessions.add(UniProtFactory.INSTANCE.createUniProtAccession("P23456"));
        secondaryAccessions.add(UniProtFactory.INSTANCE.createUniProtAccession("P23457"));
        
        builder = UniProtEntryBuilder.newInstance();
         entry =   builder   
                 .secondaryAccessions(secondaryAccessions)
                .build();
        assertEquals(2, entry.getSecondaryAccessions().size());
        assertEquals(secondaryAccessions, entry.getSecondaryAccessions());
        TestHelper.verifyJson(entry);
        
    }

    @Test
    public void testSetUniProtId() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertNull(entry.getUniProtId());
        
        builder = UniProtEntryBuilder.newInstance();
        entry = builder   
                .uniProtId("P12345_HUMAN")
                .build();
        assertNotNull(entry.getUniProtId());
        assertEquals("P12345_HUMAN", entry.getUniProtId().getValue());
        TestHelper.verifyJson(entry);
    }
    
    @Test
    public void testSetUniProtId2() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertNull(entry.getUniProtId());
        UniProtId uniprotId =UniProtFactory.INSTANCE.createUniProtId("P12346_HUMAN");
        builder = UniProtEntryBuilder.newInstance();
        entry = builder   
                .uniProtId(uniprotId)
                .build();
        assertNotNull(entry.getUniProtId());
        assertEquals("P12346_HUMAN", entry.getUniProtId().getValue());
        TestHelper.verifyJson(entry);
    }


    @Test
    public void testSetTaxonomyLineage() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertTrue(entry.getTaxonomyLineage().isEmpty());
        List<OrganismName> taxonomies = new ArrayList<>();
        OrganismName taxon= UniProtFactory.INSTANCE.getTaxonomyFactory().createOrganismName("homo sapien");
        taxonomies.add(taxon);
        builder = UniProtEntryBuilder.newInstance();
         entry = builder     
                 .taxonomyLineage(taxonomies)
                .build();
        assertEquals(1, entry.getTaxonomyLineage().size());
        assertEquals(taxon, entry.getTaxonomyLineage().get(0));
        TestHelper.verifyJson(entry);
    }

    @Test
    public void testSetProteinExistence() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertEquals(ProteinExistence.UNKNOWN, entry.getProteinExistence());
        builder = UniProtEntryBuilder.newInstance();
         entry =   builder 
                 .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                .build();
        assertEquals(ProteinExistence.PROTEIN_LEVEL, entry.getProteinExistence());
        TestHelper.verifyJson(entry);
    }

    @Test
    public void testSetEntryAudit() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertEquals(null, entry.getEntryAudit());
        LocalDate firstPublicDate = LocalDate.of(2011, 10, 18);
        LocalDate lastAnnotationUpdateDate = LocalDate.of(2013, 9, 2);
        LocalDate lastSequenceUpdateDate = LocalDate.of(2014, 3, 22);
        int entryVersion =12;
        int sequenceVersion =5;
        EntryAudit entryAudit = UniProtFactory.INSTANCE.createEntryAudit(firstPublicDate,
                lastAnnotationUpdateDate, lastSequenceUpdateDate, entryVersion, sequenceVersion);
        builder = UniProtEntryBuilder.newInstance();
         entry = builder     
                 .entryAudit(entryAudit)
                .build();
        assertEquals(entryAudit, entry.getEntryAudit());
        TestHelper.verifyJson(entry);
    }

    @Test
    public void testSetOrganelles() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertTrue(entry.getOrganelles().isEmpty());
        List<Organelle> organelles = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        organelles.add(UniProtFactory.INSTANCE.createOrganelle(GeneEncodingType.APICOPLAST_PLASTID, null, evidences));
        organelles.add(UniProtFactory.INSTANCE.createOrganelle(GeneEncodingType.CYANELLE_PLASTID, "some value", evidences));
        builder = UniProtEntryBuilder.newInstance();
         entry = builder     
                 .organelles(organelles)
                .build();
        assertEquals(2, entry.getOrganelles().size());
        assertEquals(organelles, entry.getOrganelles());
        TestHelper.verifyJson(entry);
    }

    @Test
    public void testSetKeywords() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertTrue(entry.getKeywords().isEmpty());
        List<Keyword> keywords = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        keywords.add(UniProtFactory.INSTANCE.createKeyword("key1", evidences));
        keywords.add(UniProtFactory.INSTANCE.createKeyword("key2", evidences));
        keywords.add(UniProtFactory.INSTANCE.createKeyword("key3", evidences));
        builder = UniProtEntryBuilder.newInstance();
         entry = builder     
                 .keywords(keywords)
                .build();
        assertEquals(3, entry.getKeywords().size());
        assertEquals(keywords, entry.getKeywords());
        TestHelper.verifyJson(entry);
    }

    @Test
    public void testSetProteinDescription() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertEquals(null, entry.getProteinDescription());
        ProteinDescription proteinDescription =createProteinDescription();
        builder = UniProtEntryBuilder.newInstance();
         entry = builder  
                .proteinDescription(proteinDescription)
                .build();
        assertEquals(proteinDescription, entry.getProteinDescription());
        TestHelper.verifyJson(entry);
    }
    private ProteinDescription createProteinDescription() {
    	List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<EC> ecNumbers = createECNumbers();
		ProteinName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
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
		
		ProteinName altName =ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers);
		
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

    @Test
    public void testSetComments() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder.build();
        assertNotNull( entry.getComments());
        assertTrue(entry.getComments().isEmpty());

        List<Comment> comments = createComments();
        builder = UniProtEntryBuilder.newInstance();
         entry = builder.comments(comments).build();
        assertNotNull( entry.getComments());
        assertEquals(3, entry.getComments().size());
        assertEquals(1, entry.getCommentByType(CommentType.FUNCTION).size());
        assertEquals(1, entry.getCommentByType(CommentType.COFACTOR).size());
        assertEquals(0, entry.getCommentByType(CommentType.DISEASE).size());
        assertEquals(comments, entry.getComments());
        TestHelper.verifyJson(entry);
        
    }

	private List<Comment> createComments(){
        CommentFactory commentFactory =UniProtFactory.INSTANCE.getCommentFactory();
        List<Comment> comments = new ArrayList<>();
        comments.add(FreeTextCommentBuilder.buildFreeTextComment(CommentType.ALLERGEN, createEvidenceValues()));
        FreeTextCommentBuilder ftcBuilder=commentFactory.createFreeTextCommentBuilder();
        ftcBuilder.commentType(CommentType.FUNCTION)
        .texts(createEvidenceValues());
        comments.add(commentFactory.createComment(ftcBuilder));
        DBCrossReference<CofactorReferenceType> reference =new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =CofactorCommentBuilder.createCofactor("somename",reference,  createEvidences() );
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder =commentFactory.createCofactorCommentBuilder();
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        String molecule ="some mol";
        CofactorComment cofactorComment =
                builder.molecule(molecule)
                .cofactors(cofactors)
                .note(note)
                .build();
        comments.add(cofactorComment);
        return comments;
    }
    private List<EvidencedValue> createEvidenceValues(){
        List<EvidencedValue>  evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
     return evidencedValues;   
    }

    @Test
    public void testSetUniProtReferences() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertNotNull( entry.getReferences());
        assertTrue(entry.getReferences().isEmpty());
        List<UniProtReference>  uniReferences =createUniProtReferences();
        
        builder = UniProtEntryBuilder.newInstance();
         entry = builder       
                 .references(uniReferences)
                .build();
        assertNotNull( entry.getReferences());
        assertEquals(2, entry.getReferences().size());
        assertEquals(1, entry.getReferencesByType(CitationType.JOURNAL_ARTICLE).size());
        assertEquals(1, entry.getReferencesByType(CitationType.SUBMISSION).size());
        assertEquals(0, entry.getReferencesByType(CitationType.BOOK).size());
        assertEquals(uniReferences, entry.getReferences());
        TestHelper.verifyJson(entry);
        
    }
    private List<UniProtReference> createUniProtReferences(){
        UniProtReferenceFactory factory = UniProtFactory.INSTANCE.getUniProtReferenceFactory();
        List<UniProtReference> references = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        List<String>  referencePositions =
                Arrays.asList("Some position");
        Submission submission =
                factory.getCitationFactory()
                .createSubmissionBuilder()
                .submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .build();
        UniProtReference subReference =factory.createUniProtReference(submission,
                referencePositions, null, evidences);
    
        JournalArticle ja =
                factory.getCitationFactory()
                .createJournalArticleBuilder()
                .journalName("some name")
                .title("some title")
                .build();
        
        UniProtReference jaReference =factory.createUniProtReference(ja,
                referencePositions, null, evidences);
        references.add(subReference);
        references.add(jaReference);
        return  references;
    }

    @Test
    public void testSetGenes() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertTrue( entry.getGenes().isEmpty());
        GeneFactory geneFactory =UniProtFactory.INSTANCE.getGeneFactory();
        String val = "someGene";
        List<Evidence> evidences = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361")
        });
        GeneName geneName = geneFactory.createGeneName(val, evidences);
        List<GeneNameSynonym> synonyms = new ArrayList<>();

        List<OrderedLocusName> olnNames = new ArrayList<>();

        List<ORFName> orfNames = new ArrayList<>();
        Gene gene1 = geneFactory.createGene(geneName, synonyms, olnNames, orfNames);
        
        List<GeneNameSynonym> synonyms2 = new ArrayList<>();
        List<OrderedLocusName> olnNames2 = new ArrayList<>();
        String val2 = "someSyn";
        List<Evidence> evidences2 = Arrays.asList(new Evidence[]{
                createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389730")
        });
        OrderedLocusName olnName = geneFactory.createOrderedLocusName(val2, evidences2);
        olnNames2.add(olnName);
        List<ORFName> orfNames2 = new ArrayList<>();

        Gene gene2 = geneFactory.createGene(null, synonyms2, olnNames2, orfNames2);
        List<Gene> genes = new ArrayList<>();
        genes.add(gene1);
        genes.add(gene2);
        builder = UniProtEntryBuilder.newInstance();
         entry = builder   
                .genes(genes)
                .build();
        assertEquals(2, entry.getGenes().size());
        assertEquals(gene1, entry.getGenes().get(0));
        TestHelper.verifyJson(entry);
        
    }
    private Evidence createEvidence(String evidenceStr) {
        return UniProtFactory.INSTANCE.createEvidence(evidenceStr);
    }
    @Test
    public void testSetOrganism() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertNull(entry.getOrganism());
        String scientificName ="Homo sapiens";
        String commonName = "Human";
        OrganismName organism =  UniProtFactory.INSTANCE.getTaxonomyFactory().createOrganismName(scientificName, commonName);
        
        builder = UniProtEntryBuilder.newInstance();
         entry = builder    
                 .organism(organism)
                .build();
        assertNotNull(entry.getOrganism());
        assertEquals(organism, entry.getOrganism());
        TestHelper.verifyJson(entry);
        
    }

    @Test
    public void testSetOrganismHosts() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertTrue(entry.getOrganismHosts().isEmpty());
        String scientificName ="Homo sapiens";
        String commonName = "Human";
        OrganismName organism =  UniProtFactory.INSTANCE.getTaxonomyFactory().createOrganismName( scientificName, commonName);
        List<Organism> organismHosts = new ArrayList<>();
        organismHosts.add(
        		UniProtFactory.INSTANCE.getTaxonomyFactory().createOrganism(organism, 9606)
        		);
        builder = UniProtEntryBuilder.newInstance();
         entry = builder   
                .organismHosts(organismHosts)
                .build();
        assertEquals(1, entry.getOrganismHosts().size());
        assertEquals(organismHosts, entry.getOrganismHosts());
        TestHelper.verifyJson(entry);
    }

    @Test
    public void testSetUniProtDatabaseCrossReferences() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertNotNull(entry.getDatabaseCrossReferences());
        assertTrue(entry.getDatabaseCrossReferences().isEmpty());
        List<UniProtDBCrossReference> xrefs= createDbXref();
        builder = UniProtEntryBuilder.newInstance();
         entry = builder       
                 .uniProtDBCrossReferences(xrefs)
                .build();
        assertNotNull(entry.getDatabaseCrossReferences());
        assertEquals(6, entry.getDatabaseCrossReferences().size());
        assertEquals(2, entry.getDatabaseCrossReferencesByType(new UniProtXDbType("HPA")).size());
        assertEquals(3, entry.getDatabaseCrossReferencesByType("EMBL").size());
        assertEquals(1, entry.getDatabaseCrossReferencesByType("Ensembl").size());
        TestHelper.verifyJson(entry);
        
    }
    
    public List<UniProtDBCrossReference> createDbXref(){
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String type = "Ensembl";
        String id ="ENST00000393119";
        String description ="ENSP00000376827";
        String thirdAttr= "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        List<UniProtDBCrossReference>  xrefs = new ArrayList<>();
        xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));

        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.


        type = "EMBL";
        id ="DQ185029";
        description ="AAZ94714.1";
        thirdAttr= "-";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
        // DR   EMBL; AK000352; BAA91105.1; ALT_INIT; mRNA.
        type ="EMBL";
        id ="AK000352";
        description ="BAA91105.1";
        thirdAttr= "ALT_INIT";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
        // DR   EMBL; AK310815; -; NOT_ANNOTATED_CDS; mRNA.
        type = "EMBL";
        id ="AK310815";
        description ="-";
        thirdAttr= "NOT_ANNOTATED_CDS";
        fourthAttr = "mRNA";
        isoform = null;
        xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));

        //   DR   HPA; HPA021372; -.
        type = "HPA";
        id ="HPA021372";
        description ="-";
        thirdAttr=  null;
        fourthAttr = null;
        isoform = null;
        xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
        //  DR   HPA; HPA021812; -.
        type = "HPA";
        id ="HPA021812";
        description ="-";
        thirdAttr=  null;
        fourthAttr = null;
        isoform = null;
        xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));

        return xrefs;
    
    }

    @Test
    public void testSetSequence() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertNull(entry.getSequence());
        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
        Sequence sequence =UniProtFactory.INSTANCE.createSequence(value);
        builder = UniProtEntryBuilder.newInstance();
         entry = builder      
                 .sequence(sequence)
                .build();
        assertNotNull(entry.getSequence());
        assertEquals(sequence, entry.getSequence());
        assertEquals(value, entry.getSequence().getValue());
        TestHelper.verifyJson(entry);
    }
    
    @Test
    public void testSetSequence2() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertNull(entry.getSequence());
        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
      
        builder = UniProtEntryBuilder.newInstance();
         entry = builder      
                 .sequence(value)
                .build();
        assertNotNull(entry.getSequence());
        assertEquals(value, entry.getSequence().getValue());
        TestHelper.verifyJson(entry);
    }

    @Test
    public void testSetUniProtTaxonId() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertNull(entry.getTaxonId());
        long taxId = 9606;
        List<Evidence> evidences =createEvidences() ;
        UniProtTaxonId taxonId = UniProtFactory.INSTANCE.createUniProtTaxonId(taxId, evidences);
        builder = UniProtEntryBuilder.newInstance();
         entry = builder 
                .uniProtTaxonId(taxonId)
                .build();
        assertNotNull(entry.getTaxonId());
        assertEquals(taxId, entry.getTaxonId().getTaxonId());
        TestHelper.verifyJson(entry);
        
    }

    @Test
    public void testSetInternalSection() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertNull(entry.getInternalSection());
        InternalSection internalSection =createInternalSection();
        builder = UniProtEntryBuilder.newInstance();
         entry = builder  
                 .internalSection(internalSection)
                .build();
        assertNotNull(entry.getInternalSection());
        assertEquals(internalSection, entry.getInternalSection());
        TestHelper.verifyJson(entry);
    }
    private InternalSection createInternalSection(){
        List<InternalLine> internalLines = new ArrayList<>();
        internalLines.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.CP, "Val1"));
        internalLines.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.HA, "Val2"));
        List<SourceLine> sourceLines = new ArrayList<>();
        sourceLines.add(UniProtFactory.INSTANCE.createSourceLine("source1"));
        return UniProtFactory.INSTANCE.createInternalSection(internalLines, null, sourceLines);
    }

    @Test
    public void testSetFeatures() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertNotNull(entry.getFeatures());
        assertTrue(entry.getFeatures().isEmpty());
        List<Feature> features = createFeatures();
        builder = UniProtEntryBuilder.newInstance();
         entry = builder
                 .features(features)
                .build();
        assertNotNull(entry.getFeatures());
        assertEquals(4, entry.getFeatures().size());
        assertEquals(features, entry.getFeatures());
        TestHelper.verifyJson(entry);
    }
    private List<Feature> createFeatures(){
        List<Feature  > features = new ArrayList<>();
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
                       "some desc3", new FeatureIdImpl("PRO_123"),  evidences));
        		
       return features;
    }
    private Feature createVarSeqFeature(){
    	Range location = new Range(65, 86);
        List<String> value =Arrays.asList("report1", "report 2");
		SequenceReport report = FeatureFactory.INSTANCE.createReport(value);
		AlternativeSequence as =new AlternativeSequenceImpl("RS", Arrays.asList("DB", "AA"),
				report
				);
		FeatureId featureId = 	FeatureFactory.INSTANCE.createFeatureId("VSP_112"); 
		  
		return new FeatureImpl(FeatureType.VAR_SEQ, location, "Some description",
				featureId, as, null,
				createEvidences());
	
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
