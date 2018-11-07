package uk.ac.ebi.uniprot.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtDBCrossReferenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.DatabaseTypeNotExistException;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;

public class DrLineConverter extends EvidenceCollector implements Converter<DrLineObject, UniProtDrObjects> {
	private final UniProtDBCrossReferenceFactory factory = UniProtDBCrossReferenceFactory.INSTANCE;
	private boolean ignoreWrongDR = false;

	public DrLineConverter() {

	}

	public DrLineConverter(boolean ignoreWrongDR) {
		this.ignoreWrongDR = ignoreWrongDR;
	}

	@Override
	public UniProtDrObjects convert(DrLineObject f) {

		UniProtDrObjects uniProtDrObjects = new UniProtDrObjects();
		if (f == null)
			return uniProtDrObjects;
		Map<Object, List<Evidence>> evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		this.addAll(evidences.values());
		for (DrLineObject.DrObject drline : f.drObjects) {
			if (drline.ssLineValue != null)
				addSSProsites(drline, uniProtDrObjects);
			else
				addDrLine(drline, uniProtDrObjects, evidences);
		}
		return uniProtDrObjects;
	}

	private void addSSProsites(DrLineObject.DrObject drline, UniProtDrObjects uniProtDrObjects) {
		if (drline.ssLineValue != null) {
			if (uniProtDrObjects.ssProsites == null)
				uniProtDrObjects.ssProsites = new ArrayList<InternalLine>();
			uniProtDrObjects.ssProsites
					.add(UniProtFactory.INSTANCE.createInternalLine(InternalLineType.PROSITE, drline.ssLineValue));
		}
	}

	private void addDrLine(DrLineObject.DrObject drline, UniProtDrObjects uniProtDrObjects,
			Map<Object, List<Evidence>> evidenceMap) throws DatabaseTypeNotExistException {

		if (drline.ssLineValue != null)
			return;
		DatabaseType type = DatabaseType.getDatabaseType(drline.DbName);
		if (type == DatabaseType.UNKNOWN) {
			if (this.ignoreWrongDR)
				return;
			else
				throw new DatabaseTypeNotExistException(drline.DbName);
		}
		String id = drline.attributes.get(0);
		String description = drline.attributes.get(1);
		String thirdAttribute = null;
		String fourthAttribute = null;
		String isoformId = null;
		List<Evidence> evidences = null;
		if (type.getNumberOfAttribute() >= 3) {
			if(drline.attributes.size()<3) {
				if(ignoreWrongDR)
					return ;
				else
					thirdAttribute ="-";
			}else {
				thirdAttribute = drline.attributes.get(2);
			}
		}
		if (type.getNumberOfAttribute() >= 4) {
			if(drline.attributes.size()<4) {
				if(ignoreWrongDR)
					return ;
				else
					fourthAttribute ="-";
			}else
				fourthAttribute = drline.attributes.get(3);
		}
		
		
		evidences = evidenceMap.get(drline);
		if ((drline.isoform != null) && (!drline.isoform.isEmpty())) {
			isoformId = drline.isoform;
		}
		uniProtDrObjects.drObjects.add(factory.createUniProtDBCrossReference(type, id, description, thirdAttribute,
				fourthAttribute, isoformId, evidences));
	}
}
