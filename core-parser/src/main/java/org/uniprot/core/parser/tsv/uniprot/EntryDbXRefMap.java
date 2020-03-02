package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

public class EntryDbXRefMap implements NamedValueMap {
    private static final String DR = "dr_";
    private final List<UniProtCrossReference> dbReferences;
    private static final Map<String, String> D3MethodMAP = new HashMap<>();

    static {
        D3MethodMAP.put("X-ray", "X-ray crystallography");
        D3MethodMAP.put("NMR", "NMR spectroscopy");
        D3MethodMAP.put("EM", "Electron microscopy");
        D3MethodMAP.put("Model", "Model");
        D3MethodMAP.put("Neutron", "Neutron diffraction");
        D3MethodMAP.put("Fiber", "Fiber diffraction");
        D3MethodMAP.put("IR", "Infrared spectroscopy");
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(val -> val.startsWith(DR))
                || EntryGoXrefMap.contains(fields);
    }

    public EntryDbXRefMap(List<UniProtCrossReference> dbReferences) {
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
        Map<String, List<UniProtCrossReference>> xrefMap =
                dbReferences.stream()
                        .collect(Collectors.groupingBy(xref -> xref.getDatabase().getName()));
        xrefMap.forEach((key, value) -> addToMap(map, key, value));
        return map;
    }

    private void addToMap(Map<String, String> map, String type, List<UniProtCrossReference> xrefs) {
        if (type.equalsIgnoreCase("GO")) {
            EntryGoXrefMap dlGoXref = new EntryGoXrefMap(xrefs);
            Map<String, String> goMap = dlGoXref.attributeValues();
            goMap.forEach(map::put);
        } else if (type.equalsIgnoreCase("PROTEOMES")) {
            map.put(
                    DR + type.toLowerCase(),
                    xrefs.stream()
                            .map(EntryDbXRefMap::proteomeXrefToString)
                            .collect(Collectors.joining("; ")));
        } else {
            map.put(
                    DR + type.toLowerCase(),
                    xrefs.stream()
                            .map(EntryDbXRefMap::dbXrefToString)
                            .collect(Collectors.joining(";", "", ";")));
            if (type.equalsIgnoreCase("PDB")) {
                map.put("3d", pdbXrefTo3DString(xrefs));
            }
        }
    }

    private String pdbXrefTo3DString(List<UniProtCrossReference> xrefs) {
        Map<String, Long> result =
                xrefs.stream()
                        .flatMap(val -> val.getProperties().stream())
                        .filter(val -> val.getKey().equalsIgnoreCase("Method"))
                        .map(Property::getValue)
                        .map(D3MethodMAP::get)
                        .filter(Objects::nonNull)
                        .collect(
                                Collectors.groupingBy(
                                        val -> val, TreeMap::new, Collectors.counting()));

        return result.entrySet().stream()
                .map(val -> (val.getKey() + " (" + val.getValue().toString() + ")"))
                .collect(Collectors.joining("; "));
    }

    public static String dbXrefToString(UniProtCrossReference xref) {
        StringBuilder sb = new StringBuilder();
        sb.append(xref.getId());
        if (xref.getIsoformId() != null && !xref.getIsoformId().isEmpty()) {
            sb.append(" [").append(xref.getIsoformId()).append("]");
        }
        return sb.toString();
    }

    public static String proteomeXrefToString(UniProtCrossReference xref) {
        StringBuilder sb = new StringBuilder();
        sb.append(xref.getId()).append(": ").append(xref.getProperties().get(0).getValue());

        return sb.toString();
    }
}
