package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

class EntryCrossReferenceMapTest {

    @Test
    void testGetDataEmpty() {
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(null);
        Map<String, String> result = dl.attributeValues();
        assertTrue(result.isEmpty());
    }

    @Test
    void hasEmbl() {
        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("EMBL"),
                        "AY189288",
                        "AAO86732.1",
                        "-",
                        "mRNA",
                        null));
        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("EMBL"),
                        "AK022746",
                        "BAB14220.1",
                        "-",
                        "mRNA",
                        null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(1, result.size());
        verify("AY189288;AK022746;", "xref_embl", result);
    }

    @Test
    void hasEmblAndEnsembl() {
        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("EMBL"),
                        "AY189288",
                        "AAO86732.1",
                        "-",
                        "mRNA",
                        null));
        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("EMBL"),
                        "AK022746",
                        "BAB14220.1",
                        "-",
                        "mRNA",
                        null));
        // Ensembl; ENST00000330899; ENSP00000369127; ENSG00000086061. [P31689-1]
        // Ensembl; ENST00000439351; ENSP00000414398; ENSG00000090520.
        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("Ensembl"),
                        "ENST00000330899",
                        "ENSP00000369127",
                        "ENSG00000086061",
                        null,
                        "P31689-1"));
        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("Ensembl"),
                        "ENST00000439351",
                        "ENSP00000414398",
                        "ENSG00000090520",
                        null,
                        null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        verify("AY189288;AK022746;", "xref_embl", result);
        verify("ENST00000330899 [P31689-1];ENST00000439351;", "xref_ensembl", result);
    }

    @Test
    void hasPdbAndSmr() {
        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(createXref(new UniProtKBDatabaseImpl("PDB"), "2LO1", "NMR", "-", "A=1-70", null));
        xrefs.add(createXref(new UniProtKBDatabaseImpl("PDB"), "2M6Y", "NMR", "-", "A=1-67", null));
        // PDB; 5TKG; X-ray; 1.20 A; A/B=16-23
        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("PDB"),
                        "5TKG",
                        "X-ray",
                        "1.20 A",
                        "A/B=16-23",
                        null));
        xrefs.add(createXref(new UniProtKBDatabaseImpl("SMR"), "P31689", "-", null, null, null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(3, result.size());
        verify("2LO1;2M6Y;5TKG;", "xref_pdb", result);
        verify("P31689;", "xref_smr", result);
        String pdb3d = "NMR spectroscopy (2); X-ray crystallography (1)";
        verify(pdb3d, "structure_3d", result);
    }

    @Test
    void hasIntactAndString() {
        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createXref(new UniProtKBDatabaseImpl("IntAct"), "P31689", "97", null, null, null));

        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("STRING"),
                        "9606.ENSP00000369127",
                        "-",
                        null,
                        null,
                        null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        verify("P31689;", "xref_intact", result);
        verify("9606.ENSP00000369127;", "xref_string", result);
    }

    @Test
    void hasChemblAndSwissLipids() {
        List<UniProtKBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("ChEMBL"),
                        "CHEMBL2189122",
                        "-",
                        null,
                        null,
                        null));

        xrefs.add(
                createXref(
                        new UniProtKBDatabaseImpl("SwissLipids"),
                        "SLP:000000475",
                        "-",
                        null,
                        null,
                        null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        verify("CHEMBL2189122;", "xref_chembl", result);
        verify("SLP:000000475;", "xref_swisslipids", result);
    }

    @Test
    void testBbXrefToString() {
        UniProtKBCrossReference dbxref =
                createXref(
                        new UniProtKBDatabaseImpl("EMBL"),
                        "AY189288",
                        "AAO86732.1",
                        "-",
                        "mRNA",
                        null);
        String result = EntryCrossReferenceMap.dbXrefToString(dbxref);
        assertEquals("AY189288", result);
    }

    @Test
    void testProteomeXrefToString() {
        // UP000006548: Chromosome 4
        UniProtKBCrossReference dbxref =
                createXref(
                        new UniProtKBDatabaseImpl("Proteomes"),
                        "UP000006548",
                        "Chromosome 4",
                        null,
                        null,
                        null);
        String result = EntryCrossReferenceMap.proteomeXrefToString(dbxref);
        assertEquals("UP000006548: Chromosome 4", result);
    }

    private void verify(String expected, String field, Map<String, String> result) {
        String evaluated = result.get(field);
        assertEquals(expected, evaluated);
    }

    private UniProtKBCrossReference createXref(
            UniProtKBDatabase type,
            String id,
            String desc,
            String third,
            String fourth,
            String isoform) {
        return new UniProtCrossReferenceBuilder()
                .database(type)
                .isoformId(isoform)
                .id(id)
                .propertiesAdd(type.getUniProtDatabaseAttribute(0), desc)
                .propertiesAdd(type.getUniProtDatabaseAttribute(1), third)
                .propertiesAdd(type.getUniProtDatabaseAttribute(2), fourth)
                .build();
    }
}
