package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public enum EvidenceTypes {
	INSTANCE;
	private final String FILENAME = "META-INF/evidenceDbConfiguration.json";
	private List<EvidenceType>  types = new ArrayList<>();
	private Map<String, EvidenceType> typeMap ;
	EvidenceTypes() {
		init();
	}

	private void init() {
		final ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = EvidenceTypes.class.getClassLoader().getResourceAsStream(FILENAME);) {
			types = objectMapper.readValue(is,
					new TypeReference<List<EvidenceType>>() {
					});
			typeMap =types.stream().collect(Collectors.toMap(val -> val.getName(), val->val));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	public List<EvidenceType> getAllTypes() {
		return types;
	}
	
	public Optional<EvidenceType> getType(String typeName){
		return Optional.ofNullable(typeMap.get(typeName));
	}
}
