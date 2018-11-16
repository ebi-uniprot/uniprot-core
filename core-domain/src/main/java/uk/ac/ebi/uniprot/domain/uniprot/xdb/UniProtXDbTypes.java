package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



public enum UniProtXDbTypes {
	INSTANCE;
	private final String FILENAME = "META-INF/drlineconfiguration.json";
	private List<UniProtXDbType>  types = new ArrayList<>();
	private Map<String, UniProtXDbType> typeMap ;
	UniProtXDbTypes() {
		init();
	}

	private void init() {
		final ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = UniProtXDbTypes.class.getClassLoader().getResourceAsStream(FILENAME);) {
			List<UniProtXDbTypeTemp> temps = objectMapper.readValue(is,
					new TypeReference<List<UniProtXDbTypeTemp>>() {
					});
			this.types =temps.stream().map(val -> new UniProtXDbType(val.name, val.displayName, val.category, val.uriLink, val.attributes))
			.collect(Collectors.toList());
			typeMap =types.stream().collect(Collectors.toMap(val -> val.getName(), val->val));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public List<UniProtXDbType> getAllDBXRefTypes() {
		return types;
	}
	
	public UniProtXDbType getType(String typeName){
		UniProtXDbType type =typeMap.get(typeName);
		if(type ==null) {
			throw new IllegalArgumentException (typeName + " does not exist in UniProt database type list");
		}
		return type;
	}
	static class UniProtXDbTypeTemp{
		public String name;
		public String displayName;
		public DatabaseCategory category;
		public String uriLink;
		public List<DBXRefTypeAttribute> attributes;
	}
}
