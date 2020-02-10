package org.uniprot.cv.xdb.validator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DBXRefReader implements AutoCloseable {
    private static final String DATA_REGION_SEP =
            "___________________________________________________________________________";
    private static final String COPYRIGHT_SEP =
            "-----------------------------------------------------------------------";
    private static final String LINE_SEP = "\n";
    // Fields of the dbxref file - begin
    private static final String AC_STR = "AC";
    private static final String ABBREV_STR = "Abbrev";
    private static final String NAME_STR = "Name";
    private static final String REF_STR = "Ref";
    private static final String LINK_TP_STR = "LinkTp";
    private static final String SERVER_STR = "Server";
    private static final String DB_URL_STR = "Db_URL";
    private static final String CAT_STR = "Cat";
    // Fields of the dbxref file - end

    private static final String KEY_VAL_SEPARATOR = ": ";
    private static final Pattern NEWLINE_PATTERN = Pattern.compile("^\\s*$", Pattern.MULTILINE);

    private Scanner reader;
    private boolean dataRegionStarted;

    public DBXRefReader(String ftpUrl) throws IOException {
        URL url = new URL(ftpUrl);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        this.reader = new Scanner(inputStream, StandardCharsets.UTF_8.name());
        this.dataRegionStarted = false;
    }

    public DBXRef read() {
        // skip the un-needed lines
        while (this.reader.hasNext() && !this.dataRegionStarted) {
            String lines = reader.next();
            if (DATA_REGION_SEP.equals(lines)) {
                this.dataRegionStarted = true;
                this.reader.useDelimiter(NEWLINE_PATTERN);
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
        String acc = null, abbr = null, name = null, ref = null;
        String lType = null, server = null, url = null, cat = null;

        for (String line : lines) {
            String[] keyVal = line.split(KEY_VAL_SEPARATOR);
            switch (keyVal[0].trim()) {
                case AC_STR:
                    acc = keyVal[1].trim();
                    break;
                case ABBREV_STR:
                    abbr = keyVal[1].trim();
                    break;
                case NAME_STR:
                    name = keyVal[1].trim();
                    break;
                case REF_STR:
                    ref = keyVal[1].trim();
                    break;
                case LINK_TP_STR:
                    lType = keyVal[1].trim();
                    break;
                case SERVER_STR:
                    server = keyVal[1].trim();
                    break;
                case DB_URL_STR:
                    url = keyVal[1].trim();
                    break;
                case CAT_STR:
                    cat = keyVal[1].trim();
                    break;
                default: // do nothing
            }
        }

        DBXRef.DBXRefBuilder builder = new DBXRef.DBXRefBuilder();
        builder.accession(acc).abbr(abbr).name(name);
        builder.ref(ref).linkType(lType).server(server);
        builder.dbUrl(url).category(cat);

        return builder.build();
    }

    @Override
    public void close() {
        reader.close();
    }
}
