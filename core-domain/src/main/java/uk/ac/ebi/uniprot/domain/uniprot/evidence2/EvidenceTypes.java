package uk.ac.ebi.uniprot.domain.uniprot.evidence2;

import uk.ac.ebi.uniprot.domain.util.Utils;
import uk.ac.ebi.uniprot.domain.util.property.PropertyArray;
import uk.ac.ebi.uniprot.domain.util.property.PropertyObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
            PropertyArray list = new PropertyArray(source);

            list.forEach(item -> {
                PropertyObject dbTypeDetail = (PropertyObject) item;
                String name = dbTypeDetail.getString("name");
                String displayName = dbTypeDetail.getString("displayName");
                String uriLink = dbTypeDetail.getString("uriLink");
                types.add(new EvidenceTypeDetail(name, displayName, uriLink));
            });
            typeMap = types.stream().collect(Collectors.toMap(EvidenceTypeDetail::getName, val -> val));
        } catch (Exception e) {
            throw new RuntimeException("Unable to load property file", e);
        }
    }

}
