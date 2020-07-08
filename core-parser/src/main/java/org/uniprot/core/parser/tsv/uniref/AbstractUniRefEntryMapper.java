package org.uniprot.core.parser.tsv.uniref;

import org.uniprot.core.parser.tsv.EntityValueMapper;

import java.util.*;

/**
 * Created 07/07/2020
 *
 * @author Edd
 */
public abstract class AbstractUniRefEntryMapper<T> implements EntityValueMapper<T> {
    static final String DELIMITER = "; ";
    static final List<String> UNIREF_FIELDS =
            Collections.unmodifiableList(
                    Arrays.asList(
                            "id",
                            "name",
                            "common_taxon",
                            "common_taxonid",
                            "count",
                            "created",
                            "length",
                            "sequence",
                            "identity",
                            "types"));
    static final String ORGANISM = "organism";
    static final String ORGANISM_ID = "organism_id";
    static final String MEMBER = "members";

    boolean contains(List<String> fields) {
        return fields.stream().anyMatch(UNIREF_FIELDS::contains);
    }

    @Override
    public Map<String, String> mapEntity(T entry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        if (contains(fields)) {
            map.putAll(getSimpleAttributeValues(entry));
        }
        if (fields.contains(ORGANISM)) {
            map.put(ORGANISM, getOrganisms(entry));
        }
        if (fields.contains(ORGANISM_ID)) {
            map.put(ORGANISM_ID, getOrganismTaxId(entry));
        }
        if (fields.contains(MEMBER)) {
            map.put(MEMBER, getMembers(entry));
        }

        return map;
    }

    abstract Map<String, String> getSimpleAttributeValues(T entry);

    abstract String getOrganisms(T entry);

    abstract String getOrganismTaxId(T entry);

    abstract String getMembers(T entry);
}
