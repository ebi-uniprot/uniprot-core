package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.math.BigInteger;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SourceType;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class EvidenceConverter implements Converter<EvidenceType, Evidence> {
	private final ObjectFactory xmlUniprotFactory = new ObjectFactory();
	private final EvidenceXrefConverter xrefConverter = new EvidenceXrefConverter();

	@Override
	public Evidence fromXml(EvidenceType xmlObj) {
		EvidenceCode evCode = EvidenceCode.codeOf(xmlObj.getType());
		DBCrossReference<uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType> xref = null;
		if (xmlObj.getSource() != null) {
			xref = xrefConverter.fromXml(xmlObj.getSource());
		}
		return UniProtFactory.INSTANCE.createEvidence(evCode, xref);
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
			implements Converter<SourceType, DBCrossReference<uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType>> {
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
		public DBCrossReference<uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType> fromXml(SourceType xmlObj) {
			if (xmlObj.getDbReference() != null) {
				return UniProtFactory.INSTANCE.createDBCrossReference(
						new uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType(xmlObj.getDbReference().getType()),
						xmlObj.getDbReference().getId());
			} else {
				String attr = REF + xmlObj.getRef().toString();
				return UniProtFactory.INSTANCE.createDBCrossReference(
						new uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType(REFERENCE), attr);
			}
		}

		@Override
		public SourceType toXml(DBCrossReference<uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType> uniObj) {
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
