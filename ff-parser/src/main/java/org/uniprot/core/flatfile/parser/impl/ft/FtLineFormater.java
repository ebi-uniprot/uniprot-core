package org.uniprot.core.flatfile.parser.impl.ft;

import org.uniprot.core.flatfile.parser.LineFormater;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

public class FtLineFormater implements LineFormater {
    private static final String OTHER_LINE_PREFIX = "FT                   ";
    private static final String FIRST_LINE_PREFIX = "FT   ";
    private static final String LINE_SEPARATOR = "\n";

    @Override
    public String format(String lines) {
        String[] tokens = lines.split(LINE_SEPARATOR);
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            if (isFirstLineFF(token)) {

                if (!token.startsWith(FIRST_LINE_PREFIX)) {
                    sb.append(FIRST_LINE_PREFIX + token);
                } else {
                    sb.append(token);
                }
            } else {

                if (!token.startsWith(OTHER_LINE_PREFIX)) {
                    sb.append(OTHER_LINE_PREFIX + token);
                } else {
                    sb.append(token);
                }
            }
            sb.append(LINE_SEPARATOR);
        }
        return sb.toString();
    }

    private boolean isFirstLineFF(String token) {
        String val = token;
        if (val.startsWith(FIRST_LINE_PREFIX)) {
            val = val.substring(FIRST_LINE_PREFIX.length());
        }
        for (UniprotKBFeatureType type : UniprotKBFeatureType.values()) {
            if (token.startsWith(type.getName())) {
                return true;
            }
        }
        return false;
    }
}
