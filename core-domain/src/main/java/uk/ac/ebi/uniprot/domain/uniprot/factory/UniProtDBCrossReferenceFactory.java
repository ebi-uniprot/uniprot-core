package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DBXRefTypeAttribute;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;

import java.util.ArrayList;
import java.util.List;

public enum UniProtDBCrossReferenceFactory {
    INSTANCE;

    public UniProtDBCrossReference createUniProtDBCrossReference(String type,
                                                                 String id, String description) {
        return createUniProtDBCrossReference(type, id, description, null);
    }

    public UniProtDBCrossReference createUniProtDBCrossReference(String type,
                                                                 String id, String description, String thirdAttribute) {
        return createUniProtDBCrossReference(type, id, description, thirdAttribute, null);
    }

    public UniProtDBCrossReference createUniProtDBCrossReference(String type,
                                                                 String id, String description, String thirdAttribute, String fourthAttribute) {
        return createUniProtDBCrossReference(type, id, description, thirdAttribute, fourthAttribute, null);
    }

    public UniProtDBCrossReference createUniProtDBCrossReference(String databaseName,
                                                                 String id, String description, String thirdAttribute,
                                                                 String fourthAttribute, String isoformId) {
        UniProtXDbType opType = new UniProtXDbType(databaseName);
        List<Property> properties = new ArrayList<>();


        List<DBXRefTypeAttribute> attributes = opType.getDetail().getAttributes();
        addProperty(properties, attributes, 0, description);
        addProperty(properties, attributes, 1, thirdAttribute);
        addProperty(properties, attributes, 2, fourthAttribute);

        return new UniProtDBCrossReferenceImpl(opType,
                                               id, properties, isoformId);
    }

    private void addProperty(List<Property> properties, List<DBXRefTypeAttribute> attributes, int number, String value) {
        if (value == null || value.isEmpty())
            return;
        if (attributes.size() < number + 1) {
            return;
        }
        DBXRefTypeAttribute attr = attributes.get(number);
        properties.add(new Property(attr.getName(), value));
    }


}
