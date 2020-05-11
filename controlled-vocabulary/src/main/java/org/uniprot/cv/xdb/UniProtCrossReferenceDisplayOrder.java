package org.uniprot.cv.xdb;

import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.CVSystemProperties;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** @author jieluo */
public enum UniProtCrossReferenceDisplayOrder
        implements org.uniprot.core.uniprotkb.xdb.DatabaseDisplayOrder<UniProtDatabaseDetail> {
    INSTANCE;

    private Map<String, DatabaseDisplayOrder> databaseType2DefsNoCase;
    private static final String DR_ORD_LOCATION = "https://www.ebi.ac.uk/~trembl/generator/dr_ord";
    private boolean init = false;

    UniProtCrossReferenceDisplayOrder() {
        initCache();
    }

    @Override
    public List<UniProtDatabaseDetail> getOrderedDatabases() {
        return SafeLazyInitializer.uniProtDatabaseDetails;
    }

    private void initCache() {
        if (init) {
            return;
        }
        databaseType2DefsNoCase = new ConcurrentHashMap<>();
        List<String> lines =
                AbstractFileReader.readLines(
                        Optional.ofNullable(CVSystemProperties.getDrOrdLocation())
                                .orElse(DR_ORD_LOCATION));
        int pos = 1;
        for (String readLine : lines) {
            readLine = readLine.trim();
            if (!readLine.startsWith("#")) {
                DatabaseDisplayOrder def = createDatabaseDef(readLine, pos);
                databaseType2DefsNoCase.put(def.getDbName().toUpperCase(), def);
                pos++;
            }
        }
        init = true;
    }

    private DatabaseDisplayOrder createDatabaseDef(String line, int pos) {
        String[] data = line.split(" ");
        String db = data[0].trim();
        int secondOrder = 1;
        if (data.length > 1) {
            secondOrder = getSecondOrder(data[1]);
        }
        return new DatabaseDisplayOrder(db, pos, secondOrder);
    }

    private int getSecondOrder(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            // ignore
        }
        return 1; // use default
    }

    private static class SafeLazyInitializer {
        static List<UniProtDatabaseDetail> uniProtDatabaseDetails =
                initValues(INSTANCE.databaseType2DefsNoCase);

        private static List<UniProtDatabaseDetail> initValues(
                Map<String, DatabaseDisplayOrder> databaseType2DefsNoCase) {
            List<UniProtDatabaseDetail> values =
                    new CopyOnWriteArrayList<>(UniProtDatabaseTypes.INSTANCE.getAllDbTypes());
            values.sort(new CrossReferenceDbOrder(databaseType2DefsNoCase));
            return values;
        }
    }

    private static class CrossReferenceDbOrder implements Comparator<UniProtDatabaseDetail> {
        private final Map<String, DatabaseDisplayOrder> databaseType2DefsNoCase;

        private CrossReferenceDbOrder(Map<String, DatabaseDisplayOrder> databaseType2DefsNoCase) {
            this.databaseType2DefsNoCase = databaseType2DefsNoCase;
        }

        @Override
        public int compare(UniProtDatabaseDetail o1, UniProtDatabaseDetail o2) {
            DatabaseDisplayOrder dbOrder1 = getXRefDBDef(o1);
            DatabaseDisplayOrder dbOrder2 = getXRefDBDef(o2);
            if ((dbOrder1 == null) && (dbOrder2 == null)) {
                return 0;
            } else if (dbOrder1 == null) {
                return 1;
            } else if (dbOrder2 == null) {
                return -1;
            }
            int val = dbOrder1.getDisplayOrder() - dbOrder2.getDisplayOrder();
            if (val == 0) {
                return dbOrder1.getSecondaryOrder() - dbOrder2.getDisplayOrder();
            } else {
                return val;
            }
        }

        private DatabaseDisplayOrder getXRefDBDef(UniProtDatabaseDetail type) {
            return databaseType2DefsNoCase.get(type.getDisplayName().toUpperCase());
        }
    }

    private class DatabaseDisplayOrder {
        private final String dbName;
        private final int displayOrder;
        private final int secondaryOrder;

        DatabaseDisplayOrder(String dbName, int displayOrder, int secondaryOrder) {
            this.dbName = dbName;
            this.displayOrder = displayOrder;
            this.secondaryOrder = secondaryOrder;
        }

        int getDisplayOrder() {
            return this.displayOrder;
        }

        int getSecondaryOrder() {
            return this.secondaryOrder;
        }

        String getDbName() {
            return this.dbName;
        }
    }
}
