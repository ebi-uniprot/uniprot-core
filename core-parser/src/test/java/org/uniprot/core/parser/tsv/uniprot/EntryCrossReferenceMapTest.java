package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtkbDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.cv.xdb.UniProtkbDatabaseImpl;

class EntryCrossReferenceMapTest {

    @Test
    void testGetDataEmpty() {
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(null);
        Map<String, String> result = dl.attributeValues();
        assertTrue(result.isEmpty());
    }

    @Test
    void hasEmbl() {
        List<UniProtkbCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("EMBL"),
                        "AY189288",
                        "AAO86732.1",
                        "-",
                        "mRNA",
                        null));
        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("EMBL"),
                        "AK022746",
                        "BAB14220.1",
                        "-",
                        "mRNA",
                        null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(1, result.size());
        verify("AY189288;AK022746;", "dr_embl", result);
    }

    @Test
    void hasEmblAndEnsembl() {
        List<UniProtkbCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("EMBL"),
                        "AY189288",
                        "AAO86732.1",
                        "-",
                        "mRNA",
                        null));
        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("EMBL"),
                        "AK022746",
                        "BAB14220.1",
                        "-",
                        "mRNA",
                        null));
        // Ensembl; ENST00000330899; ENSP00000369127; ENSG00000086061. [P31689-1]
        // Ensembl; ENST00000439351; ENSP00000414398; ENSG00000090520.
        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("Ensembl"),
                        "ENST00000330899",
                        "ENSP00000369127",
                        "ENSG00000086061",
                        null,
                        "P31689-1"));
        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("Ensembl"),
                        "ENST00000439351",
                        "ENSP00000414398",
                        "ENSG00000090520",
                        null,
                        null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        verify("AY189288;AK022746;", "dr_embl", result);
        verify("ENST00000330899 [P31689-1];ENST00000439351;", "dr_ensembl", result);
    }

    @Test
    void hasPdbAndSmr() {
        List<UniProtkbCrossReference> xrefs = new ArrayList<>();
        xrefs.add(createXref(new UniProtkbDatabaseImpl("PDB"), "2LO1", "NMR", "-", "A=1-70", null));
        xrefs.add(createXref(new UniProtkbDatabaseImpl("PDB"), "2M6Y", "NMR", "-", "A=1-67", null));
        // PDB; 5TKG; X-ray; 1.20 A; A/B=16-23
        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("PDB"),
                        "5TKG",
                        "X-ray",
                        "1.20 A",
                        "A/B=16-23",
                        null));
        xrefs.add(createXref(new UniProtkbDatabaseImpl("SMR"), "P31689", "-", null, null, null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(3, result.size());
        verify("2LO1;2M6Y;5TKG;", "dr_pdb", result);
        verify("P31689;", "dr_smr", result);
        String pdb3d = "NMR spectroscopy (2); X-ray crystallography (1)";
        verify(pdb3d, "3d", result);
    }

    @Test
    void hasIntactAndString() {
        List<UniProtkbCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createXref(new UniProtkbDatabaseImpl("IntAct"), "P31689", "97", null, null, null));

        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("STRING"),
                        "9606.ENSP00000369127",
                        "-",
                        null,
                        null,
                        null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        verify("P31689;", "dr_intact", result);
        verify("9606.ENSP00000369127;", "dr_string", result);
    }

    @Test
    void hasChemblAndSwissLipids() {
        List<UniProtkbCrossReference> xrefs = new ArrayList<>();
        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("ChEMBL"),
                        "CHEMBL2189122",
                        "-",
                        null,
                        null,
                        null));

        xrefs.add(
                createXref(
                        new UniProtkbDatabaseImpl("SwissLipids"),
                        "SLP:000000475",
                        "-",
                        null,
                        null,
                        null));
        EntryCrossReferenceMap dl = new EntryCrossReferenceMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        verify("CHEMBL2189122;", "dr_chembl", result);
        verify("SLP:000000475;", "dr_swisslipids", result);
    }

    @Test
    void testBbXrefToString() {
        UniProtkbCrossReference dbxref =
                createXref(
                        new UniProtkbDatabaseImpl("EMBL"),
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
        UniProtkbCrossReference dbxref =
                createXref(
                        new UniProtkbDatabaseImpl("Proteomes"),
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

    private UniProtkbCrossReference createXref(
            UniProtkbDatabase type,
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
