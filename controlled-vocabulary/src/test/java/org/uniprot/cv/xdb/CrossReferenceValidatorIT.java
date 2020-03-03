package org.uniprot.cv.xdb;

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
import org.uniprot.core.cv.xdb.UniProtDatabaseCategory;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.util.Pair;
import org.uniprot.cv.xdb.validator.CrossReferenceReader;
import org.uniprot.cv.xdb.validator.CrossReferenceValidator;
import org.uniprot.cv.xdb.validator.DBXRef;

class CrossReferenceValidatorIT {
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
        UniProtDatabaseDetail opType = UniProtDatabaseTypes.INSTANCE.getDbTypeByName("EMBL");
        // validate
        List<Pair<String, String>> mismatches = CrossReferenceValidator.validate(opType);
        assertEquals(1, mismatches.size());
    }

    @Test
    void testFailedValidation() {
        UniProtDatabaseDetail opType =
                UniProtDatabaseTypes.INSTANCE.getDbTypeByName("ArachnoServer");

        UniProtDatabaseDetail actualOpType =
                new UniProtDatabaseDetail(
                        opType.getName(),
                        opType.getDisplayName(),
                        UniProtDatabaseCategory.CHEMISTRY,
                        opType.getUriLink(),
                        opType.getAttributes(),
                        false,
                        null);

        // validate, the category should mismatch
        List<Pair<String, String>> mismatches = CrossReferenceValidator.validate(actualOpType);
        assertEquals(2, mismatches.size());
        assertEquals(
                UniProtDatabaseCategory.ORGANISM_SPECIFIC_DATABASES.getDisplayName(),
                mismatches.get(0).getKey()); // expected
        assertEquals(
                UniProtDatabaseCategory.CHEMISTRY.getDisplayName(),
                mismatches.get(0).getValue()); // actual
    }

    @Disabled
    @Test
    void testValidateEachDBXRef() {
        // check if all the drlineconfig.json is in sync with dbxref.txt
        for (UniProtDatabaseDetail dbTypeDetail : UniProtDatabaseTypes.INSTANCE.getAllDbTypes()) {
            if (!NEW_DBS.contains(dbTypeDetail.getName())) {
                System.out.println(dbTypeDetail.getName());
                List<Pair<String, String>> mismatches =
                        CrossReferenceValidator.validate(dbTypeDetail);
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
        Map<String, UniProtDatabaseDetail> typeMap =
                UniProtDatabaseTypes.INSTANCE.getAllDbTypesMap();

        try (CrossReferenceReader reader =
                new CrossReferenceReader(CrossReferenceValidator.DBREF_FTP)) {
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
                                CrossReferenceValidator.validate(typeMap.get(dbXRef.getAbbr()));

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
