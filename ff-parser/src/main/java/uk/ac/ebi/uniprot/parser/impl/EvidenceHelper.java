package uk.ac.ebi.uniprot.parser.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;

public class EvidenceHelper {
	private static EvidenceInfoConverter evConverter = new EvidenceInfoConverter();
	private final static EvidenceFactory evFactory = EvidenceFactory.INSTANCE;

	public static void setEvidences(HasEvidences he, Map<Object, List<Evidence>> evidences, Object obj) {
		List<Evidence> evIds = evidences.get(obj);
	//	if (evIds != null)
	//		he.setEvidences(evIds);
	}

	public static Map<Object, List<Evidence>> convert(EvidenceInfo f) {
		return evConverter.convert(f);
	}

	public static List<Evidence> convert(List<String> evStrs) {
		return evStrs.stream().map(val -> evFactory.createFromEvidenceLine(val)).collect(Collectors.toList());

	}
}
