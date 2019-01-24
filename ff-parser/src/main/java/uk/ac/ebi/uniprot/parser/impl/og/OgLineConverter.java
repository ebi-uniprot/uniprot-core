package uk.ac.ebi.uniprot.parser.impl.og;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceConverterHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OgLineConverter extends EvidenceCollector implements
		Converter<OgLineObject, List<Organelle>> {
	private final UniProtFactory factory = UniProtFactory.INSTANCE;

	@Override
	public List<Organelle> convert(OgLineObject f) {
		List<Organelle> organelles = new ArrayList<Organelle>();
		Map<Object, List<Evidence>> evidenceMap = EvidenceConverterHelper.convert(f
				.getEvidenceInfo());
		this.addAll(evidenceMap.values());
		for (OgLineObject.OgEnum ogEnum : f.ogs) {
			GeneEncodingType type = GeneEncodingType.UNKOWN;
			switch (ogEnum) {
			case HYDROGENOSOME:
				type = GeneEncodingType.HYDROGENOSOME;
				break;
			case MITOCHONDRION:
				type = GeneEncodingType.MITOCHONDRION;
				break;
			case NUCLEOMORPH:
				type = GeneEncodingType.NUCLEOMORPH;
				break;
			case PLASTID:
				type = GeneEncodingType.PLASTID;
				break;
			case PLASTID_APICOPLAST:
				type = GeneEncodingType.APICOPLAST_PLASTID;
				break;
			case PLASTID_CHLOROPLAST:
				type = GeneEncodingType.CHLOROPLAST_PLASTID;
				break;
			case PLASTID_ORGANELLAR_CHROMATOPHORE:
				type = GeneEncodingType.CHROMATOPHORE_PLASTID;
				break;
			case PLASTID_CYANELLE:
				type = GeneEncodingType.CYANELLE_PLASTID;
				break;
			case PLASTID_NON_PHOTOSYNTHETIC:
				type = GeneEncodingType.NON_PHOTOSYNTHETIC_PLASTID;
				break;
			case PLASMID:
				type = GeneEncodingType.PLASMID;
				break;
			default:
				type = GeneEncodingType.UNKOWN;
				break;

			}
			Organelle org = factory.createOrganelle(type, "", evidenceMap.get(ogEnum));
		
			organelles.add(org);
		}

		for (String val : f.plasmidNames) {
			Organelle org = factory.createOrganelle(GeneEncodingType.PLASMID, val, evidenceMap.get(val));
			organelles.add(org);
		}
		return organelles;
	}

}
