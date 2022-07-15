package org.uniprot.core.xml.uniprot;

import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.LigandPartType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class LigandPartConverter implements Converter<LigandPartType, LigandPart> {
    private static final String COLON = ":";
    private final ObjectFactory xmlUniprotFactory;

    public LigandPartConverter() {
        this(new ObjectFactory());
    }

    public LigandPartConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public LigandPart fromXml(LigandPartType xmlObj) {
        LigandPartBuilder builder = new LigandPartBuilder();
        builder.name(xmlObj.getName());
        if (Utils.notNull(xmlObj.getDbReference())) {
            builder.id(xmlObj.getDbReference().getType() + COLON + xmlObj.getDbReference().getId());
        }
        if (Utils.notNullNotEmpty(xmlObj.getNote())) {
            builder.note(xmlObj.getNote());
        }
        if (Utils.notNullNotEmpty(xmlObj.getLabel())) {
            builder.label(xmlObj.getLabel());
        }
        return builder.build();
    }

    @Override
    public LigandPartType toXml(LigandPart uniObj) {
        LigandPartType ligandType = xmlUniprotFactory.createLigandPartType();
        ligandType.setName(uniObj.getName());
        if (Utils.notNullNotEmpty(uniObj.getId())) {
            ligandType.setDbReference(convertDbReferenceType(uniObj.getId()));
        }
        if (Utils.notNullNotEmpty(uniObj.getNote())) {
            ligandType.setNote(uniObj.getNote());
        }
        if (Utils.notNullNotEmpty(uniObj.getLabel())) {
            ligandType.setLabel(uniObj.getLabel());
        }
        return ligandType;
    }

    private DbReferenceType convertDbReferenceType(String val) {
        int index = val.indexOf(':');
        String type = val.substring(0, index);
        String id = val.substring(index + 1);
        DbReferenceType dbRef = xmlUniprotFactory.createDbReferenceType();
        dbRef.setId(id);
        dbRef.setType(type);
        return dbRef;
    }
}
