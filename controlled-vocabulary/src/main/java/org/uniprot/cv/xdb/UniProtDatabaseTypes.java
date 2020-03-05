package org.uniprot.cv.xdb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseCategory;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.util.Utils;
import org.uniprot.core.util.property.Property;

public enum UniProtDatabaseTypes {
    INSTANCE;
    private final String FILENAME = "META-INF/drlineconfiguration.json";
    private final String NEW_DB_LIST = "META-INF/new_db_list.txt";

    private List<UniProtDatabaseDetail> types = new ArrayList<>();
    private Map<String, UniProtDatabaseDetail> typeMap;

    UniProtDatabaseTypes() {
        init();
    }

    public List<UniProtDatabaseDetail> getAllDbTypes() {
        return types;
    }

    public Map<String, UniProtDatabaseDetail> getAllDbTypesMap() {
        return this.typeMap;
    }

    public UniProtDatabaseDetail getDbTypeByName(String typeName) {
        UniProtDatabaseDetail type = typeMap.get(typeName);
        if (type == null) {
            throw new IllegalArgumentException(
                    typeName + " does not exist in UniProt database type list");
        }
        // validate the UniProtDatabaseDetail with dbXRef.txt

        return type;
    }

    public List<UniProtDatabaseDetail> getDBTypesByCategory(UniProtDatabaseCategory dbCatergory) {
        return this.types.stream()
                .filter(dbType -> dbType.getCategory() == dbCatergory)
                .collect(Collectors.toList());
    }

    private List<String> getNewDB() {
        List<String> newDbs = new ArrayList<>();

        try (InputStream inputStream =
                        UniProtDatabaseTypes.class
                                .getClassLoader()
                                .getResourceAsStream(NEW_DB_LIST);
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
                UniProtDatabaseTypes.class.getClassLoader().getResourceAsStream(FILENAME)) {
            String source = Utils.loadPropertyInput(configFile);
            List<Property> jsonArray = Property.parseJsonArray(source);

            jsonArray.forEach(
                    item -> {
                        String name = item.getString("name");
                        String displayName = item.getString("displayName");
                        String category = item.getString("category");
                        String uriLink = item.getString("uriLink");

                        String implicit = item.optString("implicit", "false");
                        boolean isImplicit = Boolean.parseBoolean(implicit);

                        String linkedReason = item.optString("linkedReason", null);

                        List<UniProtDatabaseAttribute> attributes = new ArrayList<>();
                        List<Property> properties = item.getProperties("attributes");
                        if (properties != null) {
                            properties.forEach(
                                    p -> {
                                        String attributeName = p.getString("name");
                                        String attributeXmlTag = p.optString("xmlTag", null);
                                        String attributeUriLink = p.optString("uriLink", null);
                                        attributes.add(
                                                new UniProtDatabaseAttribute(
                                                        attributeName,
                                                        attributeXmlTag,
                                                        attributeUriLink));
                                    });
                        }
                        UniProtDatabaseDetail xdbType =
                                new UniProtDatabaseDetail(
                                        name,
                                        displayName,
                                        UniProtDatabaseCategory.typeOf(category),
                                        uriLink,
                                        attributes,
                                        isImplicit,
                                        linkedReason);
                        types.add(xdbType);
                    });
            typeMap =
                    types.stream()
                            .collect(Collectors.toMap(UniProtDatabaseDetail::getName, val -> val));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}