package org.uniprot.core.xml.uniprot.comment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.util.Utils;
import org.uniprot.cv.subcell.SubcellularLocationCache;

import com.google.common.base.Strings;

/**
 * @author jluo
 * @date: 4 Jun 2021
 */
public class SubcellLocationNameMapImpl implements SubcellLocationNameMap {
    /** */
    private static final long serialVersionUID = -8992760196010310897L;

    private Map<String, String> subcellularLocationMap = new HashMap<>();
    private Map<String, String> subcellularLocationNameMap = new HashMap<>();
    private static final String DEFAULT_FILE =
            "https://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/docs/subcell.txt";

    public SubcellLocationNameMapImpl() {
        this(DEFAULT_FILE);
    }

    public SubcellLocationNameMapImpl(String subcellFile) {
        if (Strings.isNullOrEmpty(subcellFile)) {
            subcellFile = DEFAULT_FILE;
        }
        loadSubcellularLocationMap(subcellFile);
    }

    public SubcellLocationNameMapImpl(Map<String, String> subcellularLocationMap) {
        this.subcellularLocationMap = Collections.unmodifiableMap(subcellularLocationMap);
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
                    SubcellularLocationCache.INSTANCE.get(filename);
            subcellularLocationMap =
                    entries.stream()
                            .collect(
                                    Collectors.toUnmodifiableMap(
                                            this::getLowercaseContent,
                                            SubcellularLocationEntry::getContent));
            subcellularLocationNameMap =
                    entries.stream()
                            .collect(
                                    Collectors.toUnmodifiableMap(
                                            this::getLowercaseName,
                                            SubcellularLocationEntry::getName));
        }
    }

    private String getLowercaseContent(SubcellularLocationEntry entry) {
        return entry.getContent().toLowerCase();
    }
    private String getLowercaseName(SubcellularLocationEntry entry) {
        return entry.getName().toLowerCase();
    }

	@Override
	public String getName(String name) {
		return subcellularLocationNameMap.get(name.toLowerCase());
	}
}
