package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public enum EvidenceTypes {
	INSTANCE;
	private final String FILENAME = "META-INF/evidenceDbConfiguration.json";
	private List<EvidenceTypeDetail>  types = new ArrayList<>();
	private Map<String, EvidenceTypeDetail> typeMap ;
	EvidenceTypes() {
		init();
	}

	private void init() {
		final ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = EvidenceTypes.class.getClassLoader().getResourceAsStream(FILENAME);) {
			types = objectMapper.readValue(is,
					new TypeReference<List<EvidenceTypeDetail>>() {
					});
			typeMap =types.stream().collect(Collectors.toMap(val -> val.getName(), val->val));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	public List<EvidenceTypeDetail> getAllTypes() {
		return types;
	}
	
	public EvidenceTypeDetail getType(String typeName){
		EvidenceTypeDetail type = typeMap.get(typeName);
		if(type ==null) {
			throw new IllegalArgumentException (typeName + " does not exist in Evidence type list");
		}
		return type;
	}
	
}
