package org.uniprot.cv.xdb;

import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseCategory;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.util.Utils;
import org.uniprot.core.util.property.Property;
import org.uniprot.cv.common.AbstractFileReader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.uniprot.cv.common.CVSystemProperties.getDrDatabaseTypesLocation;

public enum UniProtDatabaseTypes {
    INSTANCE;
    private final String fileName = "META-INF/drlineconfiguration.json";

    private final List<UniProtDatabaseDetail> uniProtKBDbTypes = new ArrayList<>();
    private final List<UniProtDatabaseDetail> diseaseDbTypes = new ArrayList<>();
    private final Map<String, List<UniProtDatabaseDetail>> databaseCollectionOperations = Map.of("UNIPROTKB", uniProtKBDbTypes,
            "DISEASE", diseaseDbTypes);
    private Map<String, UniProtDatabaseDetail> uniProtKBDbTypeMap;

    UniProtDatabaseTypes() {
        init();
    }

    public List<UniProtDatabaseDetail> getUniProtKBDbTypes() {
        return uniProtKBDbTypes;
    }

    public List<UniProtDatabaseDetail> getDiseaseDbTypes() {
        return diseaseDbTypes;
    }

    public Map<String, UniProtDatabaseDetail> getUniProtKBDbTypesMap() {
        return this.uniProtKBDbTypeMap;
    }

    public UniProtDatabaseDetail getDbTypeByName(String typeName) {
        UniProtDatabaseDetail type = uniProtKBDbTypeMap.get(typeName);
        if (type == null) {
            throw new IllegalArgumentException(
                    typeName + " does not exist in UniProt database type list");
        }
        // validate the UniProtDatabaseDetail with dbXRef.txt

        return type;
    }

    public List<UniProtDatabaseDetail> getDBTypesByCategory(UniProtDatabaseCategory dbCatergory) {
        return this.uniProtKBDbTypes.stream()
                .filter(dbType -> dbType.getCategory() == dbCatergory)
                .collect(Collectors.toList());
    }

    public List<UniProtDatabaseDetail> getInternalDatabaseDetails() {
        return UniProtDatabaseTypes.INSTANCE.getUniProtKBDbTypes().stream()
                .filter(dbDetail -> "internal".equals(dbDetail.getType()))
                .collect(Collectors.toList());
    }

    private void init() {
        String source = getSourceAsString();
        List<Property> jsonArray = Property.parseJsonArray(source);

        jsonArray.forEach(
                item -> {
                    String name = item.getString("name");
                    String displayName = item.getString("displayName");
                    String category = item.getString("category");
                    String uriLink = item.getString("uriLink");
                    String uniProtDataTypeString = item.optString("uniProtDataTypes", "UNIPROTKB");
                    List<String> uniProtDataTypes = Arrays.asList(uniProtDataTypeString.split(",", -1));

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
                                    type,
                                    uniProtDataTypes);

                    uniProtDataTypes.forEach(dt -> addToDbCollection(dt, xdbType));
                });
        uniProtKBDbTypeMap =
                uniProtKBDbTypes.stream()
                        .collect(Collectors.toMap(UniProtDatabaseDetail::getName, val -> val));
    }

    private void addToDbCollection(String dt, UniProtDatabaseDetail xdbType) {
        if (databaseCollectionOperations.containsKey(dt)) {
            databaseCollectionOperations.get(dt).add(xdbType);
        }
    }

    String getSourceAsString() {
        try {
            String location = getDrDatabaseTypesLocation();
            if (Utils.notNullNotEmpty(location)) {
                Path path = Paths.get(location);
                if (Files.exists(path)) {
                    return Files.readString(path);
                }
            }
            return String.join("", AbstractFileReader.readLines(fileName));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read source file", e);
        }
    }
}
