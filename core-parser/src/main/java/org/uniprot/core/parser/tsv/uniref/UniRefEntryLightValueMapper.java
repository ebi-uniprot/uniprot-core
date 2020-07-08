package org.uniprot.core.parser.tsv.uniref;

import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created 07/07/2020
 *
 * @author Edd
 */
public class UniRefEntryLightValueMapper extends AbstractUniRefEntryMapper<UniRefEntryLight> {
    @Override
    public Map<String, String> mapEntity(UniRefEntryLight entry, List<String> fields) {
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

    @Override
    Map<String, String> getSimpleAttributeValues(UniRefEntryLight entry) {
        Map<String, String> map = new HashMap<>();
        map.put(UNIREF_FIELDS.get(0), entry.getId().getValue());
        map.put(UNIREF_FIELDS.get(1), entry.getName());
        map.put(UNIREF_FIELDS.get(2), entry.getCommonTaxon());
        map.put(UNIREF_FIELDS.get(3), Long.toString(entry.getCommonTaxonId()));
        map.put(UNIREF_FIELDS.get(4), Integer.toString(entry.getMemberCount()));
        map.put(UNIREF_FIELDS.get(5), entry.getUpdated().toString());
        map.put(UNIREF_FIELDS.get(6), Integer.toString(entry.getRepresentativeSequence().length()));
        map.put(UNIREF_FIELDS.get(7), entry.getRepresentativeSequence());
        map.put(UNIREF_FIELDS.get(8), entry.getEntryType().getIdentity());
        map.put(UNIREF_FIELDS.get(9), getTypes(entry.getMemberIdTypes()));
        return map;
    }

    @Override
    String getOrganisms(UniRefEntryLight entry) {
        return String.join(DELIMITER, entry.getOrganisms());
    }

    @Override
    String getOrganismTaxId(UniRefEntryLight entry) {
        return entry.getOrganismIds().stream()
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    String getMembers(UniRefEntryLight entry) {
        return String.join(DELIMITER, entry.getMembers());
    }

    String getTypes(Set<UniRefMemberIdType> memberIdTypes) {
        return memberIdTypes.stream()
                .sorted(Comparator.comparing(UniRefMemberIdType::getDisplayOrder))
                .map(UniRefMemberIdType::getName)
                .collect(Collectors.joining(DELIMITER));
    }
}
