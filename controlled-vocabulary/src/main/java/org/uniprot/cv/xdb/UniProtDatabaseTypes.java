package org.uniprot.cv.xdb;

import static org.uniprot.cv.common.CVSystemProperties.getDrDatabaseTypesLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseCategory;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.util.property.Property;
import org.uniprot.cv.common.AbstractFileReader;

public enum UniProtDatabaseTypes {
    INSTANCE;
    private String fileName = "META-INF/drlineconfiguration.json";

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

    public List<UniProtDatabaseDetail> getInternalDatabaseDetails() {
        return UniProtDatabaseTypes.INSTANCE.getAllDbTypes().stream()
                .filter(dbDetail -> "internal".equals(dbDetail.getType()))
                .collect(Collectors.toList());
    }

    private void init() {

        String source =
                String.join(
                        "",
                        AbstractFileReader.readLines(
                                Optional.ofNullable(getDrDatabaseTypesLocation())
                                        .orElse(fileName)));
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
                    String idMappingName = item.optString("idMappingName", null);
                    String type = item.optString("type", null);

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
                                    linkedReason,
                                    idMappingName,
                                    type);
                    types.add(xdbType);
                });
        typeMap =
                types.stream()
                        .collect(Collectors.toMap(UniProtDatabaseDetail::getName, val -> val));
    }
}
