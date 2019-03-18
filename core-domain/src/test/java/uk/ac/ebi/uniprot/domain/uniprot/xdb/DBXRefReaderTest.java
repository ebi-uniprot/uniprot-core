package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.Pair;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.validator.DBXRef;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.validator.DBXRefReader;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.validator.DBXRefValidator;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DBXRefReaderTest {

    @Test
    void testReadAll() throws IOException {
        int count = 0;
        try(DBXRefReader reader = new DBXRefReader(DBXRefValidator.DBREF_FTP)) {
            while (reader.read() != null) {
                count++;
            }
        }
        assertTrue(count >= 171);
    }
}
