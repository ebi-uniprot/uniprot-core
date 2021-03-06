package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineConverter;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineObject;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.gene.GeneName;
import org.uniprot.core.gene.GeneNameSynonym;
import org.uniprot.core.gene.ORFName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

class GnLineConverterTest {
    private final GnLineConverter converter = new GnLineConverter();

    @Test
    void test1() {
        // GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
        GnLineObject gnLineObj = new GnLineObject();
        GnLineObject.GnName gnName = new GnLineObject.GnName();
        gnName.type = GnLineObject.GnNameType.GENAME;
        gnName.names.add("Jon99Cii");
        GnLineObject.GnName gnSName = new GnLineObject.GnName();
        gnSName.type = GnLineObject.GnNameType.SYNNAME;
        gnSName.names.add("SER1");
        gnSName.names.add("SER5");
        gnSName.names.add("Ser99Da");

        GnLineObject.GnName gnOrfName = new GnLineObject.GnName();
        gnOrfName.type = GnLineObject.GnNameType.ORFNAME;
        gnOrfName.names.add("CG7877");
        GnLineObject.GnObject gnObj = new GnLineObject.GnObject();
        gnObj.names.add(gnName);
        gnObj.names.add(gnSName);
        gnObj.names.add(gnOrfName);
        gnLineObj.gnObjects.add(gnObj);
        List<Gene> genes = converter.convert(gnLineObj);
        assertEquals(1, genes.size());
        Gene gene = genes.get(0);
        assertTrue(gene.hasGeneName());
        GeneName gname = gene.getGeneName();
        assertEquals("Jon99Cii", gname.getValue());
        assertEquals(3, gene.getSynonyms().size());
        List<String> syn = new ArrayList<String>();
        syn.add("SER1");
        syn.add("SER5");
        syn.add("Ser99Da");
        for (EvidencedValue val : gene.getSynonyms()) {
            validate(val, syn);
        }
        assertEquals(0, gene.getOrderedLocusNames().size());

        assertEquals(1, gene.getOrfNames().size());
        List<String> orf = new ArrayList<String>();
        orf.add("CG7877");
        validate(gene.getOrfNames().get(0), orf);
    }

    @Test
    void test2() {
        // GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
        //  |GN   and
        //  |GN   Name=Jon99Cii2;

        GnLineObject gnLineObj = new GnLineObject();
        GnLineObject.GnName gnName = new GnLineObject.GnName();
        gnName.type = GnLineObject.GnNameType.GENAME;
        gnName.names.add("Jon99Cii");
        GnLineObject.GnName gnSName = new GnLineObject.GnName();
        gnSName.type = GnLineObject.GnNameType.SYNNAME;
        gnSName.names.add("SER1");
        gnSName.names.add("SER5");
        gnSName.names.add("Ser99Da");

        GnLineObject.GnName gnOrfName = new GnLineObject.GnName();
        gnOrfName.type = GnLineObject.GnNameType.ORFNAME;
        gnOrfName.names.add("CG7877");
        GnLineObject.GnObject gnObj = new GnLineObject.GnObject();
        gnObj.names.add(gnName);
        gnObj.names.add(gnSName);
        gnObj.names.add(gnOrfName);
        gnLineObj.gnObjects.add(gnObj);
        GnLineObject.GnObject gnObj2 = new GnLineObject.GnObject();
        GnLineObject.GnName gnName2 = new GnLineObject.GnName();
        gnName2.type = GnLineObject.GnNameType.GENAME;
        gnName2.names.add("Jon99Cii2");
        gnObj2.names.add(gnName2);
        gnLineObj.gnObjects.add(gnObj2);

        List<Gene> genes = converter.convert(gnLineObj);
        assertEquals(2, genes.size());
        Gene gene = genes.get(0);
        Gene gene2 = genes.get(1);
        assertTrue(gene.hasGeneName());
        GeneName gname = gene.getGeneName();
        assertEquals("Jon99Cii", gname.getValue());
        assertEquals(3, gene.getSynonyms().size());
        List<String> syn = new ArrayList<String>();
        syn.add("SER1");
        syn.add("SER5");
        syn.add("Ser99Da");
        for (EvidencedValue val : gene.getSynonyms()) {
            validate(val, syn);
        }
        assertEquals(0, gene.getOrderedLocusNames().size());

        assertEquals(1, gene.getOrfNames().size());
        List<String> orf = new ArrayList<String>();
        orf.add("CG7877");
        validate(gene.getOrfNames().get(0), orf);

        assertTrue(gene2.hasGeneName());
        assertEquals("Jon99Cii2", gene2.getGeneName().getValue());
        assertEquals(0, gene2.getSynonyms().size());
        assertEquals(0, gene2.getOrderedLocusNames().size());
        assertEquals(0, gene2.getOrfNames().size());
    }

