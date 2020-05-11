package org.uniprot.core.flatfile.parser.impl;

import org.uniprot.core.flatfile.parser.EntryReader;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class EntryBufferedReader implements EntryReader {
    private BufferedReader reader;
    /** Where to split entries */
    public static final String ENTRY_END_TOKEN = "//";

    private boolean available = true;

    public EntryBufferedReader(String fileName) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(fileName);

        if (fileName.endsWith(".gz")) {
            is = new GZIPInputStream(is);
        }
        reader = new BufferedReader(new InputStreamReader(is));
    }

    public String next() throws IOException {
        StringBuffer newFileEntry = new StringBuffer();
        String newLine = null;
        if (available) {
            do {
                newLine = this.reader.readLine();

                newFileEntry.append(newLine);
                newFileEntry.append('\n');
            } while (newLine != null && !newLine.equals(ENTRY_END_TOKEN));
            if (newLine == null) {
                available = false;
            }
        }
        if (newFileEntry.length() <= 0 || !available) {
            return null;
        }
        return newFileEntry.toString();
        //
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
