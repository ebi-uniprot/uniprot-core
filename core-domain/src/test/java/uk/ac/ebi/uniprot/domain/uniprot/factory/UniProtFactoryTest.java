package uk.ac.ebi.uniprot.domain.uniprot.factory;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UniProtFactoryTest {
    @Test
    public void testGetGeneFactory() {
        GeneFactory componentFactory = UniProtFactory.INSTANCE.getGeneFactory();
        assertNotNull(componentFactory);
    }

    @Test
    public void testGetProteinDescriptionFactory() {
        ProteinDescriptionFactory componentFactory = UniProtFactory.INSTANCE.getProteinDescriptionFactory();
        assertNotNull(componentFactory);
    }

    @Test
    public void testGetTaxonomyFactory() {
        TaxonomyFactory componentFactory = UniProtFactory.INSTANCE.getTaxonomyFactory();
        assertNotNull(componentFactory);
    }

    @Test
    public void testGetUniProtDBCrossReferenceFactory() {
        UniProtDBCrossReferenceFactory componentFactory = UniProtFactory.INSTANCE.getUniProtDBCrossReferenceFactory();
        assertNotNull(componentFactory);
    }

    @Test
    public void testGetCommentFactory() {
        CommentFactory componentFactory = UniProtFactory.INSTANCE.getCommentFactory();
        assertNotNull(componentFactory);
    }

    @Test
    public void testGetFeatureFactory() {
        FeatureFactory componentFactory = UniProtFactory.INSTANCE.getFeatureFactory();
        assertNotNull(componentFactory);
    }

    @Test
    public void testGetUniProtReferenceFactory() {
        UniProtReferenceFactory componentFactory = UniProtFactory.INSTANCE.getUniProtReferenceFactory();
        assertNotNull(componentFactory);
    }

    @Test
    public void testCreateEvidencedValue() {
        List<Evidence> evidences = createEvidences();
        String value ="some value";
        EvidencedValue  evidencedValue =UniProtFactory.INSTANCE.createEvidencedValue(value, evidences);
        assertEquals(value, evidencedValue.getValue());
        assertEquals(evidences, evidencedValue.getEvidences());
    }

    @Test
    public void testCreateUniProtAccession() {
        String value ="P12345";
        UniProtAccession accession =UniProtFactory.INSTANCE.createUniProtAccession(value);
        assertEquals(value, accession.getValue());
    }
    @Test
    public void testCreateUniProtId() {
        String value ="P12345_HUMAN";
        UniProtId uniprotId =UniProtFactory.INSTANCE.createUniProtId(value);
        assertEquals(value, uniprotId.getValue());
    }

    @Test
    public void testCreateOrganelle() {
        GeneEncodingType geneEncodingType = GeneEncodingType.CHROMATOPHORE_PLASTID;
        String value="p1";
        List<Evidence> evidences = createEvidences();
        Organelle organelle= UniProtFactory.INSTANCE.createOrganelle(geneEncodingType, value, evidences);
        assertEquals(geneEncodingType, organelle.getGeneEncodingType());
        assertEquals(value, organelle.getValue());
        assertEquals(evidences, organelle.getEvidences());
    }

    @Test
    public void testCreateInternalSection() {
        List<InternalLine> internalLines = new ArrayList<>();
        internalLines.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.CP, "Val1"));
        internalLines.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.HA, "Val2"));
        List<SourceLine> sourceLines = new ArrayList<>();
        sourceLines.add(UniProtFactory.INSTANCE.createSourceLine("source1"));
        InternalSection internalSection = UniProtFactory.INSTANCE.createInternalSection(internalLines, null, sourceLines);
        assertEquals(internalLines, internalSection.getInternalLines());
        assertEquals(sourceLines, internalSection.getSourceLines());
    }

    @Test
    public void testCreateInternalLine() {
        InternalLineType type = InternalLineType.CP;
        String value ="some values";
        InternalLine internalLine =UniProtFactory.INSTANCE.createInternalLine(type, value);
        assertEquals(type, internalLine.getType());
        assertEquals(value, internalLine.getValue());
    }

    @Test
    public void testCreateSourceLine() {
        String value ="some values";
        SourceLine sourceLine =UniProtFactory.INSTANCE.createSourceLine(value);
        assertEquals(value, sourceLine.getValue());
    }

    @Test
    public void testCreateKeyword() {
        String value="p1";
        List<Evidence> evidences = createEvidences();
        Keyword keyword = UniProtFactory.INSTANCE.createKeyword(value, evidences);
        assertEquals(value, keyword.getValue());
        assertEquals(evidences, keyword.getEvidences());
    }

    @Test
    public void testCreateEntryAudit() {
        LocalDate firstPublicDate = LocalDate.of(2011, 10, 18);
        LocalDate lastAnnotationUpdateDate = LocalDate.of(2013, 9, 2);
        LocalDate lastSequenceUpdateDate = LocalDate.of(2014, 3, 22);
        int entryVersion =12;
        int sequenceVersion =5;
        EntryAudit entryAudit = UniProtFactory.INSTANCE.createEntryAudit(firstPublicDate, lastAnnotationUpdateDate, 
                lastSequenceUpdateDate, entryVersion, sequenceVersion);
        assertEquals(firstPublicDate, entryAudit.getFirstPublicDate());
        assertEquals(lastAnnotationUpdateDate, entryAudit.getLastAnnotationUpdateDate());
        assertEquals(lastSequenceUpdateDate, entryAudit.getLastSequenceUpdateDate());
        assertEquals(entryVersion, entryAudit.getEntryVersion());
        assertEquals(sequenceVersion, entryAudit.getSequenceVersion());
    }
    @Test
    public void testCreateSequence(){
        String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
        Sequence sequence =UniProtFactory.INSTANCE.createSequence(value);
        assertEquals(value, sequence.getValue());
    }
    
    @Test
    public void TestCreateUniProtTaxonId(){
        long taxId = 9606;
        List<Evidence> evidences =createEvidences() ;
        UniProtTaxonId taxonId = UniProtFactory.INSTANCE.createUniProtTaxonId(taxId, evidences);
        assertEquals(taxId, taxonId.getTaxonId());
        assertEquals(evidences, taxonId.getEvidences());
    }
    
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
