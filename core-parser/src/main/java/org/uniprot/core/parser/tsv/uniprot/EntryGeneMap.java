package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.Value;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public class EntryGeneMap implements NamedValueMap {
	private static final String SPACE = " ";
	private static final String SEMICOLON = "; ";
	private final List<Gene> genes;
	public static final List<String> FIELDS = 
			Arrays.asList(
					"gene_names", "gene_primary",
					"gene_synonym", "gene_oln",
					"gene_orf"
			);
	

	public EntryGeneMap(List<Gene> genes) {
		if(genes ==null) {
			this.genes = Collections.emptyList();
		}else
			this.genes = Collections.unmodifiableList(genes);
	}

	@Override
	public Map<String, String> attributeValues() {
		if(genes.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, String> map = new HashMap<>();
		map.put(FIELDS.get(0), getGeneName());
		map.put(FIELDS.get(1), getPrimaryName());
		map.put(FIELDS.get(2), getSynonyms());
		map.put(FIELDS.get(3), getOlnName());
		map.put(FIELDS.get(4), getOrfName());

		return map;
	}

	public String getGeneName() {
		if (genes.isEmpty()) {
			return "";
		}
		return genes.stream().map(this::getGeneName).collect(Collectors.joining(SEMICOLON));
	}

	private String getGeneName(Gene gene) {
		StringBuilder sb = new StringBuilder();
		if (gene.getGeneName() != null) {
			sb.append(gene.getGeneName().getValue());
		}
		String synonym = joinNames(gene.getSynonyms());
		String olnName = joinNames(gene.getOrderedLocusNames());
		String orfName = joinNames(gene.getOrfNames());
		append(sb, synonym);
		append(sb, olnName);
		append(sb, orfName);
		return sb.toString();
	}

	private void append(StringBuilder sb, String name) {
		if (name != null && !name.isEmpty()) {
			if (sb.length() > 0) {
				sb.append(SPACE);
			}
			sb.append(name);
		}
	}

	public String getPrimaryName() {
		if (genes.isEmpty()) {
			return "";
		}
		return genes.stream().map(val -> val.getGeneName()==null? "":val.getGeneName().getValue()).collect(Collectors.joining(SEMICOLON));
	}

	public String getSynonyms() {
		if (genes.isEmpty()) {
			return "";
		}
		return genes.stream().map(val -> joinNames(val.getSynonyms())).collect(Collectors.joining(SEMICOLON));

	}

	public String getOlnName() {
		if (genes.isEmpty()) {
			return "";
		}
		return genes.stream().map(val -> joinNames(val.getOrderedLocusNames())).collect(Collectors.joining(SEMICOLON));

	}

	public String getOrfName() {
		if (genes.isEmpty()) {
			return "";
		}
		return genes.stream().map(val -> joinNames(val.getOrfNames())).collect(Collectors.joining(SEMICOLON));

	}

	private String joinNames(List<? extends EvidencedValue> names) {
		if ((names != null) && !names.isEmpty()) {
			return names.stream().map(Value::getValue).collect(Collectors.joining(SPACE));
		} else
			return "";
	}
	public static  boolean contains(List<String> fields) {
		return fields.stream().anyMatch(FIELDS::contains);
		
	}
}
