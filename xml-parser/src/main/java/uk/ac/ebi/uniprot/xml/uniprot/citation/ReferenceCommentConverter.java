package uk.ac.ebi.uniprot.xml.uniprot.citation;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;
import org.uniprot.core.uniprot.builder.ReferenceCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SourceDataType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;

import java.util.*;

public class ReferenceCommentConverter implements Converter<SourceDataType, List<ReferenceComment>> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	private static final Logger logger = LoggerFactory.getLogger(ReferenceCommentConverter.class);
	private static final List<ReferenceCommentType> orderList = Arrays.asList(ReferenceCommentType.STRAIN,
			ReferenceCommentType.PLASMID, ReferenceCommentType.TRANSPOSON, ReferenceCommentType.TISSUE);
	private final Comparator<ReferenceComment> rcComparator = new ReferenceCommentComparator();

	public ReferenceCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public ReferenceCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public List<ReferenceComment> fromXml(SourceDataType xmlObj) {
		if ((xmlObj == null) || (xmlObj.getStrainOrPlasmidOrTransposon() == null))
			return Collections.emptyList();
		List<Object> listSourceXML = xmlObj.getStrainOrPlasmidOrTransposon();

		List<ReferenceComment> rcList = new ArrayList<>();
		for (Object o : listSourceXML) {
			ReferenceComment rc = null;
			if (o instanceof SourceDataType.Plasmid) {
				SourceDataType.Plasmid plasmid = (SourceDataType.Plasmid) o;
				rc = createReferenceComment(ReferenceCommentType.PLASMID,
						plasmid.getValue(), evRefMapper.parseEvidenceIds(plasmid.getEvidence()));
			} else if (o instanceof SourceDataType.Strain) {
				SourceDataType.Strain strain = (SourceDataType.Strain) o;
				rc = createReferenceComment(ReferenceCommentType.STRAIN,
						strain.getValue(), evRefMapper.parseEvidenceIds(strain.getEvidence()));
			} else if (o instanceof SourceDataType.Tissue) {
				SourceDataType.Tissue tissue = (SourceDataType.Tissue) o;
				rc = createReferenceComment(ReferenceCommentType.TISSUE,
						tissue.getValue(), evRefMapper.parseEvidenceIds(tissue.getEvidence()));
			} else if (o instanceof SourceDataType.Transposon) {
				SourceDataType.Transposon transposon = (SourceDataType.Transposon) o;
				rc = createReferenceComment(ReferenceCommentType.TRANSPOSON,
						transposon.getValue(), evRefMapper.parseEvidenceIds(transposon.getEvidence()));
			}
			if (rc != null)
				rcList.add(rc);

		}
		return rcList;
	}

	private ReferenceComment createReferenceComment(ReferenceCommentType type, String value, List<Evidence> evidences) {
		return new ReferenceCommentBuilder()
				.type(type)
				.value(value)
				.evidences(evidences)
				.build();
	}

	@Override
	public SourceDataType toXml(List<ReferenceComment> uniObj) {
		if ((uniObj == null) || uniObj.isEmpty()) {
			return null;
		}
		SourceDataType sourceDataXML = xmlUniprotFactory.createSourceDataType();

		// Sort the list first
		List<ReferenceComment> rcList = new ArrayList<>(uniObj);

		Collections.sort(rcList, rcComparator);

		for (ReferenceComment rc : rcList) {
			ReferenceCommentType rcType = rc.getType();
			if (Strings.isNullOrEmpty(rc.getValue())) {
				logger.error("No sequence sample source detected ");
				continue;
			}

			switch (rcType) {
			case PLASMID:
				SourceDataType.Plasmid plasmid = xmlUniprotFactory.createSourceDataTypePlasmid();
				plasmid.setValue(rc.getValue());
				if (!rc.getEvidences().isEmpty())
					plasmid.getEvidence().addAll(convertEvidence(rc));
				sourceDataXML.getStrainOrPlasmidOrTransposon().add(plasmid);
				break;
			case STRAIN:
				SourceDataType.Strain strain = xmlUniprotFactory.createSourceDataTypeStrain();
				strain.setValue(rc.getValue());
				if (!rc.getEvidences().isEmpty())
					strain.getEvidence().addAll(convertEvidence(rc));
				sourceDataXML.getStrainOrPlasmidOrTransposon().add(strain);
				break;
			case TISSUE:
				SourceDataType.Tissue tissue = xmlUniprotFactory.createSourceDataTypeTissue();
				tissue.setValue(rc.getValue());
				if (!rc.getEvidences().isEmpty())
					tissue.getEvidence().addAll(convertEvidence(rc));
				sourceDataXML.getStrainOrPlasmidOrTransposon().add(tissue);
				break;
			case TRANSPOSON:
				SourceDataType.Transposon transposon = xmlUniprotFactory.createSourceDataTypeTransposon();
				transposon.setValue(rc.getValue());
				if (!rc.getEvidences().isEmpty())
					transposon.getEvidence().addAll(convertEvidence(rc));
				sourceDataXML.getStrainOrPlasmidOrTransposon().add(transposon);
				break;
			}
		}

		return sourceDataXML;
	}

	private List<Integer> convertEvidence(ReferenceComment rc) {
		if (!rc.getEvidences().isEmpty()) {
			return evRefMapper.writeEvidences(rc.getEvidences());
		} else
			return Collections.emptyList();
	}

	private class ReferenceCommentComparator implements Comparator<ReferenceComment> {
		@Override
		public int compare(ReferenceComment o1, ReferenceComment o2) {

			if (orderList.indexOf(o1.getType()) > orderList.indexOf(o2.getType())) {
				return 1;
			}
			if (orderList.indexOf(o1.getType()) < orderList.indexOf(o2.getType())) {
				return -1;
			}

			return 0;
		}
	}
}
