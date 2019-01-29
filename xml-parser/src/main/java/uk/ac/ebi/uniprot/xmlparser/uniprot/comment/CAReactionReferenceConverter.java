package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

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
