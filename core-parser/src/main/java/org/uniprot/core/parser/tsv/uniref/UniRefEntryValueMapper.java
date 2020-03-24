package org.uniprot.core.parser.tsv.uniref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefMember;

/**
 * @author jluo
 * @date: 22 Aug 2019
 */
public class UniRefEntryValueMapper implements EntityValueMapper<UniRefEntry> {

    private static final String DELIMITER = "; ";
    private static final List<String> UNIREF_FIELDS =
            Collections.unmodifiableList(
                    Arrays.asList(
                            "id",
                            "name",
                            "common_taxon",
                            "common_taxonid",
                            "count",
                            "created",
                            "length",
                            "sequence"));
    private static final String ORGANISM = "organism";
    private static final String ORGANISM_ID = "organism_id";
    private static final String MEMBER = "member";

    @Override
    public Map<String, String> mapEntity(UniRefEntry entry, List<String> fields) {
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

    private static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(UNIREF_FIELDS::contains);
    }

    private Map<String, String> getSimpleAttributeValues(UniRefEntry entry) {
        Map<String, String> map = new HashMap<>();
        map.put(UNIREF_FIELDS.get(0), entry.getId().getValue());
        map.put(UNIREF_FIELDS.get(1), entry.getName());
        map.put(UNIREF_FIELDS.get(2), entry.getCommonTaxon());
        map.put(UNIREF_FIELDS.get(3), Long.toString(entry.getCommonTaxonId()));
        map.put(UNIREF_FIELDS.get(4), Integer.toString(entry.getMemberCount()));
        map.put(UNIREF_FIELDS.get(5), entry.getUpdated().toString());
        map.put(
                UNIREF_FIELDS.get(6),
                Integer.toString(entry.getRepresentativeMember().getSequenceLength()));
        map.put(UNIREF_FIELDS.get(7), entry.getRepresentativeMember().getSequence().getValue());
        return map;
    }

    static String getOrganisms(UniRefEntry entry) {
        List<String> organisms = new ArrayList<>();
        organisms.add(entry.getRepresentativeMember().getOrganismName());
        entry.getMembers().stream()
                .map(UniRefMember::getOrganismName)
                .forEach(
                        val -> {
                            if (!organisms.contains(val)) {
                                organisms.add(val);
                            }
                        });
        return String.join(DELIMITER, organisms);
    }

    static String getOrganismTaxId(UniRefEntry entry) {
        List<Long> taxIds = new ArrayList<>();
        taxIds.add(entry.getRepresentativeMember().getOrganismTaxId());
        entry.getMembers().stream()
                .map(UniRefMember::getOrganismTaxId)
                .forEach(
                        val -> {
                            if (!taxIds.contains(val)) {
                                taxIds.add(val);
                            }
                        });
        return taxIds.stream().map(Object::toString).collect(Collectors.joining(DELIMITER));
    }

    static String getMembers(UniRefEntry entry) {
        List<String> members = new ArrayList<>();
        members.add(getMember(entry.getRepresentativeMember()));
        entry.getMembers().stream().map(UniRefEntryValueMapper::getMember).forEach(members::add);
        return String.join(DELIMITER, members);
    }

    private static String getMember(UniRefMember member) {
        return member.getMemberId();
    }
}
