package uk.ac.ebi.uniprot.cv.xdb;


import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.common.property.PropertyArray;
import uk.ac.ebi.uniprot.common.property.PropertyObject;
import uk.ac.ebi.uniprot.cv.xdb.validator.DBXRefValidator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public enum UniProtXDbTypes {
    INSTANCE;
    private final String FILENAME = "META-INF/drlineconfiguration.json";
    private final String NEW_DB_LIST = "META-INF/new_db_list.txt";
    
    private List<UniProtXDbTypeDetail> types = new ArrayList<>();
    private Map<String, UniProtXDbTypeDetail> typeMap;

    UniProtXDbTypes() {
        init();
    }

    public List<UniProtXDbTypeDetail> getAllDBXRefTypes() {
        return types;
    }

    public Map<String, UniProtXDbTypeDetail> getTypeMap(){
        return this.typeMap;
    }

    public UniProtXDbTypeDetail getType(String typeName) {
        UniProtXDbTypeDetail type = typeMap.get(typeName);
        if (type == null) {
            throw new IllegalArgumentException(typeName + " does not exist in UniProt database type list");
        }
        // validate the UniProtXDbTypeDetail with dbXRef.txt
        
        return type;
    }

    public List<UniProtXDbTypeDetail> getDBTypesByCategory(DatabaseCategory dbCatergory){
        return this.types
                .stream()
                .filter(dbType -> dbType.getCategory() == dbCatergory)
                .collect(Collectors.toList());
    }
    private List<String> getNewDB(){
    	List<String> newDbs = new ArrayList<>();
  
    	 try (InputStream inputStream = UniProtXDbTypes.class.getClassLoader().getResourceAsStream(NEW_DB_LIST);		 
    			 BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
             String line;
             while ((line = br.readLine()) != null) {
            	 newDbs.add(line);
             }
    	
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return newDbs;
    }
    
    private void init() {
    	List<String> newDbs = getNewDB();
        try (InputStream configFile = UniProtXDbTypes.class.getClassLoader().getResourceAsStream(FILENAME)) {
            String source = Utils.loadPropertyInput(configFile);
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
                        String attributeXmlTag = property.optString("xmlTag", null);
                        String attributeUriLink = property.optString("uriLink", null);
                        attributes.add(new DBXRefTypeAttribute(attributeName, attributeXmlTag, attributeUriLink));
                    });
                }
                UniProtXDbTypeDetail xdbType = new UniProtXDbTypeDetail(name, displayName, DatabaseCategory
                        .typeOf(category), uriLink, attributes);
                if(!newDbs.contains(xdbType.getName()))
                	DBXRefValidator.validate(xdbType);
                types.add(xdbType);
            });
            typeMap = types.stream().collect(Collectors.toMap(UniProtXDbTypeDetail::getDisplayName, val -> val));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
