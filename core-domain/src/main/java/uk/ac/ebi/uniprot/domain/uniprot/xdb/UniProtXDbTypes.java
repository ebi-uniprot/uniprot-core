package uk.ac.ebi.uniprot.domain.uniprot.xdb;


import uk.ac.ebi.uniprot.domain.util.property.PropertyArray;
import uk.ac.ebi.uniprot.domain.util.property.PropertyObject;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public enum UniProtXDbTypes {
	INSTANCE;
	private final String FILENAME = "META-INF/drlineconfiguration.json";
	private List<UniProtXDbTypeDetail>  types = new ArrayList<>();
	private Map<String, UniProtXDbTypeDetail> typeMap ;
	UniProtXDbTypes() {
		init();
	}

	private void init() {
		try {
			URL filePath = UniProtXDbTypes.class.getClassLoader().getResource(FILENAME);
			if(filePath != null) {
				File file = new File(filePath.toURI());
				String source = new String(Files.readAllBytes(file.toPath()));
				PropertyArray jsonArray = new PropertyArray(source);

				jsonArray.forEach(item -> {
					PropertyObject dbTypeDetail = (PropertyObject) item;
					String name = dbTypeDetail.getString("name");
					String displayName = dbTypeDetail.getString("displayName");
					String category = dbTypeDetail.getString("category");
					String uriLink = dbTypeDetail.getString("uriLink");
					List<DBXRefTypeAttribute> attributes = new ArrayList<>();
					PropertyArray properties = dbTypeDetail.optJSONArray("attributes");
					if (properties != null) {
						properties.forEach(p -> {
							PropertyObject property = (PropertyObject) p;
							String attributeName = property.getString("name");
							String attributeXmlTag =  property.optString("xmlTag",null);
							String attributeUriLink = property.optString("uriLink",null);
							attributes.add(new DBXRefTypeAttribute(attributeName, attributeXmlTag, attributeUriLink));
						});
					}
				types.add(new UniProtXDbTypeDetail(name, displayName, DatabaseCategory.typeOf(category), uriLink, attributes));
				});
			}else {
				throw new RuntimeException("Unable to find property file "+FILENAME);
			}
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
