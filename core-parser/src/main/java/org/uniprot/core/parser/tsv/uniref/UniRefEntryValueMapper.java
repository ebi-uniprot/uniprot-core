package org.uniprot.core.parser.tsv.uniref;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 22 Aug 2019
 */
public class UniRefEntryValueMapper extends AbstractUniRefEntryMapper<UniRefEntry> {
    @Override
    Map<String, String> getSimpleAttributeValues(UniRefEntry entry) {
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

        map.put(UNIREF_FIELDS.get(8), entry.getEntryType().getIdentity());
        map.put(UNIREF_FIELDS.get(9), getTypes(entry));
        return map;
    }

    @Override
    String getOrganisms(UniRefEntry entry) {
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

    @Override
    String getOrganismTaxId(UniRefEntry entry) {
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

    @Override
    String getMembers(UniRefEntry entry) {
        List<String> members = new ArrayList<>();
        members.add(getMember(entry.getRepresentativeMember()));
        entry.getMembers().stream().map(this::getMember).forEach(members::add);
        return String.join(DELIMITER, members);
    }

    private String getMember(UniRefMember member) {
        return member.getMemberId();
    }

    private String getTypes(UniRefEntry entry) {
        Set<String> types = new HashSet<>();
        if (Utils.notNullNotEmpty(entry.getMembers())) {
            entry.getMembers().stream()
                    .map(UniRefMember::getMemberIdType)
                    .map(UniRefMemberIdType::getDisplayName)
                    .forEach(types::add);
        }
        types.add(entry.getRepresentativeMember().getMemberIdType().getDisplayName());
        return String.join(", ", types);
    }
}
