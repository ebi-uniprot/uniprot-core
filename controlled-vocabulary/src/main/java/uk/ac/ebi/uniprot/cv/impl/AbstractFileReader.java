package uk.ac.ebi.uniprot.cv.impl;

import uk.ac.ebi.uniprot.cv.FileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractFileReader<T> implements FileReader<T> {
    private static final String FTP_PREFIX = "ftp://";
    static final List<String> COPYRIGHT_LINES =
            Arrays.asList(
                    "-----------------------------------------------------------------------",
                    "Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms",
                    "Distributed under the Creative Commons Attribution (CC BY 4.0) License",
                    "-----------------------------------------------------------------------"
            );

    abstract List<T> parseLines(List<String> lines);

    @Override
    public List<T> parse(String filename) {
        List<String> lines = readLines(filename);
        return parseLines(lines);
    }

    private List<String> fetchFromFTP(String ftpUrl) {
        List<String> lines = new ArrayList<>();
        try {
            final URL url = new URL(ftpUrl);
            final URLConnection con = url.openConnection();
            con.setDoInput(true);
            final java.io.InputStream in = con.getInputStream();

            if (in != null) {
                BufferedReader inText = new BufferedReader(new InputStreamReader(in));
                String l;
                while ((l = inText.readLine()) != null) {
                    lines.add(l);
                }
                inText.close();
                in.close();
            }
        } catch (Exception e) {

        }
        return lines;
    }

    List<String> readLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {

        }
        if (filename.startsWith(FTP_PREFIX)) {
            return fetchFromFTP(filename);
        } else {
            return Collections.emptyList();
        }
    }
}
