package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;
import org.uniprot.core.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;
import org.uniprot.cv.xdb.UniProtXDbTypeImpl;

class EntryGoXrefMapTest {

    @Test
    void testFields() {
        List<String> fields = EntryGoXrefMap.FIELDS;
        List<String> expected = Arrays.asList("go", "go_c", "go_f", "go_p", "go_id");
        assertEquals(expected, fields);
    }

    @Test
    void testGetDataEmpty() {
        EntryGoXrefMap dl = new EntryGoXrefMap(null);
        Map<String, String> result = dl.attributeValues();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetDataFull() {
        List<UniProtDBCrossReference> xrefs = create();
        EntryGoXrefMap dl = new EntryGoXrefMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        String go =
                "endoplasmic reticulum [GO:0005783];"
                        + " endoplasmic reticulum lumen [GO:0005788];"
                        + " membrane [GO:0016020];"
                        + " unfolded protein binding [GO:0051082];"
                        + " IRE1-mediated unfolded protein response [GO:0036498];"
                        + " positive regulation of ATPase activity [GO:0032781];"
                        + " protein folding [GO:0006457]";

        verify(go, "go", result);
        String go_c =
                "endoplasmic reticulum [GO:0005783];"
                        + " endoplasmic reticulum lumen [GO:0005788];"
                        + " membrane [GO:0016020]";
        verify(go_c, "go_c", result);
        String go_f = "unfolded protein binding [GO:0051082]";
        verify(go_f, "go_f", result);
        String go_p =
                "IRE1-mediated unfolded protein response [GO:0036498];"
                        + " positive regulation of ATPase activity [GO:0032781];"
                        + " protein folding [GO:0006457]";

        verify(go_p, "go_p", result);
        String go_id =
                "GO:0005783; GO:0005788; GO:0006457; GO:0016020; GO:0032781; GO:0036498; GO:0051082";

        verify(go_id, "go_id", result);
    }

    @Test
    void testGetDataFullWithDownloadableDbXref() {
        List<UniProtDBCrossReference> xrefs = create();
        EntryDbXRefMap dl = new EntryDbXRefMap(xrefs);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        String go =
                "endoplasmic reticulum [GO:0005783];"
                        + " endoplasmic reticulum lumen [GO:0005788];"
                        + " membrane [GO:0016020];"
                        + " unfolded protein binding [GO:0051082];"
                        + " IRE1-mediated unfolded protein response [GO:0036498];"
                        + " positive regulation of ATPase activity [GO:0032781];"
                        + " protein folding [GO:0006457]";

        verify(go, "go", result);
        String go_c =
                "endoplasmic reticulum [GO:0005783];"
                        + " endoplasmic reticulum lumen [GO:0005788];"
                        + " membrane [GO:0016020]";
        verify(go_c, "go_c", result);
        String go_f = "unfolded protein binding [GO:0051082]";
        verify(go_f, "go_f", result);
        String go_p =
                "IRE1-mediated unfolded protein response [GO:0036498];"
                        + " positive regulation of ATPase activity [GO:0032781];"
                        + " protein folding [GO:0006457]";

        verify(go_p, "go_p", result);
        String go_id =
                "GO:0005783; GO:0005788; GO:0006457; GO:0016020; GO:0032781; GO:0036498; GO:0051082";

        verify(go_id, "go_id", result);
    }

    private void verify(String expected, String field, Map<String, String> result) {
        String evaluated = result.get(field);
        assertEquals(expected, evaluated);
    }

    private List<UniProtDBCrossReference> create() {
        List<UniProtDBCrossReference> xrefs = new ArrayList<>();
        xrefs.add(create("GO:0005783", "C:endoplasmic reticulum", "IDA:AgBase"));
        xrefs.add(create("GO:0005788", "C:endoplasmic reticulum lumen", "TAS:Reactome"));
        xrefs.add(create("GO:0016020", "C:membrane", "HDA:UniProtKB"));
        xrefs.add(create("GO:0051082", "F:unfolded protein binding", "IEA:InterPro"));
        xrefs.add(
                create("GO:0036498", "P:IRE1-mediated unfolded protein response", "TAS:Reactome"));
        xrefs.add(create("GO:0032781", "P:positive regulation of ATPase activity", "IDA:AgBase"));
        xrefs.add(create("GO:0006457", "P:protein folding", "IEA:InterPro"));
        return xrefs;
    }

    private UniProtDBCrossReference create(String id, String term, String evidence) {
        UniProtXDbType type = new UniProtXDbTypeImpl("GO");
        return new UniProtDBCrossReferenceBuilder()
                .databaseType(type)
                .id(id)
                .propertiesAdd(type.getAttribute(0), term)
                .propertiesAdd(type.getAttribute(1), evidence)
                .build();
    }
}
