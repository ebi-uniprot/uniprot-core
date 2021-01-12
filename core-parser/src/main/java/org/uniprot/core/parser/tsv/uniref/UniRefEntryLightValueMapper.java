package org.uniprot.core.parser.tsv.uniref;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.Sequence;
import org.uniprot.core.parser.tsv.uniprot.EntryMapUtil;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismName;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;

/**
 * Created 07/07/2020
 *
 * @author Edd
 */
public class UniRefEntryLightValueMapper extends AbstractUniRefEntryMapper<UniRefEntryLight> {
    @Override
    Map<String, String> getSimpleAttributeValues(UniRefEntryLight entry) {
        Map<String, String> map = new HashMap<>();
        map.put(UNIREF_FIELDS.get(0), entry.getId().getValue());
        map.put(UNIREF_FIELDS.get(1), entry.getName());
        String organismCommon = "";
        String organismCommonId = "";
        if (entry.getCommonTaxon() != null) {
            organismCommon = EntryMapUtil.convertOrganism(entry.getCommonTaxon());
            organismCommonId = Long.toString(entry.getCommonTaxon().getTaxonId());
        }
        Sequence sequence = entry.getRepresentativeMember().getSequence();
        map.put(UNIREF_FIELDS.get(2), organismCommon);
        map.put(UNIREF_FIELDS.get(3), organismCommonId);
        map.put(UNIREF_FIELDS.get(4), Integer.toString(entry.getMemberCount()));
        map.put(UNIREF_FIELDS.get(5), entry.getUpdated().toString());
        map.put(UNIREF_FIELDS.get(6), Integer.toString(sequence.getLength()));
        map.put(UNIREF_FIELDS.get(7), sequence.getValue());
        map.put(UNIREF_FIELDS.get(8), entry.getEntryType().getIdentity());
        map.put(UNIREF_FIELDS.get(9), getTypes(entry.getMemberIdTypes()));
        return map;
    }

    @Override
    String getOrganisms(UniRefEntryLight entry) {
        return entry.getOrganisms().stream()
                .map(organism -> (OrganismName) organism)
                .map(EntryMapUtil::convertOrganism)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    String getOrganismTaxId(UniRefEntryLight entry) {
        return entry.getOrganisms().stream()
                .map(Organism::getTaxonId)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    String getMembers(UniRefEntryLight entry) {
        return String.join(DELIMITER, entry.getMembers());
    }

    String getTypes(Set<UniRefMemberIdType> memberIdTypes) {
        return memberIdTypes.stream()
                .sorted(Comparator.comparing(UniRefMemberIdType::getMemberIdTypeId))
                .map(UniRefMemberIdType::getName)
                .collect(Collectors.joining(DELIMITER));
    }
}
