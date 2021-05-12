package org.uniprot.core.xml.uniprot.comment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.util.Utils;
import org.uniprot.cv.subcell.SubcellularLocationFileReader;

/**
 * @author jluo
 * @date: 19-Nov-2020 Create This map is used by SLCCommentConverter, use enum is not ideal.
 *     However, CommentConverter is called by entry converter, each time, when we need to convert an
 *     entry, we create entry converter. We don't want to the map each time when we create a
 *     converter. Maybe there is a better solution.
 */
public enum SubcellLocationNameMap implements Serializable {
    instance;

    private static final long serialVersionUID = -9031742801112342533L;
    private Map<String, String> subcellularLocationMap = new HashMap<>();
    private static final String DEFAULT_FILE =
            "https://ftp.uniprot.org/pub/databases/uniprot/knowledgebase/complete/docs/subcell.txt";

    SubcellLocationNameMap() {
        loadSubcellularLocationMap(DEFAULT_FILE);
    }

    public String getLocationName(String name) {
        return subcellularLocationMap.getOrDefault(name.toLowerCase(), name);
    }

    public Map<String, String> getNameMap() {
        return subcellularLocationMap;
    }

    private void loadSubcellularLocationMap(String filename) {
        if (Utils.notNullNotEmpty(filename)) {
            List<SubcellularLocationEntry> entries =
                    new SubcellularLocationFileReader().parse(filename);
            subcellularLocationMap =
                    entries.stream()
                            .collect(
                                    Collectors.toMap(
                                            this::getLowercaseContent,
                                            SubcellularLocationEntry::getContent));
        }
    }

    private String getLowercaseContent(SubcellularLocationEntry entry) {
        return entry.getContent().toLowerCase();
    }
}
