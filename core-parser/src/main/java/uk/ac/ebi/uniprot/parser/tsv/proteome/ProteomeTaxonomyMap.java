package uk.ac.ebi.uniprot.parser.tsv.proteome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.uniprot.taxonomy.Taxonomy;

import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

/**
 *
 * @author jluo
 * @date: 1 May 2019
 *
*/

public class ProteomeTaxonomyMap implements NamedValueMap {
	   public static final List<String> FIELDS = Arrays.asList("organism", "organism_id","taxon_mnemonic");

	   private final Taxonomy taxonomy;

		public ProteomeTaxonomyMap(Taxonomy taxonomy) {
			this.taxonomy = taxonomy;
		}

		@Override
		public Map<String, String> attributeValues() {
			Map<String, String> map = new HashMap<>();
			map.put(FIELDS.get(0), EntryMapUtil.convertOrganism(taxonomy));
			map.put(FIELDS.get(1), "" + taxonomy.getTaxonId());
			map.put(FIELDS.get(2),  taxonomy.getMnemonic());

			return map;
		}


	 public static boolean contains(List<String> fields) {
	        return fields.stream().anyMatch(FIELDS::contains);

	    }

}

