package org.uniprot.core.parser.tsv.uniparc;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.util.Utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
                        "gene", "protein", "proteome", "accession", "first_seen", "last_seen",
                        "database", "active", "ncbiGi", "timeline", "version", "version_uniparc"));
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
        Optional<LocalDate> firstSeen = getFirstSeenDate();
        Optional<LocalDate> lastSeen = getLastSeenDate();
        map.put(FIELDS.get(0), getGeneNames());
        map.put(FIELDS.get(1), getProteinNames());
        map.put(FIELDS.get(2), getProteomes());
        map.put(FIELDS.get(3), getUniProtKBAccessions());
        map.put(FIELDS.get(4), firstSeen.map(LocalDate::toString).orElse(""));
        map.put(FIELDS.get(5), lastSeen.map(LocalDate::toString).orElse(""));
        map.put(FIELDS.get(6), getDatabases());
        map.put(FIELDS.get(7), getActives());
        map.put(FIELDS.get(8), getNcbiGis());
        map.put(FIELDS.get(9), getTimelines());
        map.put(FIELDS.get(10), getVersions());
        map.put(FIELDS.get(11), getVersionIs());
        map.putAll(getDatabasesMap());

        return map;
    }

    private Map<String, String> getDatabasesMap() {
        Map<String, String> result = new HashMap<>();
        uniParcCrossReferences.forEach(
                xref -> result.put(xref.getDatabase().getName(), xref.getId()));
        return result;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }

    private Optional<LocalDate> getFirstSeenDate() {
        return uniParcCrossReferences.stream()
                .filter(xref -> Utils.notNull(xref.getCreated()))
                .map(UniParcCrossReference::getCreated)
                .min(Comparator.comparing(LocalDate::toEpochDay));
    }

    private Optional<LocalDate> getLastSeenDate() {
        return uniParcCrossReferences.stream()
                .filter(xref -> Utils.notNull(xref.getLastUpdated()))
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
        String proteome = xref.getProteomeId();
        if (Utils.notNullNotEmpty(proteome)) {
            if (Utils.notNullNotEmpty(xref.getComponent())) {
                proteome += ":" + xref.getComponent();
            }
            return proteome;
        }
        return null;
    }

    private String getUniProtKBAccessions() {
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

    private String getGeneNames() {
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::getGeneName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(DELIMITER2));
    }

    private String getProteinNames() {
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::getProteinName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(DELIMITER2));
    }

    private String getDatabases() {
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::getDatabase)
                .filter(Objects::nonNull)
                .map(UniParcDatabase::getName)
                .collect(Collectors.joining(DELIMITER2));
    }

    private String getActives(){
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::isActive)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER2));
    }

    private String getNcbiGis(){
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::getNcbiGi)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(DELIMITER2));
    }

    private String getTimelines(){
        return uniParcCrossReferences.stream()
                .map(xref -> getDiff(xref.getCreated(), xref.getLastUpdated()))
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER2));
    }

    private String getVersions(){
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::getVersion)
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER2));
    }

    private String getVersionIs(){
        return uniParcCrossReferences.stream()
                .map(UniParcCrossReference::getVersionI)
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER2));
    }

    private Long getDiff(LocalDate start, LocalDate end){
        if(Objects.isNull(start) || Objects.isNull(end)){
            return null;
        }
        return ChronoUnit.DAYS.between(end, start);
    }
}
