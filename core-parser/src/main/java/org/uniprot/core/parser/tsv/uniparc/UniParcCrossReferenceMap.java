package org.uniprot.core.parser.tsv.uniparc;

import org.uniprot.core.Property;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
public class UniParcCrossReferenceMap implements NamedValueMap {
    private static final String DELIMITER = "; ";
    private static final String DELIMITER2 = ";";

    public static final List<String> FIELDS;

    static {
        List<String> fields = new ArrayList<>();
        fields.addAll(
                Arrays.asList(
                        "gene", "protein", "proteome", "accession", "first_seen", "last_seen"));
        Arrays.stream(UniParcDatabase.values()).map(UniParcDatabase::getName).forEach(fields::add);
        FIELDS = Collections.unmodifiableList(fields);
    }

    private final List<UniParcCrossReference> uniParcCrossReferences;

    public UniParcCrossReferenceMap(List<UniParcCrossReference> uniParcCrossReferences) {
        this.uniParcCrossReferences = uniParcCrossReferences;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        String proteins = getData(UniParcCrossReference.PROPERTY_PROTEIN_NAME);
        String genes = getData(UniParcCrossReference.PROPERTY_GENE_NAME);
        ;
        String accessions = getUniProtAccessions();
        String proteomes = getProteomes();
        Optional<LocalDate> firstSeen = getFirstSeenDate();
        Optional<LocalDate> lastSeen = getLastSeenDate();
        map.put(FIELDS.get(0), genes);
        map.put(FIELDS.get(1), proteins);
        map.put(FIELDS.get(2), proteomes);
        map.put(FIELDS.get(3), accessions);
        map.put(FIELDS.get(4), firstSeen.map(LocalDate::toString).orElse(""));
        map.put(FIELDS.get(5), lastSeen.map(LocalDate::toString).orElse(""));
        map.putAll(getDatabasesMap());

        return map;
    }

    private Map<String, String> getDatabasesMap() {
        Map<String, String> result = new HashMap<>();
        uniParcCrossReferences.forEach(
                xref -> {
                    result.put(xref.getDatabase().getName(), xref.getId());
                });
        return result;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }

    private Optional<LocalDate> getFirstSeenDate() {
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::getCreated)
                .min(Comparator.comparing(LocalDate::toEpochDay));
    }

    private Optional<LocalDate> getLastSeenDate() {
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::getLastUpdated)
                .max(Comparator.comparing(LocalDate::toEpochDay));
    }

    private String getProteomes() {
        return uniParcCrossReferences.stream()
                .map(this::getProteome)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(DELIMITER));
    }

    private String getProteome(UniParcCrossReference xref) {
        Optional<Property> opUpid = getProperty(xref, UniParcCrossReference.PROPERTY_PROTEOME_ID);
        if (opUpid.isPresent()) {
            Optional<Property> opPComponent =
                    getProperty(xref, UniParcCrossReference.PROPERTY_COMPONENT);
            String proteome = opUpid.get().getValue();
            if (opPComponent.isPresent()) {
                proteome += ":" + opPComponent.get().getValue();
            }
            return proteome;
        }
        return null;
    }

    private String getUniProtAccessions() {
        return uniParcCrossReferences.stream()
                .map(this::getUniProtAccession)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(DELIMITER));
    }

    private String getUniProtAccession(UniParcCrossReference xref) {
        UniParcDatabase type = xref.getDatabase();
        if ((type == UniParcDatabase.SWISSPROT)
                || (type == UniParcDatabase.TREMBL)
                || (type == UniParcDatabase.SWISSPROT_VARSPLIC)) {
            String accession = xref.getId();
            if (!xref.isActive()) {
                accession += "." + xref.getVersion() + " (obsolete)";
            }
            return accession;
        }
        return null;
    }

    private String getData(String propertyType) {
        return uniParcCrossReferences.stream()
                .map(val -> getProperty(val, propertyType))
                .map(val -> val.map(Property::getValue).orElse(""))
                .collect(Collectors.joining(DELIMITER2));
    }

    private Optional<Property> getProperty(UniParcCrossReference xref, String propertyType) {
        return xref.getProperties().stream()
                .filter(val -> val.getKey().equals(propertyType))
                .findFirst();
    }
}
