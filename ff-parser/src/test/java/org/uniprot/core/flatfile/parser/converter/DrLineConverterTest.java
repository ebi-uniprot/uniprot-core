package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.exception.DatabaseTypeNotExistException;
import org.uniprot.core.flatfile.parser.impl.dr.DrLineConverter;
import org.uniprot.core.flatfile.parser.impl.dr.DrLineObject;
import org.uniprot.core.flatfile.parser.impl.dr.UniProtDrObjects;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

class DrLineConverterTest {
    private final DrLineConverter converter = new DrLineConverter();

    @Test
    void testEmbl() {
        DrLineObject obj = new DrLineObject();
        obj.drObjects.add(
                creatDrObject("EMBL", "AY548484", "AAT09660.1", "someValue", "Genomic_DNA"));
        UniProtDrObjects drObjects = converter.convert(obj);
        List<UniProtKBCrossReference> xrefs = drObjects.drObjects;
        assertEquals(1, xrefs.size());
        validate(xrefs.get(0), "EMBL", "AY548484", "AAT09660.1", "someValue", "Genomic_DNA");
    }

    @Test
    void test() {
        /**
         * val drLine = """DR EMBL; AY548484; AAT09660.1; -; Genomic_DNA. |DR RefSeq; YP_031579.1;
         * NC_005946.1. |DR ProteinModelPortal; Q6GZX4; -. |DR GeneID; 2947773; -. |DR ProtClustDB;
         * CLSP2511514; -. |DR GO; GO:0006355; P:regulation of transcription, DNA-dependent;
         * IEA:UniProtKB-KW. |DR GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
         * |DR InterPro; IPR007031; Poxvirus_VLTF3.
         */
        DrLineObject obj = new DrLineObject();
        obj.drObjects.add(creatDrObject("EMBL", "AY548484", "AAT09660.1", "-", "Genomic_DNA"));
        obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", null, null));
        obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null));
        obj.drObjects.add(
                creatDrObject(
                        "GO",
                        "GO:0006355",
                        "P:regulation of transcription, DNA-dependent",
                        "IEA:UniProtKB-KW",
                        null));
        obj.drObjects.add(
                creatDrObject(
                        "GO",
                        "GO:0046782",
                        "P:regulation of viral transcription",
                        "IEA:InterPro",
                        null));
        obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null));
        UniProtDrObjects drObjects = converter.convert(obj);
        List<UniProtKBCrossReference> xrefs = drObjects.drObjects;
        assertEquals(6, xrefs.size());
        validate(xrefs.get(0), "EMBL", "AY548484", "AAT09660.1", "-", "Genomic_DNA");
        validate(xrefs.get(1), "REFSEQ", "YP_031579.1", "NC_005946.1", null, null);
        validate(xrefs.get(2), "GENEID", "2947773", "-", null, null);
        validate(
                xrefs.get(3),
                "GO",
                "GO:0006355",
                "P:regulation of transcription, DNA-dependent",
                "IEA:UniProtKB-KW",
                null);
        validate(
                xrefs.get(4),
                "GO",
                "GO:0046782",
                "P:regulation of viral transcription",
                "IEA:InterPro",
                null);
        validate(xrefs.get(5), "INTERPRO", "IPR007031", "Poxvirus_VLTF3", null, null);
    }

    @Test
    void testWrongDr() throws Exception {
        /** val drLine = """DR EMBL2; AY548484; AAT09660.1; -; Genomic_DNA. */
        DrLineObject obj = new DrLineObject();
        obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"));

        assertThrows(DatabaseTypeNotExistException.class, () -> converter.convert(obj));
    }

    @Test
    void testIgnoreWrongDr() throws Exception {
        /** val drLine = """DR EMBL2; AY548484; AAT09660.1; -; Genomic_DNA. */
        DrLineObject obj = new DrLineObject();
        obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"));
        DrLineConverter converter2 = new DrLineConverter(true);
        UniProtDrObjects drObjects = converter2.convert(obj);
        assertNotNull(drObjects);
    }

    @Test
    void testIgnoreWrongDrComprehensive() {
        /**
         * val drLine = """DR EMBL; AY548484; AAT09660.1; -; Genomic_DNA. |DR RefSeq; YP_031579.1;
         * NC_005946.1. |DR ProteinModelPortal; Q6GZX4; -. |DR GeneID; 2947773; -. |DR ProtClustDB;
         * CLSP2511514; -. |DR GO; GO:0006355; P:regulation of transcription, DNA-dependent;
         * IEA:UniProtKB-KW. |DR GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
         * |DR InterPro; IPR007031; Poxvirus_VLTF3.
         */
        DrLineObject obj = new DrLineObject();
        obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"));
        obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", null, null));
        obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null));
        obj.drObjects.add(
                creatDrObject(
                        "GO",
                        "GO:0006355",
                        "P:regulation of transcription, DNA-dependent",
                        "IEA:UniProtKB-KW",
                        null));
        obj.drObjects.add(
                creatDrObject(
                        "GO",
                        "GO:0046782",
                        "P:regulation of viral transcription",
                        "IEA:InterPro",
                        null));
        obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null));
        DrLineConverter converter2 = new DrLineConverter(true);
        UniProtDrObjects drObjects = converter2.convert(obj);
        List<UniProtKBCrossReference> xrefs = drObjects.drObjects;
        assertEquals(5, xrefs.size());
        //	validate( xrefs.get(0), "EMBL,
        //			"AY548484", "AAT09660.1", "-", "Genomic_DNA");

        validate(xrefs.get(0), "REFSEQ", "YP_031579.1", "NC_005946.1", null, null);
        validate(xrefs.get(1), "GENEID", "2947773", "-", null, null);
        validate(
                xrefs.get(2),
                "GO",
                "GO:0006355",
                "P:regulation of transcription, DNA-dependent",
                "IEA:UniProtKB-KW",
                null);
        validate(
                xrefs.get(3),
                "GO",
                "GO:0046782",
                "P:regulation of viral transcription",
                "IEA:InterPro",
                null);
        validate(xrefs.get(4), "INTERPRO", "IPR007031", "Poxvirus_VLTF3", null, null);
    }

    @Test
    void testIgnoreWrongDR3() {
        /**
         * val drLine = """DR EMBL; AY548484; AAT09660.1; -; Genomic_DNA. |DR RefSeq; YP_031579.1;
         * NC_005946.1. |DR ProteinModelPortal; Q6GZX4; -. |DR GeneID; 2947773; -. |DR ProtClustDB;
         * CLSP2511514; -. |DR GO; GO:0006355; P:regulation of transcription, DNA-dependent;
         * IEA:UniProtKB-KW. |DR GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
         * |DR InterPro; IPR007031; Poxvirus_VLTF3.
         */
        DrLineObject obj = new DrLineObject();
        obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"));
        obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", null, null));
        obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null));
        obj.drObjects.add(
                creatDrObject(
                        "GO",
                        "GO:0006355",
                        "P:regulation of transcription, DNA-dependent",
                        "IEA:UniProtKB-KW",
                        null));
        obj.drObjects.add(
                creatDrObject(
                        "GO", "GO:0046782", "P:regulation of viral transcription", null, null));
        obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null));
        DrLineConverter converter2 = new DrLineConverter(true);
        UniProtDrObjects drObjects = converter2.convert(obj);
        List<UniProtKBCrossReference> xrefs = drObjects.drObjects;
        assertEquals(5, xrefs.size());
        //	validate( xrefs.get(0), "EMBL,
        //			"AY548484", "AAT09660.1", "-", "Genomic_DNA");

        validate(xrefs.get(0), "REFSEQ", "YP_031579.1", "NC_005946.1", null, null);
        validate(xrefs.get(1), "GENEID", "2947773", "-", null, null);
        validate(
                xrefs.get(2),
                "GO",
                "GO:0006355",
                "P:regulation of transcription, DNA-dependent",
                "IEA:UniProtKB-KW",
                null);
        //	validate( xrefs.get(4), "GO,
        //			"GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null );
        validate(xrefs.get(4), "InterPro", "IPR007031", "Poxvirus_VLTF3", null, null);
    }

    @Test
    void testIgnoreWrongDR2() {
        /**
         * val drLine = """DR EMBL; AY548484; AAT09660.1; -; Genomic_DNA. |DR RefSeq; YP_031579.1;
         * NC_005946.1. |DR ProteinModelPortal; Q6GZX4; -. |DR GeneID; 2947773; -. |DR ProtClustDB;
         * CLSP2511514; -. |DR GO; GO:0006355; P:regulation of transcription, DNA-dependent;
         * IEA:UniProtKB-KW. |DR GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
         * |DR InterPro; IPR007031; Poxvirus_VLTF3.
         */
        DrLineObject obj = new DrLineObject();
        obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"));
        obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", "notdata", null));
        obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null));
        obj.drObjects.add(
                creatDrObject(
                        "GO",
                        "GO:0006355",
                        "P:regulation of transcription, DNA-dependent",
                        "IEA:UniProtKB-KW",
                        null));
        obj.drObjects.add(
                creatDrObject(
                        "GO",
                        "GO:0046782",
                        "P:regulation of viral transcription",
                        "IEA:InterPro",
                        null));
        obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null));
        DrLineConverter converter2 = new DrLineConverter(true);
        UniProtDrObjects drObjects = converter2.convert(obj);
        List<UniProtKBCrossReference> xrefs = drObjects.drObjects;
        assertEquals(5, xrefs.size());
        //	validate( xrefs.get(0), "EMBL,
        //			"AY548484", "AAT09660.1", "-", "Genomic_DNA");

        validate(xrefs.get(0), "REFSEQ", "YP_031579.1", "NC_005946.1", null, null);
        validate(xrefs.get(1), "GENEID", "2947773", "-", null, null);
        validate(
                xrefs.get(2),
                "GO",
                "GO:0006355",
                "P:regulation of transcription, DNA-dependent",
                "IEA:UniProtKB-KW",
                null);
        validate(
                xrefs.get(3),
                "GO",
                "GO:0046782",
                "P:regulation of viral transcription",
                "IEA:InterPro",
                null);
        validate(xrefs.get(4), "INTERPRO", "IPR007031", "Poxvirus_VLTF3", null, null);
    }

    @Test
    void testEvidence() {
        // "DR   EMBL; CP001509; ACT41999.1; -; Genomic_DNA.{EI3}
        // DR   EMBL; AM946981; CAQ30614.1; -; Genomic_DNA.{EI4}
        // DR   GeneID; 2947773; -.

        DrLineObject obj = new DrLineObject();
        DrLineObject.DrObject obj1 =
                creatDrObject("EMBL", "CP001509", "ACT41999.1", "-", "Genomic_DNA");
        DrLineObject.DrObject obj2 =
                creatDrObject("EMBL", "AM946981", "CAQ30614.1", "-", "Genomic_DNA");
        DrLineObject.DrObject obj3 = creatDrObject("GeneID", "2947773", "-", null, null);
        obj.drObjects.add(obj1);
        obj.drObjects.add(obj2);
        obj.drObjects.add(obj3);

        UniProtDrObjects drObjects = converter.convert(obj);
        List<UniProtKBCrossReference> xrefs = drObjects.drObjects;
        assertEquals(3, xrefs.size());
        UniProtKBCrossReference xref1 = xrefs.get(0);
        UniProtKBCrossReference xref2 = xrefs.get(1);
        UniProtKBCrossReference xref3 = xrefs.get(2);
        validate(xref1, "EMBL", "CP001509", "ACT41999.1", "-", "Genomic_DNA");
        validate(xref2, "EMBL", "AM946981", "CAQ30614.1", "-", "Genomic_DNA");
        validate(xref3, "GENEID", "2947773", "-", null, null);
    }

    private void validate(
            UniProtKBCrossReference xref,
            String type,
            String first,
            String second,
            String third,
            String fourth) {
        assertEquals(first, xref.getId());
        assertEquals(second, xref.getProperties().get(0).getValue());
        if (third != null) {
            assertTrue(xref.getProperties().size() >= 2);
            assertEquals(third, xref.getProperties().get(1).getValue());
        } else {
            assertFalse(xref.getProperties().size() >= 2);
        }
        if (fourth != null) {
            assertTrue(xref.getProperties().size() >= 3);
            assertEquals(fourth, xref.getProperties().get(2).getValue());
        } else {
            assertFalse(xref.getProperties().size() >= 3);
        }
    }

    private DrLineObject.DrObject creatDrObject(
            String dbname, String first, String second, String third, String fourth) {
        DrLineObject.DrObject dr1 = new DrLineObject.DrObject();
        dr1.DbName = dbname;
        dr1.attributes.add(first);
        dr1.attributes.add(second);
        if (third != null) dr1.attributes.add(third);
        if (fourth != null) dr1.attributes.add(fourth);
        return dr1;
    }
}
