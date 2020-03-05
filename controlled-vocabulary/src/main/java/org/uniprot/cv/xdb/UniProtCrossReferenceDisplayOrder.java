package org.uniprot.cv.xdb;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;

/** @author jieluo */
public enum UniProtCrossReferenceDisplayOrder
        implements org.uniprot.core.uniprot.xdb.DatabaseDisplayOrder {
    INSTANCE;
    private Map<String, DatabaseDisplayOrder> databaseType2DefsNoCase;
    private static final String DR_ORD_FILE = "META-INF/dr_ord";
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
        try {
            BufferedReader orderFileReader = getReaderFromUrl();
            if (orderFileReader == null) {
                orderFileReader = getReaderFromFile();
            }
            String readLine = orderFileReader.readLine();
            int pos = 1;
            while ((readLine != null)) {
                readLine = readLine.trim();
                if (!readLine.startsWith("#")) {
                    DatabaseDisplayOrder def = createDatabaseDef(readLine, pos);
                    databaseType2DefsNoCase.put(def.getDbName().toUpperCase(), def);
                    pos++;
                }
                readLine = orderFileReader.readLine();
            }
            orderFileReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
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

    private BufferedReader getReaderFromUrl() {
        URLConnection urlConnection = null;
        URL url = null;
        BufferedReader reader = null;
        try {
            url = new URL(UniProtCrossReferenceDisplayOrder.DR_ORD_LOCATION);
        } catch (MalformedURLException ex) {
            return null;
        }
        try {
            urlConnection = url.openConnection();
            urlConnection.setUseCaches(true);
            urlConnection.connect();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException ex) {
            return null;
        }
        return reader;
    }

    private BufferedReader getReaderFromFile() {
        InputStream inputStream =
                UniProtCrossReferenceDisplayOrder.class
                        .getClassLoader()
                        .getResourceAsStream(UniProtCrossReferenceDisplayOrder.DR_ORD_FILE);
        if (inputStream == null) {
            File file = new File(UniProtCrossReferenceDisplayOrder.DR_ORD_FILE);
            try (InputStream fileInputStream = new FileInputStream(file)) {
                inputStream = fileInputStream;
            } catch (IOException e) {
                throw new RuntimeException(" Database Cross Reference order file could not open.");
            }
        }

        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private static class SafeLazyInitializer {
        static List<UniProtDatabaseDetail> uniProtDatabaseDetails =
                initValues(UniProtCrossReferenceDisplayOrder.INSTANCE.databaseType2DefsNoCase);

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

            if (dbOrder1 == null) {
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