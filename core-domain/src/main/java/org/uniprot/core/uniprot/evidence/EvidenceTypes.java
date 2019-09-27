package org.uniprot.core.uniprot.evidence;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.util.Utils;
import org.uniprot.core.util.property.Property;


public enum EvidenceTypes {
    INSTANCE;
    private final String FILENAME = "META-INF/evidenceDbConfiguration.json";
    private List<EvidenceTypeDetail> types = new ArrayList<>();
    private Map<String, EvidenceTypeDetail> typeMap;

    EvidenceTypes() {
        init();
    }

    public List<EvidenceTypeDetail> getAllTypes() {
        return types;
    }

    public EvidenceTypeDetail getType(String typeName) {
        EvidenceTypeDetail type = typeMap.get(typeName);
        if (type == null) {
            throw new IllegalArgumentException(typeName + " does not exist in Evidence type list");
        }
        return type;
    }

    private void init() {
        try (InputStream configFile = EvidenceTypes.class.getClassLoader().getResourceAsStream(FILENAME)) {
            String source = Utils.loadPropertyInput(configFile);
            List<Property> list = Property.parseJsonArray(source);
            list.forEach(item -> {
                types.add(convert(item));
            });
            typeMap = types.stream().collect(Collectors.toMap(EvidenceTypeDetail::getName, val -> val));
        } catch (Exception e) {
            throw new RuntimeException("Unable to load property file", e);
        }
    }

    private  EvidenceTypeDetail convert(Property obj) {
    	   String name = obj.getString("name");
           String displayName = obj.getString("displayName");
           String uriLink = obj.getString("uriLink");
           String category = obj.getString("category");
           EvidenceTypeCategory etCategory= EvidenceTypeCategory.valueOf(category);
           return new EvidenceTypeDetail(name, displayName,  etCategory, uriLink);
           
    }
}
