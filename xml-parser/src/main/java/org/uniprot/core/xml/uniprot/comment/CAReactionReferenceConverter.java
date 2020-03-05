package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.ReactionDatabase;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class CAReactionReferenceConverter
        implements Converter<DbReferenceType, CrossReference<ReactionDatabase>> {
    private final ObjectFactory xmlUniprotFactory;

    public CAReactionReferenceConverter() {
        this(new ObjectFactory());
    }

    public CAReactionReferenceConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public CrossReference<ReactionDatabase> fromXml(DbReferenceType xmlObj) {
        ReactionDatabase type = ReactionDatabase.typeOf(xmlObj.getType());
        return new CrossReferenceBuilder<ReactionDatabase>()
                .database(type)
                .id(xmlObj.getId())
                .build();
    }

    @Override
    public DbReferenceType toXml(CrossReference<ReactionDatabase> uniObj) {
        DbReferenceType dbref = xmlUniprotFactory.createDbReferenceType();
        dbref.setType(uniObj.getDatabase().toDisplayName());
        dbref.setId(uniObj.getId());
        return dbref;
    }
}
