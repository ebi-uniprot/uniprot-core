package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.UniProtKBReference;

public class EntryReferenceMap implements NamedValueMap {
    static final String LIT_PUBMED_ID = "lit_pubmed_id";
    static final String LIT_DOI_ID = "lit_doi_id";
    static final List<String> FIELDS = Arrays.asList(LIT_PUBMED_ID, LIT_DOI_ID);
    private final List<UniProtKBReference> references;

    public EntryReferenceMap(List<UniProtKBReference> references) {
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

        Map<CitationDatabase, String> idMaps =
                references.stream()
                        .map(UniProtKBReference::getCitation)
                        .filter(val -> val.getCitationCrossReferences() != null)
                        .flatMap(val -> val.getCitationCrossReferences().stream())
                        .collect(
                                Collectors.groupingBy(
                                        CrossReference::getDatabase,
                                        Collectors.mapping(
                                                CrossReference::getId, Collectors.joining("; "))));

        Map<String, String> map = new HashMap<>();
        map.put(LIT_PUBMED_ID, idMaps.getOrDefault(CitationDatabase.PUBMED, ""));
        map.put(LIT_DOI_ID, idMaps.getOrDefault(CitationDatabase.DOI, ""));
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
