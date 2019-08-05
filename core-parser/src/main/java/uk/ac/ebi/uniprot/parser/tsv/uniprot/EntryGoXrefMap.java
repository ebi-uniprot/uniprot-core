package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

public class EntryGoXrefMap implements NamedValueMap {
    private final List<UniProtDBCrossReference> dbReferences;
    public static final List<String> FIELDS = Arrays.asList("go", "go_c", "go_f", "go_p", "go_id");

    public EntryGoXrefMap(List<UniProtDBCrossReference> dbReferences) {
        if (dbReferences == null) {
            this.dbReferences = Collections.emptyList();
        } else {
            this.dbReferences = Collections.unmodifiableList(dbReferences);
        }

    }

    @Override
    public Map<String, String> attributeValues() {
        if (dbReferences.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> map = new HashMap<>();
        addTypedGoXRefToMap(map, FIELDS.get(0), "", dbReferences);
        addTypedGoXRefToMap(map, FIELDS.get(1), "C", dbReferences);
        addTypedGoXRefToMap(map, FIELDS.get(2), "F", dbReferences);
        addTypedGoXRefToMap(map, FIELDS.get(3), "P", dbReferences);

        map.put(FIELDS.get(4), dbReferences.stream().map(UniProtDBCrossReference::getId).sorted()
                .collect(Collectors.joining("; ")));

        return map;
    }

    private void addTypedGoXRefToMap(Map<String, String> map, String field, String type, List<UniProtDBCrossReference> xrefs) {
        String result = xrefs.stream().map(val -> getGoTypedString(val, type))
                .filter(val -> val != null && !val.isEmpty()).collect(Collectors.joining("; "));
        if (result != null && !result.isEmpty()) {
            map.put(field, result);
        }
    }

    private String getGoTypedString(UniProtDBCrossReference xref, String type) {
        Optional<Property> result;
        if (type == null || type.isEmpty()) {
            result = xref.getProperties().stream().filter(val -> val.getKey().equalsIgnoreCase("GoTerm")).findFirst();
        } else
            result = xref.getProperties().stream()
                    .filter(val -> (val.getKey().equalsIgnoreCase("GoTerm") && val.getValue().startsWith(type))).findFirst();
        return result
                .map(property -> property.getValue().substring(2) + " [" + xref.getId() + "]")
                .orElse(null);
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);

    }
}
