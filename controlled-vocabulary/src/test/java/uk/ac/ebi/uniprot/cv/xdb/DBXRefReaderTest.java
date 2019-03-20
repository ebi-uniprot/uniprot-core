package uk.ac.ebi.uniprot.cv.xdb;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.cv.xdb.validator.DBXRefReader;
import uk.ac.ebi.uniprot.cv.xdb.validator.DBXRefValidator;

import java.io.IOException;

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
