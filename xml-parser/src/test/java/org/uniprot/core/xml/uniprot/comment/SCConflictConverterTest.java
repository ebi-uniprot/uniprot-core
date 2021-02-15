package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.xml.jaxb.uniprot.CommentType.Conflict;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

@Slf4j
class SCConflictConverterTest {

    @Test
    void testSequence() {
        String sequence = "BAA86482.2";
        SCConflictConverter converter = new SCConflictConverter();
        Conflict conflict = converter.toXml(sequence);
        conflict.setType("erroneous initiation");
        log.debug(UniProtXmlTestHelper.toXmlString(conflict, Conflict.class, "confict"));
        String converted = converter.fromXml(conflict);
        assertEquals(sequence, converted);
    }

    @Test
    void testRef() {
        String sequence = "Ref.1";
        SCConflictConverter converter = new SCConflictConverter();
        Conflict conflict = converter.toXml(sequence);
        conflict.setType("erroneous initiation");
        log.debug(UniProtXmlTestHelper.toXmlString(conflict, Conflict.class, "confict"));
        String converted = converter.fromXml(conflict);
        assertEquals(sequence, converted);
    }
}
