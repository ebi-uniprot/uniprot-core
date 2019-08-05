package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class CAReactionReferenceConverter implements Converter<DbReferenceType, DBCrossReference<ReactionReferenceType>> {
    private final ObjectFactory xmlUniprotFactory;

    public CAReactionReferenceConverter() {
        this(new ObjectFactory());
    }

    public CAReactionReferenceConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public DBCrossReference<ReactionReferenceType> fromXml(DbReferenceType xmlObj) {
        ReactionReferenceType type = ReactionReferenceType.typeOf(xmlObj.getType());
        return new DBCrossReferenceBuilder<ReactionReferenceType>()
                .databaseType(type)
                .id(xmlObj.getId())
                .build();
    }

    @Override
    public DbReferenceType toXml(DBCrossReference<ReactionReferenceType> uniObj) {
        DbReferenceType dbref = xmlUniprotFactory.createDbReferenceType();
        dbref.setType(uniObj.getDatabaseType().toDisplayName());
        dbref.setId(uniObj.getId());
        return dbref;
    }

}
