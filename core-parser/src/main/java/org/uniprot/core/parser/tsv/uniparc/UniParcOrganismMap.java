package org.uniprot.core.parser.tsv.uniparc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.uniprot.EntryMapUtil;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;

import com.google.common.base.Strings;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
public class UniParcOrganismMap implements NamedValueMap {
    private static final String DELIMITER = "; ";
    public static final List<String> FIELDS = Arrays.asList("organism", "organism_id");
    private final List<Taxonomy> organisms;

    public UniParcOrganismMap(List<Taxonomy> organisms) {
        this.organisms = organisms;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        String organismNames =
                organisms.stream()
                        .map(val -> EntryMapUtil.convertOrganism(val))
                        .filter(val -> !Strings.isNullOrEmpty(val))
                        .collect(Collectors.joining(DELIMITER));
        String taxIds =
                organisms.stream()
                        .map(val -> "" + val.getTaxonId())
                        .filter(val -> !Strings.isNullOrEmpty(val) && !val.equals("O"))
                        .collect(Collectors.joining(DELIMITER));

        map.put(FIELDS.get(0), organismNames);
        map.put(FIELDS.get(1), taxIds);

        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
