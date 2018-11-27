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
	private List<UniProtXDbTypeDetail>  types = new ArrayList<>();
	private Map<String, UniProtXDbTypeDetail> typeMap ;
	UniProtXDbTypes() {
		init();
	}

	private void init() {
		final ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = UniProtXDbTypes.class.getClassLoader().getResourceAsStream(FILENAME);) {
			types = objectMapper.readValue(is,
					new TypeReference<List<UniProtXDbTypeDetail>>() {
					});
			
			typeMap =types.stream().collect(Collectors.toMap(val -> val.getDisplayName(), val->val));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public List<UniProtXDbTypeDetail> getAllDBXRefTypes() {
		return types;
	}
	
	public UniProtXDbTypeDetail getType(String typeName){
		UniProtXDbTypeDetail type =typeMap.get(typeName);
		if(type ==null) {
			throw new IllegalArgumentException (typeName + " does not exist in UniProt database type list");
		}
		return type;
	}
	
}
