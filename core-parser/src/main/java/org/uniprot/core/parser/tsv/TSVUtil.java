package org.uniprot.core.parser.tsv;

import java.util.List;

import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2020-03-22
 */
public class TSVUtil {

    public static String getOrDefaultEmpty(String input) {
        if (Utils.notNullNotEmpty(input)) {
            return input;
        } else {
            return "";
        }
    }

    public static String getOrDefaultEmpty(List<String> input) {
        if (Utils.notNullNotEmpty(input)) {
            return String.join(", ", input);
        } else {
            return "";
        }
    }
}
