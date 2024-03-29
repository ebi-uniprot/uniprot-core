package org.uniprot.core.xml.uniprot;

import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.LigandType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class LigandConverter implements Converter<LigandType, Ligand> {
    private static final String COLON = ":";
    private static final String CHEBI_ID_PREFIX = "ChEBI:CHEBI:";
    private final ObjectFactory xmlUniprotFactory;

    public LigandConverter() {
        this(new ObjectFactory());
    }

    public LigandConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Ligand fromXml(LigandType xmlObj) {
        LigandBuilder builder = new LigandBuilder();
        if (Utils.notNull(xmlObj.getDbReference())) {
            builder.id(xmlObj.getDbReference().getType() + COLON + xmlObj.getDbReference().getId());
        }
        builder.name(xmlObj.getName());
        if (Utils.notNullNotEmpty(xmlObj.getLabel())) {
            builder.label(xmlObj.getLabel());
        }
        if (Utils.notNullNotEmpty(xmlObj.getNote())) {
            builder.note(xmlObj.getNote());
        }
        return builder.build();
    }

    @Override
    public LigandType toXml(Ligand uniObj) {
        LigandType ligandType = xmlUniprotFactory.createLigandType();
        if (Utils.notNullNotEmpty(uniObj.getId())) {
            ligandType.setDbReference(convertDbReferenceType(uniObj.getId()));
        }
        ligandType.setName(uniObj.getName());
        if (Utils.notNullNotEmpty(uniObj.getLabel())) {
            ligandType.setLabel(uniObj.getLabel());
        }
        if (Utils.notNullNotEmpty(uniObj.getNote())) {
            ligandType.setNote(uniObj.getNote());
        }
        return ligandType;
    }

    private DbReferenceType convertDbReferenceType(String val) {
        if (!val.contains(COLON)) {
            val = CHEBI_ID_PREFIX + val;
        }
        int index = val.indexOf(':');
        String id = val.substring(index + 1);
        String type = val.substring(0, index);
        DbReferenceType dbRef = xmlUniprotFactory.createDbReferenceType();
        dbRef.setType(type);
        dbRef.setId(id);
        return dbRef;
    }
}
