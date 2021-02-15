package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.EvidenceHelper.createEvidences;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.KeywordBuilder;
import org.uniprot.core.xml.jaxb.uniprot.KeywordType;

@Slf4j
class KeywordConverterTest {
    @Test
    void test() {
        String val = "Transmembrane";
        String id = "KW-0812";
        List<Evidence> evidences = createEvidences();
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper(evidences);
        KeywordConverter converter = new KeywordConverter(evRefMapper);
        Keyword keyword =
                new KeywordBuilder()
                        .id(id)
                        .name(val)
                        .category(KeywordCategory.UNKNOWN)
                        .evidencesSet(evidences)
                        .build();
        KeywordType xmlObj = converter.toXml(keyword);

        assertEquals(id, xmlObj.getId());
        assertEquals(val, xmlObj.getValue());
        assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
        log.debug(UniProtXmlTestHelper.toXmlString(xmlObj, KeywordType.class, "keyword"));

        Keyword converted = converter.fromXml(xmlObj);
        assertEquals(keyword, converted);
    }
}
