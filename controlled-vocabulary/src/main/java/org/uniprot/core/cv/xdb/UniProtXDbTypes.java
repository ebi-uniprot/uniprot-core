package org.uniprot.core.cv.xdb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.cv.xdb.validator.DBXRefValidator;
import org.uniprot.core.util.Utils;
import org.uniprot.core.util.property.Property;

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

    public Map<String, UniProtXDbTypeDetail> getTypeMap() {
        return this.typeMap;
    }

    public UniProtXDbTypeDetail getType(String typeName) {
        UniProtXDbTypeDetail type = typeMap.get(typeName);
        if (type == null) {
            throw new IllegalArgumentException(
                    typeName + " does not exist in UniProt database type list");
        }
        // validate the UniProtXDbTypeDetail with dbXRef.txt

        return type;
    }

    public List<UniProtXDbTypeDetail> getDBTypesByCategory(DatabaseCategory dbCatergory) {
        return this.types.stream()
                .filter(dbType -> dbType.getCategory() == dbCatergory)
                .collect(Collectors.toList());
    }

    private List<String> getNewDB() {
        List<String> newDbs = new ArrayList<>();

        try (InputStream inputStream =
                        UniProtXDbTypes.class.getClassLoader().getResourceAsStream(NEW_DB_LIST);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                newDbs.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDbs;
    }

    private void init() {
        List<String> newDbs = getNewDB();
        try (InputStream configFile =
                UniProtXDbTypes.class.getClassLoader().getResourceAsStream(FILENAME)) {
            String source = Utils.loadPropertyInput(configFile);
            List<Property> jsonArray = Property.parseJsonArray(source);

            jsonArray.forEach(
                    item -> {
                        String name = item.getString("name");
                        String displayName = item.getString("displayName");
                        String category = item.getString("category");
                        String uriLink = item.getString("uriLink");
                        List<DBXRefTypeAttribute> attributes = new ArrayList<>();
                        List<Property> properties = item.getProperties("attributes");
                        if (properties != null) {
                            properties.forEach(
                                    p -> {
                                        String attributeName = p.getString("name");
                                        String attributeXmlTag = p.optString("xmlTag", null);
                                        String attributeUriLink = p.optString("uriLink", null);
                                        attributes.add(
                                                new DBXRefTypeAttribute(
                                                        attributeName,
                                                        attributeXmlTag,
                                                        attributeUriLink));
                                    });
                        }
                        UniProtXDbTypeDetail xdbType =
                                new UniProtXDbTypeDetail(
                                        name,
                                        displayName,
                                        DatabaseCategory.typeOf(category),
                                        uriLink,
                                        attributes);
                        if (!newDbs.contains(xdbType.getName())) DBXRefValidator.validate(xdbType);
                        types.add(xdbType);
                    });
            typeMap =
                    types.stream()
                            .collect(
                                    Collectors.toMap(
                                            UniProtXDbTypeDetail::getDisplayName, val -> val));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
