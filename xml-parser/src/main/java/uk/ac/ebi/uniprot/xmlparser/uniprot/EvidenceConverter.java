package uk.ac.ebi.uniprot.xmlparser.uniprot;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SourceType;
import uk.ac.ebi.uniprot.xmlparser.Converter;

import java.math.BigInteger;

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
        EvidenceCode evCode = EvidenceCode.codeOf(xmlObj.getType());
        DBCrossReference<uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType> xref = null;
        if (xmlObj.getSource() != null) {
            xref = xrefConverter.fromXml(xmlObj.getSource());
        }
        return new EvidenceBuilder()
                .evidenceCode(evCode)
                .databaseId(xref.getId())
                .databaseName(xref.getDatabaseType().getName())
                .build();
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
            implements Converter<SourceType, DBCrossReference<uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType>> {
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
        public DBCrossReference<uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType> fromXml(SourceType xmlObj) {
            if (xmlObj.getDbReference() != null) {
                return new DBCrossReferenceBuilder<uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType>()
                        .databaseType(new uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType(xmlObj.getDbReference()
                                                                                                         .getType()))
                        .id(xmlObj.getDbReference().getId())
                        .build();
            } else {
                String attr = REF + xmlObj.getRef().toString();
                return new DBCrossReferenceBuilder<uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType>()
                        .databaseType(new uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType(REFERENCE))
                        .id(attr)
                        .build();
            }
        }

        @Override
        public SourceType toXml(DBCrossReference<uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType> uniObj) {
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
