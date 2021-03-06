package org.uniprot.core.parser.tsv.proteome;

import java.util.*;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.parser.tsv.uniprot.EntryMapUtil;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

/**
 * @author jluo
 * @date: 1 May 2019
 */
public class ProteomeTaxonomyMap implements NamedValueMap {
    public static final List<String> FIELDS =
            Collections.unmodifiableList(Arrays.asList("organism", "organism_id", "mnemonic"));

    private final Taxonomy taxonomy;

    public ProteomeTaxonomyMap(Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), EntryMapUtil.convertOrganism(taxonomy));
        map.put(FIELDS.get(1), "" + taxonomy.getTaxonId());
        map.put(FIELDS.get(2), taxonomy.getMnemonic());

        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
