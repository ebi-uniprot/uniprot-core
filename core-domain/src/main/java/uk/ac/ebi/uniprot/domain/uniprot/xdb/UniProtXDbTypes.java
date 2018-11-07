package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtXDbTypeImpl;



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
			List<UniProtXDbTypeImpl> types = objectMapper.readValue(is,
					new TypeReference<List<UniProtXDbTypeImpl>>() {
					});
			types.forEach(val -> this.types.add(val));
			typeMap =types.stream().collect(Collectors.toMap(val -> val.getName(), val->val));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public List<UniProtXDbType> getAllDBXRefTypes() {
		return types;
	}
	
	public Optional<UniProtXDbType> getType(String typeName){
		return Optional.ofNullable(typeMap.get(typeName));
	}
}
