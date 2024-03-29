package org.uniprot.core.parser.tsv.uniprot;

import static org.uniprot.core.util.Utils.*;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

public class EntryCrossReferenceMap implements NamedValueMap {
    private static final String CROSS_REF = "xref_";
    private static final String FULL_SUFFIX = "_full";
    private final List<UniProtKBCrossReference> dbReferences;
    private static final Map<String, String> d3MethodMap = new HashMap<>();

    static {
        d3MethodMap.put("X-ray", "X-ray crystallography");
        d3MethodMap.put("NMR", "NMR spectroscopy");
        d3MethodMap.put("EM", "Electron microscopy");
        d3MethodMap.put("Model", "Model");
        d3MethodMap.put("Neutron", "Neutron diffraction");
        d3MethodMap.put("Fiber", "Fiber diffraction");
        d3MethodMap.put("IR", "Infrared spectroscopy");
    }

    public static boolean contains(List<String> fields) {
        return fields.stream()
                        .anyMatch(
                                val ->
                                        val.startsWith(CROSS_REF)
                                                || val.equalsIgnoreCase("structure_3d"))
                || EntryGoCrossReferenceMap.contains(fields);
    }

    public EntryCrossReferenceMap(List<UniProtKBCrossReference> dbReferences) {
        this.dbReferences = unmodifiableList(dbReferences);
    }

    @Override
    public Map<String, String> attributeValues() {
        if (dbReferences.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> map = new HashMap<>();
        Map<String, List<UniProtKBCrossReference>> xrefMap =
                dbReferences.stream()
                        .collect(Collectors.groupingBy(xref -> xref.getDatabase().getName()));
        xrefMap.forEach((key, value) -> addToMap(map, key, value));
        return map;
    }

    private void addToMap(
            Map<String, String> map, String type, List<UniProtKBCrossReference> xrefs) {
        if (type.equalsIgnoreCase("GO")) {
            EntryGoCrossReferenceMap dlGoXref = new EntryGoCrossReferenceMap(xrefs);
            Map<String, String> goMap = dlGoXref.attributeValues();
            map.putAll(goMap);
        } else if (type.equalsIgnoreCase("PROTEOMES")) {
            map.put(
                    CROSS_REF + type.toLowerCase(),
                    xrefs.stream()
                            .map(this::proteomeXrefToString)
                            .collect(Collectors.joining("; ")));
        } else {
            map.put(
                    CROSS_REF + type.toLowerCase(),
                    xrefs.stream()
                            .map(this::dbXrefToString)
                            .collect(Collectors.joining(";", "", ";")));

            if (isMultiValueXref(xrefs)) {
                map.put(
                        CROSS_REF + type.toLowerCase() + FULL_SUFFIX,
                        xrefs.stream()
                                .map(this::dbXrefFullToString)
                                .collect(Collectors.joining(";", "", ";")));
            }

            if (type.equalsIgnoreCase("PDB")) {
                map.put("structure_3d", pdbXrefTo3DString(xrefs));
            }
        }
    }

    private static boolean isMultiValueXref(List<UniProtKBCrossReference> xrefs) {
        boolean result = false;
        UniProtKBCrossReference xref = xrefs.get(0);
        if (notNullNotEmpty(xref.getProperties())) {
            if (xref.getProperties().size() > 1) {
                result = true;
            } else { // else only one property
                Property firstProperty = xref.getProperties().get(0);
                result = notDefaultProperty(firstProperty);
            }
        }
        return result;
    }

    private static boolean notDefaultProperty(Property property) {
        return !property.getKey()
                .equalsIgnoreCase(UniProtDatabaseDetail.DEFAULT_ATTRIBUTE.getName());
    }

    private String pdbXrefTo3DString(List<UniProtKBCrossReference> xrefs) {
        Map<String, Long> result =
                xrefs.stream()
                        .flatMap(val -> val.getProperties().stream())
                        .filter(val -> val.getKey().equalsIgnoreCase("Method"))
                        .map(Property::getValue)
                        .map(d3MethodMap::get)
                        .filter(Objects::nonNull)
                        .collect(
                                Collectors.groupingBy(
                                        val -> val, TreeMap::new, Collectors.counting()));

        return result.entrySet().stream()
                .map(val -> (val.getKey() + " (" + val.getValue().toString() + ")"))
                .collect(Collectors.joining("; "));
    }

    private String dbXrefToString(UniProtKBCrossReference xref) {
        StringBuilder sb = new StringBuilder();
        sb.append(xref.getId());

        if (xref.getIsoformId() != null && !xref.getIsoformId().isEmpty()) {
            sb.append(" [").append(xref.getIsoformId()).append("]");
        }

        return sb.toString();
    }

    private String dbXrefFullToString(UniProtKBCrossReference xref) {
        StringBuilder sb = new StringBuilder();

        sb.append("\"").append(xref.getId());
        if (xref.hasProperties()) {
            String values = dbXrefPropertiesToString(xref);
            if (!values.isEmpty()) {
                sb.append("; ").append(values).append(".");
            }
        }
        if (xref.getIsoformId() != null && !xref.getIsoformId().isEmpty()) {
            sb.append(" [").append(xref.getIsoformId()).append("]");
        }
        sb.append("\"");
        return sb.toString();
    }

    private String dbXrefPropertiesToString(UniProtKBCrossReference xref) {
        List<Property> properties = xref.getProperties();
        return properties.stream()
                .map(Property::getValue)
                .map(String::strip)
                .collect(Collectors.joining("; "));
    }

    private String proteomeXrefToString(UniProtKBCrossReference xref) {
        StringBuilder sb = new StringBuilder();
        sb.append(xref.getId()).append(": ").append(xref.getProperties().get(0).getValue());

        return sb.toString();
    }
}
