package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import java.util.List;
import java.util.Map;

import org.uniprot.core.util.Utils;

public interface NamedValueMap {
	Map<String, String> attributeValues();

    default String getOrDefaultEmpty(String input) {
        if (Utils.notEmpty(input)) {
            return input;
        } else {
            return "";
        }
    }

    default String getOrDefaultEmpty(List<String> input) {
        if (Utils.notEmpty(input)) {
            return String.join(", ", input);
        } else {
            return "";
        }
    }
}
