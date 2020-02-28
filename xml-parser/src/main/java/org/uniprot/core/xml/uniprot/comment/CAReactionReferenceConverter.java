package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.ReactionDatabase;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class CAReactionReferenceConverter
        implements Converter<DbReferenceType, DBCrossReference<ReactionDatabase>> {
    private final ObjectFactory xmlUniprotFactory;

    public CAReactionReferenceConverter() {
        this(new ObjectFactory());
    }

    public CAReactionReferenceConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public DBCrossReference<ReactionDatabase> fromXml(DbReferenceType xmlObj) {
        ReactionDatabase type = ReactionDatabase.typeOf(xmlObj.getType());
        return new DBCrossReferenceBuilder<ReactionDatabase>()
                .databaseType(type)
                .id(xmlObj.getId())
                .build();
    }

    @Override
    public DbReferenceType toXml(DBCrossReference<ReactionDatabase> uniObj) {
        DbReferenceType dbref = xmlUniprotFactory.createDbReferenceType();
        dbref.setType(uniObj.getDatabaseType().toDisplayName());
        dbref.setId(uniObj.getId());
        return dbref;
    }
}
