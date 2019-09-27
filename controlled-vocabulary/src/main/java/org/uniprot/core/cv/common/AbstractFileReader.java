package org.uniprot.core.cv.common;

import org.slf4j.Logger;
import org.uniprot.core.cv.FileReader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractFileReader<T> implements FileReader<T> {
    private static final Logger LOGGER = getLogger(AbstractFileReader.class);
    private static final String FTP_PREFIX = "ftp://";
    protected static final List<String> COPYRIGHT_LINES =
            Arrays.asList(
                    "-----------------------------------------------------------------------",
                    "Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms",
                    "Distributed under the Creative Commons Attribution (CC BY 4.0) License",
                    "-----------------------------------------------------------------------"
            );

    public abstract List<T> parseLines(List<String> lines);

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
        } catch (IOException e) {
            LOGGER.error("Error while fetching the data from ftp url {}", ftpUrl);
        }
        return lines;
    }

    private List<String> readLines(String filename) {
        if (filename.startsWith(FTP_PREFIX)) {
            return fetchFromFTP(filename);
        } else {
        	
            InputStream inputStream = AbstractFileReader.class.getClassLoader().getResourceAsStream(filename);
            List<String> lines = new ArrayList<>();
            try {
                if (inputStream == null) {
                    inputStream = new FileInputStream(new File(filename));
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Problem loading file.", e);
            }

            return lines;
        }
    }
}
