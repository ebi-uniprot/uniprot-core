package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseDisplayOrder;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author jieluo
 *
 */
public class UniProtXDbDisplayOrder implements DatabaseDisplayOrder {
    private static Map<String, DBDisplayOrder> databaseType2DefsNoCase;
    private static String DR_ORD_FILE = "META-INF/conf/dr_ord";

    private static String dr_ord_location = "https://www.ebi.ac.uk/~trembl/generator/dr_ord";
    private static boolean init = false;
    private static List<DatabaseType> orderedValues;
    private static UniProtXDbDisplayOrder instance;
    private final static Logger logger = LoggerFactory.getLogger(UniProtXDbDisplayOrder.class);

    public static UniProtXDbDisplayOrder getInstance() {
        if (instance == null) {
            synchronized (UniProtXDbDisplayOrder.class) {
                if (instance == null) {
                    synchronized (UniProtXDbDisplayOrder.class) {
                        instance = new UniProtXDbDisplayOrder();
                    }
                }
            }
        }

        return instance;
    }

    private UniProtXDbDisplayOrder() {
        initCache();
    }

    private void initCache() {
        if (init)
            return;
        databaseType2DefsNoCase = new ConcurrentHashMap<>();
        try {
            BufferedReader orderFileReader = getReaderFromUrl(dr_ord_location);
            if (orderFileReader == null) {
                orderFileReader = getReaderFromFile(DR_ORD_FILE);
            }
            String readLine = orderFileReader.readLine();
            Integer pos = 1;
            while ((readLine != null)) {
                readLine = readLine.trim();
                if (!readLine.startsWith("#")) {
                    DBDisplayOrder def = createDatabaseDef(readLine, pos);
                    databaseType2DefsNoCase.put(def.getDbName().toUpperCase(), def);
                    pos++;
                }
                readLine = orderFileReader.readLine();
            }
            orderFileReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        init = true;

    }

    private DBDisplayOrder createDatabaseDef(String line, int pos) {
        String[] data = line.split(" ");
        String db = data[0].trim();
        int secondOrder = 1;
        if (data.length > 1) {
            secondOrder = getSecondOrder(data[1]);
        }
        return new DBDisplayOrder(db, pos, secondOrder);
    }

    private int getSecondOrder(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {

        }
        return 1; // use default
    }

    private BufferedReader getReaderFromUrl(String queryUrl) {
        URLConnection urlConnection = null;
        URL url = null;
        BufferedReader reader = null;
        try {
            url = new URL(queryUrl);
        } catch (MalformedURLException ex) {

        }
        try {
            urlConnection = url.openConnection();
            urlConnection.setUseCaches(true);
            urlConnection.connect();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException ex) {
            logger.warn("could not open dr order file: " + queryUrl);
        }
        return reader;
    }

    private BufferedReader getReaderFromFile(String filename) {

        try {
            InputStream in = UniProtXDbDisplayOrder.class.getClassLoader().getResourceAsStream(filename);
            if (in == null) {
                File file = new File((filename));
                in = new FileInputStream(file);
            }
            return new BufferedReader(new InputStreamReader(in));

        } catch (Exception e) {
            throw new RuntimeException(" Database Cross Reference order file could not open.");
        }
    }

    @Override
    public List<DatabaseType> getOrderedDatabases() {
        if (orderedValues == null) {
            synchronized (DatabaseType.class) {
                if (orderedValues == null) {
                    synchronized (DatabaseType.class) {
                        orderedValues = new CopyOnWriteArrayList<>();
                        for (DatabaseType type : DatabaseType.values()) {
                            orderedValues.add(type);
                        }

                        Collections.sort(orderedValues, new XRefDBOrder());
                    }
                }
            }
        }

        return orderedValues;
    }

    private DBDisplayOrder getXRefDBDef(DatabaseType type) {
        return databaseType2DefsNoCase.get(type.getName().toUpperCase());

    }

    private class DBDisplayOrder {
        private final String dbName;
        private final int displayOrder;
        private final int secondaryOrder;

        public DBDisplayOrder(String dbName, int displayOrder, int secondaryOrder) {
            this.dbName = dbName;
            this.displayOrder = displayOrder;
            this.secondaryOrder = secondaryOrder;
            init();
        }

        private void init() {

        }

        public int getDisplayOrder() {
            return this.displayOrder;
        }

        public int getSecondaryOrder() {
            return this.secondaryOrder;
        }

        public String getDbName() {
            return this.dbName;
        }

    }

    private class XRefDBOrder implements Comparator<DatabaseType> {
        @Override
        public int compare(DatabaseType o1, DatabaseType o2) {
            DBDisplayOrder dbOrder1 = getXRefDBDef(o1);
            DBDisplayOrder dbOrder2 = getXRefDBDef(o2);

            if (dbOrder1 == null) {
                return 1;
            } else if (dbOrder2 == null) {
                return -1;
            }
            int val = dbOrder1.getDisplayOrder() - dbOrder2.getDisplayOrder();
            if (val == 0)
                return dbOrder1.getSecondaryOrder() - dbOrder2.getDisplayOrder();
            else
                return val;
        }

    }
}
