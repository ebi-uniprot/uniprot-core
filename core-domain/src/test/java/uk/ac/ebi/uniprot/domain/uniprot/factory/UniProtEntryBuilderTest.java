package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.common.Sequence;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureSequence;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.Features;
import uk.ac.ebi.uniprot.domain.feature.SequenceReport;
import uk.ac.ebi.uniprot.domain.feature.VarSeqFeature;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtDatabaseCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorReference;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.comments.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;
import uk.ac.ebi.uniprot.domain.uniprot.description.RecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.AltNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDatabaseCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDatabaseCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDatabaseCrossReferencesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

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
                builder.setEntryType(UniProtEntryType.TREMBL)
                .build();
        
        assertEquals(UniProtEntryType.TREMBL, entry.getType());
        
        builder = UniProtEntryBuilder.newInstance();
         entry =
                builder.setEntryType(UniProtEntryType.SWISSPROT)
                .build();
         assertEquals(UniProtEntryType.SWISSPROT, entry.getType());
    }

    @Test
    public void testSetAccession() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertNull(entry.getPrimaryUniProtAccession());
        
        builder = UniProtEntryBuilder.newInstance();
        entry = builder   
                .setAccession("P12345")
                .build();
        assertNotNull(entry.getPrimaryUniProtAccession());
        assertEquals("P12345", entry.getPrimaryUniProtAccession().getValue());
    }

    @Test
    public void testSetAccession2() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertNull(entry.getPrimaryUniProtAccession());
        UniProtAccession accession =UniProtFactory.INSTANCE.createUniProtAccession("P23456");
        builder = UniProtEntryBuilder.newInstance();
        entry = builder   
                .setAccession(accession)
                .build();
        assertNotNull(entry.getPrimaryUniProtAccession());
        assertEquals(accession, entry.getPrimaryUniProtAccession());
        assertEquals("P23456", entry.getPrimaryUniProtAccession().getValue());
    }

    
    @Test
    public void testSetSecondaryAccessions() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertTrue(entry.getSecondaryUniProtAccessions().isEmpty());
        
        List<UniProtAccession> secondaryAccessions = new ArrayList<>();
        secondaryAccessions.add(UniProtFactory.INSTANCE.createUniProtAccession("P23456"));
        secondaryAccessions.add(UniProtFactory.INSTANCE.createUniProtAccession("P23457"));
        
        builder = UniProtEntryBuilder.newInstance();
         entry =   builder   
                 .setSecondaryAccessions(secondaryAccessions)
                .build();
        assertEquals(2, entry.getSecondaryUniProtAccessions().size());
        assertEquals(secondaryAccessions, entry.getSecondaryUniProtAccessions());
        
    }

    @Test
    public void testSetUniProtId() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertNull(entry.getUniProtId());
        
        builder = UniProtEntryBuilder.newInstance();
        entry = builder   
                .setUniProtId("P12345_HUMAN")
                .build();
        assertNotNull(entry.getUniProtId());
        assertEquals("P12345_HUMAN", entry.getUniProtId().getValue());
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
                .setUniProtId(uniprotId)
                .build();
        assertNotNull(entry.getUniProtId());
        assertEquals("P12346_HUMAN", entry.getUniProtId().getValue());
    }


    @Test
    public void testSetTaxons() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry =   builder            
                .build();
        assertTrue(entry.getTaxonomy().isEmpty());
        List<Taxon> taxonomies = new ArrayList<>();
        Taxon taxon= UniProtFactory.INSTANCE.getTaxonomyFactory().createTaxon(9606, "homo sapien");
        taxonomies.add(taxon);
        builder = UniProtEntryBuilder.newInstance();
         entry = builder     
                 .setTaxons(taxonomies)
                .build();
        assertEquals(1, entry.getTaxonomy().size());
        assertEquals(taxon, entry.getTaxonomy().get(0));
    }

    @Test
    public void testSetProteinExistence() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertEquals(ProteinExistence.UNKNOWN, entry.getProteinExistence());
        builder = UniProtEntryBuilder.newInstance();
         entry =   builder 
                 .setProteinExistence(ProteinExistence.PROTEIN_LEVEL)
                .build();
        assertEquals(ProteinExistence.PROTEIN_LEVEL, entry.getProteinExistence());

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
                 .setEntryAudit(entryAudit)
                .build();
        assertEquals(entryAudit, entry.getEntryAudit());
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
                 .setOrganelles(organelles)
                .build();
        assertEquals(2, entry.getOrganelles().size());
        assertEquals(organelles, entry.getOrganelles());
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
                 .setKeywords(keywords)
                .build();
        assertEquals(3, entry.getKeywords().size());
        assertEquals(keywords, entry.getKeywords());
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
                .setProteinDescription(proteinDescription)
                .build();
        assertEquals(proteinDescription, entry.getProteinDescription());
    }
    private ProteinDescription createProteinDescription() {
    	List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<ProteinName> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		RecName recName = ProteinDescriptionFactory.INSTANCE.createRecName(fullName, shortNames, ecNumbers);
		AltName altName = createAltName();
		List<AltName> altNames = new ArrayList<>();
		altNames.add(altName);
		ProteinRecommendedName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(recName, altNames);
		
		ProteinName fullName1 = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);

		List<ECNumber> ecNumbers1 = createECNumbers();
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName1, ecNumbers1);
		ProteinDescription description = ProteinDescriptionFactory.INSTANCE.createProteinDescription(recommendedName, subName);
		return description;
    }
	private AltName createAltName() {
		AltNameImpl.Builder builder = AltNameImpl.newBuilder();
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full alt Name", evidences);
		builder.fullName(fullName)
				.shortName(ProteinDescriptionFactory.INSTANCE.createProteinName("short name1", evidences))
				.shortName(ProteinDescriptionFactory.INSTANCE.createProteinName("short name2", evidences))
				.ecNumber(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences))
				.allergenName(ProteinDescriptionFactory.INSTANCE.createProteinName("allergen", evidences))
				.biotechName(ProteinDescriptionFactory.INSTANCE.createProteinName("biotech", evidences))
				.cDAntigenName(ProteinDescriptionFactory.INSTANCE.createProteinName("cd antigen", evidences));

		AltName altName = ProteinDescriptionFactory.INSTANCE.createAltName(builder);
		return altName;
	}
	private List<ProteinName> createShortNames() {
		List<Evidence> evidences = createEvidences();
		List<ProteinName> shortNames = new ArrayList<>();
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("short name1", evidences));
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("short name2", evidences));
		return shortNames;
	}

	private List<ECNumber> createECNumbers() {
		List<Evidence> evidences = createEvidences();
		List<ECNumber> ecNumbers = new ArrayList<>();
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.3.4.3", evidences));
		return ecNumbers;
	}

    @Test
    public void testSetComments() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertNotNull( entry.getComments());
        assertTrue(entry.getComments().getAllComments().isEmpty());
      
        Comments comments = createComments();
        builder = UniProtEntryBuilder.newInstance();
         entry = builder   
                 .setComments(comments)
                .build();
        assertNotNull( entry.getComments());
        assertEquals(3, entry.getComments().getAllComments().size());
        assertEquals(1, entry.getComments().getComments(CommentType.FUNCTION).size());
        assertEquals(1, entry.getComments().getComments(CommentType.COFACTOR).size());
        assertEquals(0, entry.getComments().getComments(CommentType.DISEASE).size());
        assertEquals(comments, entry.getComments());
        
    }
    private Comments createComments(){
        CommentFactory commentFactory =UniProtFactory.INSTANCE.getCommentFactory();
        List< Comment> comments = new ArrayList<>();
        comments.add(commentFactory.createFreeTextCommentBuilder().buildFreeTextComment(CommentType.ALLERGEN, createEvidenceValues()));
        FreeTextCommentBuilder<? extends FreeTextComment> ftcBuilder=commentFactory.createFreeTextCommentBuilder();
        ftcBuilder.commentType(CommentType.FUNCTION)
        .texts(createEvidenceValues());
        comments.add(commentFactory.createComment(ftcBuilder));
        CofactorReference reference =CofactorCommentBuilder.createCofactorReference(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =CofactorCommentBuilder.createCofactor("somename", createEvidences(), reference);
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder =commentFactory.createCofactorCommentBuilder();
        CommentNote note = CommentFactory.INSTANCE.createCommentNote(createEvidenceValues());
        String molecule ="some mol";
        CofactorComment cofactorComment =
                builder.molecule(molecule)
                .cofactors(cofactors)
                .note(note)
                .build();
        comments.add(cofactorComment);
        return commentFactory.createComments(comments);
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
        assertTrue(entry.getReferences().getReferences().isEmpty());
        UniProtReferences  uniReferences =createUniProtReferences();
        
        builder = UniProtEntryBuilder.newInstance();
         entry = builder       
                 .setUniProtReferences(uniReferences)
                .build();
        assertNotNull( entry.getReferences());
        assertEquals(2, entry.getReferences().getReferences().size());
        assertEquals(uniReferences, entry.getReferences());
        
    }
    private UniProtReferences createUniProtReferences(){
        UniProtReferenceFactory factory = UniProtFactory.INSTANCE.getUniProtReferenceFactory();
        List<UniProtReference<? extends Citation>> references = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        List<String>  referencePositions =
                Arrays.asList("Some position");
        Submission submission =
                factory.getCitationFactory()
                .createSubmissionBuilder()
                .submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .build();
        UniProtReference<Submission> subReference =factory.createUniProtReference(submission,
                referencePositions, null, evidences);
    
        JournalArticle ja =
                factory.getCitationFactory()
                .createJournalArticleBuilder()
                .journalName("some name")
                .title("some title")
                .build();
        
        UniProtReference<JournalArticle> jaReference =factory.createUniProtReference(ja,
                referencePositions, null, evidences);
        references.add(subReference);
        references.add(jaReference);
        return  factory.createUniProtReferences(references);
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
                .setGenes(genes)
                .build();
        assertEquals(2, entry.getGenes().size());
        assertEquals(gene1, entry.getGenes().get(0));
        
    }
    private Evidence createEvidence(String evidenceStr) {
        return EvidenceFactory.INSTANCE.createFromEvidenceLine(evidenceStr);
    }
    @Test
    public void testSetOrganism() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder            
                .build();
        assertNull(entry.getOrganism());
        long taxId =9606;
        String scientificName ="Homo sapiens";
        String commonName = "Human";
        Organism organism =  UniProtFactory.INSTANCE.getOrganismFactory().createOrganism(taxId, scientificName, commonName);
        
        builder = UniProtEntryBuilder.newInstance();
         entry = builder    
                 .setOrganism(organism)
                .build();
        assertNotNull(entry.getOrganism());
        assertEquals(organism, entry.getOrganism());
        
    }

    @Test
    public void testSetOrganismHosts() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertTrue(entry.getOrganismHosts().isEmpty());
        long taxId =9606;
        String scientificName ="Homo sapiens";
        String commonName = "Human";
        Organism organism =  UniProtFactory.INSTANCE.getOrganismFactory().createOrganism(taxId, scientificName, commonName);
        List<Organism> organisms = new ArrayList<>();
        organisms.add(organism);
        builder = UniProtEntryBuilder.newInstance();
         entry = builder   
                .setOrganismHosts(organisms)
                .build();
        assertEquals(1, entry.getOrganismHosts().size());
        assertEquals(organisms, entry.getOrganismHosts());
    }

    @Test
    public void testSetUniProtDatabaseCrossReferences() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertNotNull(entry.getDatabaseCrossReferences());
        assertTrue(entry.getDatabaseCrossReferences().getCrossReferences().isEmpty());
        UniProtDatabaseCrossReferences xrefs= createDbXref();
        builder = UniProtEntryBuilder.newInstance();
         entry = builder       
                 .setUniProtDatabaseCrossReferences(xrefs)
                .build();
        assertNotNull(entry.getDatabaseCrossReferences());
        assertEquals(6, entry.getDatabaseCrossReferences().getCrossReferences().size());
        
    }
    
    public UniProtDatabaseCrossReferences createDbXref(){
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        DatabaseType type =DatabaseType.ENSEMBL;
        String id ="ENST00000393119";
        String description ="ENSP00000376827";
        String thirdAttr= "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        List<UniProtDatabaseCrossReference>  xrefs = new ArrayList<>();
        xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
        
        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.
      
     
         type =DatabaseType.EMBL;
         id ="DQ185029";
         description ="AAZ94714.1";
         thirdAttr= "-";
         fourthAttr = "mRNA";
         isoform = null;
        xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
        // DR   EMBL; AK000352; BAA91105.1; ALT_INIT; mRNA.
        type =DatabaseType.EMBL;
        id ="AK000352";
        description ="BAA91105.1";
        thirdAttr= "ALT_INIT";
        fourthAttr = "mRNA";
        isoform = null;
       xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
       // DR   EMBL; AK310815; -; NOT_ANNOTATED_CDS; mRNA.
       type =DatabaseType.EMBL;
       id ="AK310815";
       description ="-";
       thirdAttr= "NOT_ANNOTATED_CDS";
       fourthAttr = "mRNA";
       isoform = null;
      xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
      
   //   DR   HPA; HPA021372; -.
      type =DatabaseType.HPA;
      id ="HPA021372";
      description ="-";
      thirdAttr=  null;
      fourthAttr = null;
      isoform = null;
     xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
     //  DR   HPA; HPA021812; -.
     type =DatabaseType.HPA;
     id ="HPA021812";
     description ="-";
     thirdAttr=  null;
     fourthAttr = null;
     isoform = null;
    xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
    return UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDatabaseCrossReferences(xrefs);
    
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
                 .setSequence(sequence)
                .build();
        assertNotNull(entry.getSequence());
        assertEquals(sequence, entry.getSequence());
        assertEquals(value, entry.getSequence().getValue());
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
                 .setSequence(value)
                .build();
        assertNotNull(entry.getSequence());
        assertEquals(value, entry.getSequence().getValue());
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
                .setUniProtTaxonId(taxonId)
                .build();
        assertNotNull(entry.getTaxonId());
        assertEquals(taxId, entry.getTaxonId().getTaxonId());
        
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
                 .setInternalSection(internalSection)
                .build();
        assertNotNull(entry.getInternalSection());
        assertEquals(internalSection, entry.getInternalSection());
    }
    private InternalSection createInternalSection(){
        List<InternalLine> internalLines = new ArrayList<>();
        internalLines.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.CP, "Val1"));
        internalLines.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.HA, "Val2"));
        List<SourceLine> sourceLines = new ArrayList<>();
        sourceLines.add(UniProtFactory.INSTANCE.createSourceLine("source1"));
        return UniProtFactory.INSTANCE.createInternalSection(internalLines, sourceLines);
    }

    @Test
    public void testSetFeatures() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder                 
                .build();
        assertNotNull(entry.getFeatures());
        assertTrue(entry.getFeatures().getAllFeatures().isEmpty());
        Features features = createFeatures();
        builder = UniProtEntryBuilder.newInstance();
         entry = builder
                 .setFeatures(features)
                .build();
        assertNotNull(entry.getFeatures());
        assertEquals(4, entry.getFeatures().getAllFeatures().size());
        assertEquals(features, entry.getFeatures());
    }
    private Features createFeatures(){
        List<Feature> features = new ArrayList<>();
        features.add(createVarSeqFeature());
        features.add(FeatureFactory.INSTANCE.buildSimpleFeature(FeatureType.TURN,
                FeatureFactory.INSTANCE.createFeatureLocation(12, 12), 
                "some desc1"));
        features.add(FeatureFactory.INSTANCE.buildSimpleFeature(FeatureType.TURN,
                FeatureFactory.INSTANCE.createFeatureLocation(20, 23), 
                "some desc2"));
        features.add(FeatureFactory.INSTANCE.buildSimpleFeatureWithFeatureId(FeatureType.CHAIN,
                FeatureFactory.INSTANCE.createFeatureLocation(200, 230), 
                "some desc3", "ft_123"));
        return  FeatureFactory.INSTANCE.createFeatures(features);
    }
    private VarSeqFeature createVarSeqFeature(){
        FeatureLocation location = FeatureFactory.INSTANCE.createFeatureLocation(65, 86);
        FeatureSequence orginalSequence =FeatureFactory.INSTANCE.createFeatureSequence("RS");
        List<FeatureSequence> alternativeSequences =Arrays.asList(new FeatureSequence[]{
                FeatureFactory.INSTANCE.createFeatureSequence("DB"),
                FeatureFactory.INSTANCE.createFeatureSequence("AA")});
        List<String> report = Arrays.asList(new String[]{"report1", "report 2"});
        SequenceReport sReport = FeatureFactory.INSTANCE.createSequenceReport(report);
        FeatureId featureId =FeatureFactory.INSTANCE.createFeatureId("VRS_112");
        return FeatureFactory.INSTANCE.buildVarSeqFeature(location,
                orginalSequence, alternativeSequences, sReport, featureId);
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
