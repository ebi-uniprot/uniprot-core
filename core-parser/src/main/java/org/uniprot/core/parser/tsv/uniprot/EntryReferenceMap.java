package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.uniprot.UniProtReference;

public class EntryReferenceMap implements NamedValueMap {
    public static final List<String> FIELDS = Arrays.asList("pm_id");
    private final List<UniProtReference> references;

    public EntryReferenceMap(List<UniProtReference> references) {
        if (references == null) {
            this.references = Collections.emptyList();
        } else {
            this.references = Collections.unmodifiableList(references);
        }
    }

    @Override
    public Map<String, String> attributeValues() {
        if (references.isEmpty()) {
            return Collections.emptyMap();
        }

        String result = references.stream().map(UniProtReference::getCitation).filter(val -> val.getCitationXrefs() != null)
                .flatMap(val -> val.getCitationXrefs().stream())
                .filter(val -> val.getDatabaseType().equals(CitationXrefType.PUBMED)).map(DBCrossReference::getId)
                .collect(Collectors.joining("; "));
        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), result);
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);

    }
}