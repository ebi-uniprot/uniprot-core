package org.uniprot.core.xml.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.xml.jaxb.uniprot.Uniprot;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoogleUniProtEntryConverterTest {
    @Test
    void testXMLToUniProtKBEntry() throws Exception {
        String file = "/uniprot/google/A0A6A5BR32.xml";
        InputStream inputStream = GoogleUniProtEntryConverterTest.class.getResourceAsStream(file);

        JAXBContext jaxbContext = JAXBContext.newInstance("org.uniprot.core.xml.jaxb.uniprot");

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Uniprot xmlEntry = (Uniprot) jaxbUnmarshaller.unmarshal(inputStream);
        assertNotNull(xmlEntry);

        GoogleUniProtEntryConverter converter = new GoogleUniProtEntryConverter();
        List<UniProtKBEntry> uniProtEntries =
                xmlEntry.getEntry().stream().map(converter::fromXml).toList();
        assertNotNull(uniProtEntries);
        assertEquals(1, uniProtEntries.size());
        UniProtKBEntry uniProtEntry = uniProtEntries.get(0);
        assertNotNull(uniProtEntry);
        assertEquals("A0A6A5BR32", uniProtEntry.getPrimaryAccession().getValue());
        assertEquals("PLACEHOLDER", uniProtEntry.getUniProtkbId().getValue());
        assertEquals("Uncharacterized protein", uniProtEntry.getProteinDescription().getRecommendedName().getFullName().getValue());
        // reference
        assertEquals(1, uniProtEntry.getReferences().size());
        UniProtKBReference reference = uniProtEntry.getReferences().get(0);
        Citation citation = reference.getCitation();
        assertNotNull(citation);
        assertEquals("CI-FC84BSHK1H4IL", citation.getId());
        assertEquals(CitationType.UNPUBLISHED, citation.getCitationType());
        assertEquals(1, reference.getReferencePositions().size());
        String referencePosition = reference.getReferencePositions().get(0);
        assertEquals("required field", referencePosition);
        // cross-references
        assertEquals(2, uniProtEntry.getUniProtKBCrossReferences().size());
        List<UniProtKBCrossReference> xrefs = uniProtEntry.getUniProtKBCrossReferences();
        // go cross ref
        UniProtKBCrossReference goXref = xrefs.get(0);
        assertEquals("GO", goXref.getDatabase().getName());
        assertEquals("GO:0016020", goXref.getId());
        assertEquals(1, goXref.getEvidences().size());
        Evidence goEvidence = goXref.getEvidences().get(0);
        assertEquals(EvidenceCode.ECO_0008006, goEvidence.getEvidenceCode());
        CrossReference<EvidenceDatabase> goEvidenceXref = goEvidence.getEvidenceCrossReference();
        assertNotNull(goEvidenceXref);
        assertEquals("Google", goEvidenceXref.getDatabase().getName());
        assertEquals("ProtNLM2", goEvidenceXref.getId());
        assertEquals(4, goEvidenceXref.getProperties().size());
        assertEquals("model_score", goEvidenceXref.getProperties().get(0).getKey());
        assertEquals("0.32", goEvidenceXref.getProperties().get(0).getValue());
        assertEquals("tmalign_accession", goEvidenceXref.getProperties().get(1).getKey());
        assertEquals("P0A4F9", goEvidenceXref.getProperties().get(1).getValue());
        assertEquals("tmalign_score_chain_1", goEvidenceXref.getProperties().get(2).getKey());
        assertEquals("0.57156", goEvidenceXref.getProperties().get(2).getValue());
        assertEquals("tmalign_score_chain_2", goEvidenceXref.getProperties().get(3).getKey());
        assertEquals("0.61333", goEvidenceXref.getProperties().get(3).getValue());
        // pfram cross ref
        UniProtKBCrossReference pfamXref = xrefs.get(1);
        assertEquals("Pfam", pfamXref.getDatabase().getName());
        assertEquals("PF01094", pfamXref.getId());
        assertEquals(1, pfamXref.getEvidences().size());

        Evidence pfamEvidence = pfamXref.getEvidences().get(0);
        assertEquals(EvidenceCode.ECO_0008006, pfamEvidence.getEvidenceCode());

        CrossReference<EvidenceDatabase> pfamEvidenceXref = pfamEvidence.getEvidenceCrossReference();
        assertNotNull(pfamEvidenceXref);
        assertEquals("Google", pfamEvidenceXref.getDatabase().getName());
        assertEquals("ProtNLM2", pfamEvidenceXref.getId());
        assertEquals(4, pfamEvidenceXref.getProperties().size());

        assertEquals("model_score", pfamEvidenceXref.getProperties().get(0).getKey());
        assertEquals("0.13", pfamEvidenceXref.getProperties().get(0).getValue());

        assertEquals("tmalign_accession", pfamEvidenceXref.getProperties().get(1).getKey());
        assertEquals("A0A2I3MJ91", pfamEvidenceXref.getProperties().get(1).getValue());

        assertEquals("tmalign_score_chain_1", pfamEvidenceXref.getProperties().get(2).getKey());
        assertEquals("0.52774", pfamEvidenceXref.getProperties().get(2).getValue());

        assertEquals("tmalign_score_chain_2", pfamEvidenceXref.getProperties().get(3).getKey());
        assertEquals("0.21398", pfamEvidenceXref.getProperties().get(3).getValue());

        // keywords
        assertEquals(4, uniProtEntry.getKeywords().size());
        Keyword kw1 = uniProtEntry.getKeywords().get(0);
        assertEquals(1,1);
        assertEquals("KW-0325", kw1.getId());
        assertEquals("NAME NOT SET", kw1.getName());
        assertEquals(1, kw1.getEvidences().size());
        Evidence kwEvidence = kw1.getEvidences().get(0);
        assertEquals(EvidenceCode.ECO_0008006, kwEvidence.getEvidenceCode());
        CrossReference<EvidenceDatabase> kwEvidenceXref = kwEvidence.getEvidenceCrossReference();
        assertNotNull(kwEvidenceXref);
        assertEquals("Google", kwEvidenceXref.getDatabase().getName());
        assertEquals("ProtNLM2", kwEvidenceXref.getId());
        assertEquals(4, kwEvidenceXref.getProperties().size());

        assertEquals("model_score", kwEvidenceXref.getProperties().get(0).getKey());
        assertEquals("0.09", kwEvidenceXref.getProperties().get(0).getValue());

        assertEquals("tmalign_accession", kwEvidenceXref.getProperties().get(1).getKey());
        assertEquals("P22756", kwEvidenceXref.getProperties().get(1).getValue());

        assertEquals("tmalign_score_chain_1", kwEvidenceXref.getProperties().get(2).getKey());
        assertEquals("0.57725", kwEvidenceXref.getProperties().get(2).getValue());

        assertEquals("tmalign_score_chain_2", kwEvidenceXref.getProperties().get(3).getKey());
        assertEquals("0.20145", kwEvidenceXref.getProperties().get(3).getValue());
        // protein existence
        assertEquals(ProteinExistence.UNCERTAIN, uniProtEntry.getProteinExistence());
        // not set
        assertNull(uniProtEntry.getOrganism());
        assertNull(uniProtEntry.getSequence());
        assertTrue(uniProtEntry.getGenes().isEmpty());
        assertTrue(uniProtEntry.getFeatures().isEmpty());
    }
}
