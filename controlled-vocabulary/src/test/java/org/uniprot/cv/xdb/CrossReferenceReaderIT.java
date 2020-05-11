package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.uniprot.cv.common.CVSystemProperties.getDatabaseTypesLocation;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.uniprot.cv.xdb.validator.CrossReferenceReader;
import org.uniprot.cv.xdb.validator.CrossReferenceValidator;
import org.uniprot.cv.xdb.validator.DBXRef;

class CrossReferenceReaderIT {
    private static Set<String> ACCESSION_WITHOUT_REF =
            new HashSet<>(
                    Arrays.asList(
                            "DB-0133", "DB-0225", "DB-0018", "DB-0168", "DB-0188", "DB-0227",
                            "DB-0055", "DB-0061", "DB-0161", "DB-0067", "DB-0219", "DB-0068",
                            "DB-0072", "DB-0078", "DB-0090", "DB-0099", "DB-0106", "DB-0047",
                            "DB-0236", "DB-0237", "DB-0244"));

    @Test
    void testReadAll() throws IOException {
        int count = 0;
        String ftpUrl =
                Optional.ofNullable(getDatabaseTypesLocation())
                        .orElse(CrossReferenceValidator.DBREF_FTP);
        System.out.println("FTP location found: " + ftpUrl);
        try (CrossReferenceReader reader = new CrossReferenceReader(ftpUrl)) {
            DBXRef dbxRef;
            while ((dbxRef = reader.read()) != null) {
                verifyDBXRef(dbxRef);
                count++;
            }
        }
        assertTrue(count >= 169);
    }

    private void verifyDBXRef(DBXRef dbxRef) {
        System.out.println(dbxRef.toString());
        assertNotNull(dbxRef.getAccession(), "Accession is null");
        assertNotNull(dbxRef.getAbbr(), "Abbr is null");
        assertNotNull(dbxRef.getName(), "Name is null");

        if (!ACCESSION_WITHOUT_REF.contains(dbxRef.getAccession())) {
            assertNotNull(dbxRef.getRef(), "Ref is null");
        }
        assertNotNull(dbxRef.getLinkType(), "LinkTp is null");
        assertNotNull(dbxRef.getServer(), "Server is null");
        assertNotNull(dbxRef.getDbUrl(), "db_Url is null");
        assertNotNull(dbxRef.getCategory(), "Cat is null");
    }
}
