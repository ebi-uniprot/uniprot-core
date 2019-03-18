package uk.ac.ebi.uniprot.domain.uniprot.xdb.validator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DBXRefReader implements AutoCloseable {
    private static final String DATA_REGION_SEP = "___________________________________________________________________________";
    private static final String COPYRIGHT_SEP = "-----------------------------------------------------------------------";
    private static final String LINE_SEP = "\n";
    private static final String ABBREV_STR = "Abbrev";
    private static final String CAT_STR = "Cat";
    private static final String DB_URL_STR = "Db_URL";
    private static final String KEY_VAL_SEPARATOR = ": ";

    private Scanner reader;
    private boolean dataRegionStarted;

    public DBXRefReader(String ftpUrl) throws IOException {
        URL url = new URL(ftpUrl);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        this.reader = new Scanner(inputStream, StandardCharsets.UTF_8.name());
        this.dataRegionStarted = false;
    }

    public DBXRef read(){
        // skip the un-needed lines
        while (this.reader.hasNext() && !this.dataRegionStarted) {
            String lines = reader.next();
            if (DATA_REGION_SEP.equals(lines)) {
                this.dataRegionStarted = true;
                this.reader.useDelimiter(Pattern.compile("^\\s*$", Pattern.MULTILINE));
            }
        }

        DBXRef dbxRef = null;
        String lines = this.reader.next();
        if (!lines.contains(COPYRIGHT_SEP)) {
            dbxRef = convertToDBXRef(lines);
        }
        return dbxRef;
    }

    private DBXRef convertToDBXRef(String linesStr) {
        String[] lines = linesStr.split(LINE_SEP);
        String abbr = null;
        String cat = null;
        String url = null;
        for(String line : lines){
            String[] keyVal = line.split(KEY_VAL_SEPARATOR);
            switch (keyVal[0].trim()){
                case ABBREV_STR :
                    abbr = keyVal[1].trim();
                    break;
                case CAT_STR :
                    cat = keyVal[1].trim();
                    break;
                case DB_URL_STR:
                    url = keyVal[1].trim();
                    break;
                default://do nothing
            }
        }

        DBXRef dbxref = new DBXRef(abbr, url, cat);
        return dbxref;
    }

    @Override
    public void close() {
        reader.close();

    }
}