    @Test
    void testEvidenceTag() {
        /*
        * GN   Name=blaCTX-M-14{EI4}; Synonyms=beta-lactamase CTX-M-14{EI7},
               GN   bla-CTX-M-14a{EI8, EI9}, ORFNames=ETN48_p0088{EI5}
        */
        GnLineObject gnLineObj = new GnLineObject();
        GnLineObject.GnName gnName = new GnLineObject.GnName();
        gnName.type = GnLineObject.GnNameType.GENAME;
        gnName.names.add("blaCTX-M-14");
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000255|HAMAP-Rule:PRU10084");
        gnName.getEvidenceInfo().getEvidences().put("blaCTX-M-14", evs);
        GnLineObject.GnName gnSName = new GnLineObject.GnName();
        gnSName.type = GnLineObject.GnNameType.SYNNAME;
        gnSName.names.add("beta-lactamase CTX-M-14");
        gnSName.names.add("bla-CTX-M-14a");
        evs = new ArrayList<>();
        evs.add("ECO:0000255|HAMAP-Rule:PRU10087");
        gnSName.getEvidenceInfo().getEvidences().put("beta-lactamase CTX-M-14", evs);
        evs = new ArrayList<>();
        evs.add("ECO:0000255|HAMAP-Rule:PRU10088");
        evs.add("ECO:0000255|HAMAP-Rule:PRU10089");
        gnSName.getEvidenceInfo().getEvidences().put("bla-CTX-M-14a", evs);

        GnLineObject.GnName gnOrfName = new GnLineObject.GnName();
        gnOrfName.type = GnLineObject.GnNameType.ORFNAME;
        gnOrfName.names.add("ETN48_p0088");
        evs = new ArrayList<>();
        evs.add("ECO:0000255|HAMAP-Rule:PRU10085");
        gnOrfName.getEvidenceInfo().getEvidences().put("ETN48_p0088", evs);
        GnLineObject.GnObject gnObj = new GnLineObject.GnObject();
        gnObj.names.add(gnName);
        gnObj.names.add(gnSName);
        gnObj.names.add(gnOrfName);
        gnLineObj.gnObjects.add(gnObj);
        List<Gene> genes = converter.convert(gnLineObj);
        assertEquals(1, genes.size());
        Gene gene = genes.get(0);
        assertTrue(gene.hasGeneName());
        GeneName gname = gene.getGeneName();

        assertEquals("blaCTX-M-14", gname.getValue());
        List<Evidence> evIDs = gname.getEvidences();
        assertEquals(1, evIDs.size());
        Evidence evId = evIDs.get(0);

        assertEquals("ECO:0000255|HAMAP-Rule:PRU10084", evId.getValue());
        assertEquals(2, gene.getSynonyms().size());
        List<String> syn = new ArrayList<String>();
        syn.add("beta-lactamase CTX-M-14");
        syn.add("bla-CTX-M-14a");
        GeneNameSynonym synName1 = gene.getSynonyms().get(0);
        GeneNameSynonym synName2 = gene.getSynonyms().get(1);
        for (GeneNameSynonym val : gene.getSynonyms()) {
            validate(val, syn);
            if (val.getValue().equals("bla-CTX-M-14a")) {
                synName2 = val;
            }
            if (val.getValue().equals("beta-lactamase CTX-M-14")) {
                synName1 = val;
            }
        }
        evIDs = synName1.getEvidences();
        assertEquals(1, evIDs.size());
        evId = evIDs.get(0);

        assertEquals("ECO:0000255|HAMAP-Rule:PRU10087", evId.getValue());

        evIDs = synName2.getEvidences();
        assertEquals(2, evIDs.size());
        evId = evIDs.get(0);
        Evidence evId2 = evIDs.get(1);
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10088", evId.getValue());
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10089", evId2.getValue());

        assertEquals(0, gene.getOrderedLocusNames().size());

        assertEquals(1, gene.getOrfNames().size());
        List<String> orf = new ArrayList<String>();
        orf.add("ETN48_p0088");
        validate(gene.getOrfNames().get(0), orf);
        ORFName orfName = gene.getOrfNames().get(0);
        evIDs = orfName.getEvidences();
        assertEquals(1, evIDs.size());
        evId = evIDs.get(0);

        assertEquals("ECO:0000255|HAMAP-Rule:PRU10085", evId.getValue());
    }

    private void validate(EvidencedValue val, List<String> vals) {
        assertTrue(vals.contains(val.getValue()));
    }
}
