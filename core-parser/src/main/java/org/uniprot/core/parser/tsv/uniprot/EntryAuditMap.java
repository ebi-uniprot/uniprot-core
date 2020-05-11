package org.uniprot.core.parser.tsv.uniprot;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.EntryAudit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntryAuditMap implements NamedValueMap {

    protected static final List<String> FIELDS =
            Arrays.asList(
                    "sequence_version",
                    "date_sequence_modified",
                    "version",
                    "date_created",
                    "date_modified");

    private final EntryAudit entryAudit;

    public EntryAuditMap(EntryAudit entryAudit) {
        this.entryAudit = entryAudit;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), "" + entryAudit.getSequenceVersion());
        map.put(
                FIELDS.get(1),
                entryAudit.getLastSequenceUpdateDate() == null
                        ? ""
                        : entryAudit.getLastSequenceUpdateDate().toString());
        map.put(FIELDS.get(2), "" + entryAudit.getEntryVersion());
        map.put(
                FIELDS.get(3),
                entryAudit.getFirstPublicDate() == null
                        ? ""
                        : entryAudit.getFirstPublicDate().toString());
        map.put(
                FIELDS.get(4),
                entryAudit.getLastAnnotationUpdateDate() == null
                        ? ""
                        : entryAudit.getLastAnnotationUpdateDate().toString());
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
