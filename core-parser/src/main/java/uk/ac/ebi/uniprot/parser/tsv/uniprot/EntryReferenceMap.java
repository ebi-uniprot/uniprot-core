package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;


import java.util.*;
import java.util.stream.Collectors;

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
