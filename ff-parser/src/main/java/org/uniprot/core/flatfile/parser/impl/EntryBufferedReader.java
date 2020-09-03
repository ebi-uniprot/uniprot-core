package org.uniprot.core.flatfile.parser.impl;

import java.io.*;
import java.util.zip.GZIPInputStream;

import org.uniprot.core.flatfile.parser.EntryReader;

public class EntryBufferedReader implements EntryReader {
    private final BufferedReader reader;
    /** Where to split entries */
    public static final String ENTRY_END_TOKEN = "//";

    private boolean available = true;

    @SuppressWarnings("squid:S2095")
    public EntryBufferedReader(String fileName) throws IOException {
        InputStream is = new FileInputStream(fileName);

        if (fileName.endsWith(".gz")) {
            is = new GZIPInputStream(is);
        }
        reader = new BufferedReader(new InputStreamReader(is));
    }

    public String next() throws IOException {
        StringBuilder newFileEntry = new StringBuilder();
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
