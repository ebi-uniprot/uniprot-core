package org.uniprot.core.xml.uniprot;

import java.math.BigInteger;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.EvidenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.SourceType;

public class EvidenceConverter implements Converter<EvidenceType, Evidence> {
    private final ObjectFactory xmlUniprotFactory;
    private final EvidenceXrefConverter xrefConverter;

    public EvidenceConverter() {
        this(new ObjectFactory());
    }

    public EvidenceConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.xrefConverter = new EvidenceXrefConverter(xmlUniprotFactory);
    }

    @Override
    public Evidence fromXml(EvidenceType xmlObj) {
        EvidenceCode evCode = EvidenceCode.typeOf(xmlObj.getType());

        EvidenceBuilder evidenceBuilder = new EvidenceBuilder().evidenceCode(evCode);
        if (xmlObj.getSource() != null) {
            DBCrossReference<EvidenceDatabase> xref = xrefConverter.fromXml(xmlObj.getSource());
            evidenceBuilder.databaseId(xref.getId()).databaseName(xref.getDatabaseType().getName());
        }

        return evidenceBuilder.build();
    }

    @Override
    public EvidenceType toXml(Evidence uniObj) {
        EvidenceType xmlObj = xmlUniprotFactory.createEvidenceType();
        xmlObj.setType(uniObj.getEvidenceCode().getCode());
        if (uniObj.getSource() != null) {
            xmlObj.setSource(xrefConverter.toXml(uniObj.getSource()));
        }

        xmlObj.setKey(BigInteger.valueOf(1)); // set default key
        return xmlObj;
    }

    public static class EvidenceXrefConverter
            implements Converter<SourceType, DBCrossReference<EvidenceDatabase>> {
        private static final String REFERENCE = "Reference";
        private static final String REF = "Ref.";
        private final ObjectFactory xmlUniprotFactory;

        public EvidenceXrefConverter() {
            this(new ObjectFactory());
        }

        public EvidenceXrefConverter(ObjectFactory xmlUniprotFactory) {
            this.xmlUniprotFactory = xmlUniprotFactory;
        }

        @Override
        public DBCrossReference<EvidenceDatabase> fromXml(SourceType xmlObj) {
            if (xmlObj.getDbReference() != null) {
                return new DBCrossReferenceBuilder<EvidenceDatabase>()
                        .databaseType(new EvidenceDatabase(xmlObj.getDbReference().getType()))
                        .id(xmlObj.getDbReference().getId())
                        .build();
            } else {
                String attr = REF + xmlObj.getRef().toString();
                return new DBCrossReferenceBuilder<EvidenceDatabase>()
                        .databaseType(new EvidenceDatabase(REFERENCE))
                        .id(attr)
                        .build();
            }
        }

        @Override
        public SourceType toXml(DBCrossReference<EvidenceDatabase> uniObj) {
            SourceType source = xmlUniprotFactory.createSourceType();
            if (uniObj.getDatabaseType().getName().equals(REFERENCE)) {
                String val = uniObj.getId().substring(4).trim();
                BigInteger bi = new BigInteger(val);
                source.setRef(bi);

            } else {
                DbReferenceType dbRef = xmlUniprotFactory.createDbReferenceType();
                dbRef.setType(uniObj.getDatabaseType().getName());
                dbRef.setId(uniObj.getId());
                source.setDbReference(dbRef);
            }
            return source;
        }
    }
}
