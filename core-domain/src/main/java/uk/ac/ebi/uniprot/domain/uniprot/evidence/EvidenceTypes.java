package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbTypes;
import uk.ac.ebi.uniprot.domain.util.property.PropertyArray;
import uk.ac.ebi.uniprot.domain.util.property.PropertyObject;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public enum EvidenceTypes {
	INSTANCE;
	private final String FILENAME = "META-INF/evidenceDbConfiguration.json";
	private List<EvidenceTypeDetail>  types = new ArrayList<>();
	private Map<String, EvidenceTypeDetail> typeMap ;
	EvidenceTypes() {
		init();
	}

	private void init() {
		try{
			URL filePath = UniProtXDbTypes.class.getClassLoader().getResource(FILENAME);
			if(filePath != null) {
				File file = new File(filePath.toURI());
				String source = new String(Files.readAllBytes(file.toPath()));
				PropertyArray jsonArray = new PropertyArray(source);

				jsonArray.forEach(item -> {
					PropertyObject dbTypeDetail = (PropertyObject) item;
					String name = dbTypeDetail.getString("name");
					String displayName = dbTypeDetail.getString("displayName");
					String uriLink = dbTypeDetail.getString("uriLink");
					types.add(new EvidenceTypeDetail(name, displayName, uriLink));
				});
			}else {
				throw new RuntimeException("Unable to find property file "+FILENAME);
			}
			typeMap =types.stream().collect(Collectors.toMap(EvidenceTypeDetail::getName, val->val));
		} catch (Exception e) {
			throw new RuntimeException("Unable to load property file",e);
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
