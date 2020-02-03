package org.uniprot.core.cv.xdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.validator.DBXRef;
import org.uniprot.core.cv.xdb.validator.DBXRefReader;
import org.uniprot.core.cv.xdb.validator.DBXRefValidator;
import org.uniprot.core.util.Pair;

class DBXRefValidatorIT {
    private static Set<String> IGNORED_DBS;
    private static Set<String> NEW_DBS;

    @BeforeAll
    static void setup() throws FileNotFoundException {
        loadIgnoredDB();
        loadNewDB();
    }

    static void loadIgnoredDB() throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/main/resources/META-INF/ignored_db_list.txt"));
        IGNORED_DBS = new HashSet<>();
        while (s.hasNextLine()) {
            IGNORED_DBS.add(s.nextLine());
        }
        s.close();
    }

    static void loadNewDB() throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/main/resources/META-INF/new_db_list.txt"));
        NEW_DBS = new HashSet<>();
        while (s.hasNextLine()) {
            NEW_DBS.add(s.nextLine());
        }
        s.close();
    }

    @Test
    void testSuccessfulValidation() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("EMBL");
        // validate
        List<Pair<String, String>> mismatches = DBXRefValidator.validate(opType);
        assertEquals(1, mismatches.size());
    }

    @Test
    void testFailedValidation() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ArachnoServer");

        UniProtXDbTypeDetail actualOpType =
                new UniProtXDbTypeDetail(
                        opType.getName(),
                        opType.getDisplayName(),
                        DatabaseCategory.CHEMISTRY,
                        opType.getUriLink(),
                        opType.getAttributes(),
                        false,
                        null);

        // validate, the category should mismatch
        List<Pair<String, String>> mismatches = DBXRefValidator.validate(actualOpType);
        assertEquals(2, mismatches.size());
        assertEquals(
                DatabaseCategory.ORGANISM_SPECIFIC_DATABASES.getDisplayName(),
                mismatches.get(0).getKey()); // expected
        assertEquals(
                DatabaseCategory.CHEMISTRY.getDisplayName(),
                mismatches.get(0).getValue()); // actual
    }

    @Disabled
    @Test
    void testValidateEachDBXRef() {
        // check if all the drlineconfig.json is in sync with dbxref.txt
        for (UniProtXDbTypeDetail dbTypeDetail : UniProtXDbTypes.INSTANCE.getAllDBXRefTypes()) {
            if (!NEW_DBS.contains(dbTypeDetail.getName())) {
                System.out.println(dbTypeDetail.getName());
                List<Pair<String, String>> mismatches = DBXRefValidator.validate(dbTypeDetail);
                assertTrue(
                        mismatches.isEmpty(),
                        mismatches.stream()
                                .map(p -> p.getKey() + " : " + p.getValue())
                                .collect(Collectors.toList())
                                .toString());
            }
        }
    }

    @Disabled
    @Test
    void testCompareDRLineConfig() throws IOException {
        // check if all dbxref db are there in drlineconfig.json file except for the
        // ignored dbs
        Map<String, UniProtXDbTypeDetail> typeMap = UniProtXDbTypes.INSTANCE.getTypeMap();

        try (DBXRefReader reader = new DBXRefReader(DBXRefValidator.DBREF_FTP)) {
            DBXRef dbXRef;
            while ((dbXRef = reader.read()) != null) {
                String linkType = dbXRef.getLinkType();
                if (linkType.equals("Explicit")) {
                    if (!IGNORED_DBS.contains(dbXRef.getAbbr())) {
                        assertTrue(
                                typeMap.containsKey(dbXRef.getAbbr()),
                                "DBXref "
                                        + dbXRef.getAbbr()
                                        + " is not there in drlineconfiguration");
                        List<Pair<String, String>> mismatches =
                                DBXRefValidator.validate(typeMap.get(dbXRef.getAbbr()));

                        assertTrue(
                                mismatches.isEmpty(),
                                mismatches.stream()
                                        .map(p -> p.getKey() + " : " + p.getValue())
                                        .collect(Collectors.toList())
                                        .toString());
                    }
                }
            }
        }
    }
}
