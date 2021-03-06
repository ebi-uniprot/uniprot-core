package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineConverter;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.evidence.Evidence;

class KwLineConverterTest {
    private final KwLineConverter converter = new KwLineConverter(new HashMap<>(), true);

    @Test
    void test() {
        // KW   Activator; Complete proteome; Reference proteome; Transcription
        KwLineObject obj = new KwLineObject();
        obj.keywords.add("Activator");
        obj.keywords.add("Complete proteome");
        obj.keywords.add("Reference proteome");
        obj.keywords.add("Transcription");
        List<String> keywords = new ArrayList<>();
        keywords.add("Activator");
        keywords.add("Complete proteome");
        keywords.add("Reference proteome");
        keywords.add("Transcription");
        List<Keyword> kws = converter.convert(obj);
        assertEquals(4, kws.size());
        for (Keyword kw : kws) {
            keywords.contains(kw.getName());
        }
    }

    @Test
    void testEvidences() {
        // KW   Activator{EI1}; Complete proteome{EI2};
        // KW   Reference proteome; Transcription{EI2,EI3};

        KwLineObject obj = new KwLineObject();
        obj.keywords.add("Activator");
        obj.keywords.add("Complete proteome");
        obj.keywords.add("Reference proteome");
        obj.keywords.add("Transcription");
        List<String> evIds = new ArrayList<String>();
        evIds.add("ECO:0000256|RuleBase:RU000681");
        obj.evidenceInfo.getEvidences().put("Activator", evIds);
        evIds = new ArrayList<String>();
        evIds.add("ECO:0000256|RuleBase:RU000682");
        obj.evidenceInfo.getEvidences().put("Complete proteome", evIds);
        evIds = new ArrayList<String>();
        evIds.add("ECO:0000256|RuleBase:RU000682");
        evIds.add("ECO:0000256|RuleBase:RU000683");
        obj.evidenceInfo.getEvidences().put("Transcription", evIds);

        List<Keyword> kws = converter.convert(obj);
        assertEquals(4, kws.size());
        Keyword kw1 = kws.get(0);
        Keyword kw2 = kws.get(1);
        Keyword kw3 = kws.get(2);
        Keyword kw4 = kws.get(3);
        for (Keyword kw : kws) {
            if (kw.getName().equals("Activator")) {
                kw1 = kw;
            } else if (kw.getName().equals("Complete proteome")) {
                kw2 = kw;
            } else if (kw.getName().equals("Reference proteome")) {
                kw3 = kw;
            } else if (kw.getName().equals("Transcription")) {
                kw4 = kw;
            }
        }
        assertNotNull(kw1);
        assertNotNull(kw2);
        assertNotNull(kw3);
        assertNotNull(kw4);
        List<Evidence> eviIds = kw1.getEvidences();
        assertEquals(1, eviIds.size());
        Evidence eviId = eviIds.get(0);
        assertEquals("ECO:0000256|RuleBase:RU000681", eviId.getValue());
        eviIds = kw2.getEvidences();
        assertEquals(1, eviIds.size());
        eviId = eviIds.get(0);
        assertEquals("ECO:0000256|RuleBase:RU000682", eviId.getValue());
        eviIds = kw3.getEvidences();
        assertEquals(0, eviIds.size());
        eviIds = kw4.getEvidences();
        assertEquals(2, eviIds.size());
        eviId = eviIds.get(0);
        Evidence eviId2 = eviIds.get(1);
        assertEquals("ECO:0000256|RuleBase:RU000682", eviId.getValue());
        assertEquals("ECO:0000256|RuleBase:RU000683", eviId2.getValue());
    }
}
