package org.uniprot.cv.xdb.validator;

import static org.uniprot.cv.common.CVSystemProperties.getDatabaseTypesLocation;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

public class CrossReferenceValidator {
    private static final String EXPLICIT = "Explicit";

    private static final Logger LOGGER = LoggerFactory.getLogger(CrossReferenceValidator.class);

    private static Map<String, DBXRef> ABB_DBXREF = new HashMap<>();
    public static final String DBREF_FTP =
            "ftp://ftp.ebi.ac.uk/pub/databases/uniprot/current_release/knowledgebase/complete/docs/dbxref.txt";

    static {
        try (CrossReferenceReader reader =
                new CrossReferenceReader(
                        Optional.ofNullable(getDatabaseTypesLocation()).orElse(DBREF_FTP))) {
            DBXRef xref;
            while ((xref = reader.read()) != null) {
                if (!xref.getLinkType().equals(EXPLICIT)) continue;
                ABB_DBXREF.put(xref.getAbbr().toLowerCase(), xref);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read the dbxref file {}", e.getMessage());
        }
    }

    private CrossReferenceValidator() {}

    public static List<Pair<String, String>> validate(UniProtDatabaseDetail dbTypeDetail) {
        DBXRef dbXRef = ABB_DBXREF.get(dbTypeDetail.getName().toLowerCase());
        List<Pair<String, String>> mismatches = new ArrayList<>();
        if (dbXRef == null) {
            LOGGER.error("DBXref {} is not there in dbxref.txt", dbTypeDetail.getName());
            mismatches.add(getMismatchPair(null, dbTypeDetail.getName()));
        } else {
            mismatches = compare(dbXRef, dbTypeDetail);
            if (!mismatches.isEmpty()) {
                LOGGER.error(
                        "There is mismatch between db xref and json for dbXRef {}",
                        dbXRef.getAbbr());
                for (Pair<String, String> m : mismatches) {
                    LOGGER.error("Mismatch pair <{}, {}>", m.getKey(), m.getValue());
                }
            }
        }
        return mismatches;
    }

    private static List<Pair<String, String>> compare(
            DBXRef dbXRef, UniProtDatabaseDetail dbTypeDetail) {
        List<Pair<String, String>> mismatches = new ArrayList<>();

        if (!Objects.equals(dbXRef.getAbbr(), dbTypeDetail.getName())) {
            mismatches.add(getMismatchPair(dbXRef.getAbbr(), dbTypeDetail.getName()));
        }

        if (!Objects.equals(dbXRef.getAbbr(), dbTypeDetail.getDisplayName())) {
            mismatches.add(getMismatchPair(dbXRef.getAbbr(), dbTypeDetail.getDisplayName()));
        }
        if (!Objects.equals(dbXRef.getCategory(), dbTypeDetail.getCategory().getDisplayName())) {
            mismatches.add(
                    getMismatchPair(
                            dbXRef.getCategory(), dbTypeDetail.getCategory().getDisplayName()));
        }

        String urlLink = dbTypeDetail.getUriLink().replace("%value", "%s");

        if (!Objects.equals(dbXRef.getDbUrl(), urlLink)) {
            mismatches.add(getMismatchPair(dbXRef.getDbUrl(), urlLink));
        }

        return mismatches;
    }

    private static Pair<String, String> getMismatchPair(String expected, String actual) {
        Pair<String, String> mismatch = new PairImpl<>(expected, actual);
        return mismatch;
    }
}
