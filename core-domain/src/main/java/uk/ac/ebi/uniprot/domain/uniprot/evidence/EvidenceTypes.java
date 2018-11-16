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
	private List<EvidenceType>  types = new ArrayList<>();
	private Map<String, EvidenceType> typeMap ;
	EvidenceTypes() {
		init();
	}

	private void init() {
		final ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = EvidenceTypes.class.getClassLoader().getResourceAsStream(FILENAME);) {
			List<EvidenceTypeTemp> temps = objectMapper.readValue(is,
					new TypeReference<List<EvidenceTypeTemp>>() {
					});
			this.types =temps.stream().map(val -> new EvidenceType(val.name, val.displayName, val.uriLink))
			.collect(Collectors.toList());
			typeMap =types.stream().collect(Collectors.toMap(val -> val.getName(), val->val));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	public List<EvidenceType> getAllTypes() {
		return types;
	}
	
	public EvidenceType getType(String typeName){
		EvidenceType type = typeMap.get(typeName);
		if(type ==null) {
			throw new IllegalArgumentException (typeName + " does not exist in Evidence type list");
		}
		return type;
	}
	 static class EvidenceTypeTemp{
		  public String name;
		  public String displayName;
		  public String uriLink;
		  
	}
}
