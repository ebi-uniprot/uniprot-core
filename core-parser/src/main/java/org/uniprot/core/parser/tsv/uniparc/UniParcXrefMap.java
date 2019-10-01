package org.uniprot.core.parser.tsv.uniparc;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabaseType;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
public class UniParcXrefMap implements NamedValueMap {
    private static final String DELIMITER = "; ";
    private static final String DELIMITER2 = ";";

    public static final List<String> FIELDS =
            Collections.unmodifiableList(
                    Arrays.asList(
                            "gene", "protein", "proteome", "accession", "first_seen", "last_seen"));

    private final List<UniParcDBCrossReference> xrefs;

    public UniParcXrefMap(List<UniParcDBCrossReference> xrefs) {
        this.xrefs = xrefs;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        String proteins = getData(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME);
        String genes = getData(UniParcDBCrossReference.PROPERTY_GENE_NAME);
        ;
        String accessions = getUniProtAccessions();
        String proteomes = getProteomes();
        Optional<LocalDate> firstSeen = getFirstSeenDate();
        Optional<LocalDate> lastSeen = getLastSeenDate();
        map.put(FIELDS.get(0), genes);
        map.put(FIELDS.get(1), proteins);
        map.put(FIELDS.get(2), proteomes);
        map.put(FIELDS.get(3), accessions);
        map.put(FIELDS.get(4), firstSeen.map(val -> val.toString()).orElse(""));
        map.put(FIELDS.get(5), lastSeen.map(val -> val.toString()).orElse(""));
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }

    private Optional<LocalDate> getFirstSeenDate() {
        return xrefs.stream()
                .map(val -> val.getCreated())
                .min(Comparator.comparing(LocalDate::toEpochDay));
    }

    private Optional<LocalDate> getLastSeenDate() {
        return xrefs.stream()
                .map(val -> val.getLastUpdated())
                .max(Comparator.comparing(LocalDate::toEpochDay));
    }

    private String getProteomes() {
        return xrefs.stream()
                .map(this::getProteome)
                .filter(val -> val != null)
                .collect(Collectors.joining(DELIMITER));
    }

    private String getProteome(UniParcDBCrossReference xref) {
        Optional<Property> opUpid = getProperty(xref, UniParcDBCrossReference.PROPERTY_PROTEOME_ID);
        if (opUpid.isPresent()) {
            Optional<Property> opPComponent =
                    getProperty(xref, UniParcDBCrossReference.PROPERTY_COMPONENT);
            String proteome = opUpid.get().getValue();
            if (opPComponent.isPresent()) {
                proteome += ":" + opPComponent.get().getValue();
            }
            return proteome;
        }
        return null;
    }

    private String getUniProtAccessions() {
        return xrefs.stream()
                .map(this::getUniProtAccession)
                .filter(val -> val != null)
                .collect(Collectors.joining(DELIMITER));
    }

    private String getUniProtAccession(UniParcDBCrossReference xref) {
        UniParcDatabaseType type = xref.getDatabaseType();
        if ((type == UniParcDatabaseType.SWISSPROT)
                || (type == UniParcDatabaseType.TREMBL)
                || (type == UniParcDatabaseType.SWISSPROT_VARSPLIC)) {
            String accession = xref.getId();
            if (!xref.isActive()) {
                accession += "." + xref.getVersion() + " (obsolete)";
            }
            return accession;
        }
        return null;
    }

    private String getData(String propertyType) {
        return xrefs.stream()
                .map(val -> getProperty(val, propertyType))
                .map(val -> val.isPresent() ? val.get().getValue() : "")
                .collect(Collectors.joining(DELIMITER2));
    }

    private Optional<Property> getProperty(UniParcDBCrossReference xref, String propertyType) {
        return xref.getProperties().stream()
                .filter(val -> val.getKey().equals(propertyType))
                .findFirst();
    }
}
