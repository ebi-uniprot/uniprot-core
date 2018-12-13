package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

public final class EvidenceReferenceMapper {
	private Map<Evidence, Integer> idMap = new HashMap<>();
	private Map<String, Integer> idValueMap = new HashMap<>();

	public EvidenceReferenceMapper(){
		
	}
	
	public EvidenceReferenceMapper (List<String> evidenceToIndex) {
		int index = 0;
		for (String ev : evidenceToIndex) {
			Evidence evId = UniProtFactory.INSTANCE.createEvidence(ev);
			index++;
			idMap.put(evId, index);
			this.idValueMap.put(ev, index);
		}

	}

	public void reset(Map<Evidence, Integer> idMap) {
		this.idMap.clear();
		this.idMap.putAll(idMap);
		this.idValueMap.clear();
		for (Map.Entry<Evidence, Integer> entry : idMap.entrySet()) {
			this.idValueMap.put(entry.getKey().getValue(), entry.getValue());
		}
	}

	public Integer getIndexForEvidence(String evidence) {
		return this.idValueMap.get(evidence);
	}

	public String getEvidenceValueForIndex(Integer evidenceIndex) {
		for (Map.Entry<String, Integer> entry : idValueMap.entrySet()) {
			if (entry.getValue().equals(evidenceIndex))
				return entry.getKey();
		}
		throw new IllegalArgumentException("Evidence index is not available");
	}

	public Evidence getEvidenceForIndex(Integer evidenceIndex) {
		for (Map.Entry<Evidence, Integer> entry : idMap.entrySet()) {
			if (entry.getValue().equals(evidenceIndex))
				return entry.getKey();
		}
		throw new IllegalArgumentException("Evidence index is not available");
	}

	public List<Integer> writeEvidences(List<Evidence> evidences) {
		List<Integer> indices = new ArrayList<>();
		for (Evidence evidenceId : evidences) {

			Integer evId = idMap.get(evidenceId);
			if (evId != null) {
				indices.add(evId);
			} else {
				int ev1 = getIndexForEvidence(evidenceId.getValue());
				if (ev1 != -1)
					indices.add(ev1);
				else
					throw new IllegalArgumentException(
							"The evidence reference does not contain an old reference format: "
									+ evidenceId.toString());
			}
		}
		return indices;
	}

	public List<Evidence> parseEvidenceIds(List<Integer> indices) {
		List<Evidence> evidences = new ArrayList<>();
		if (evidences != null && !evidences.isEmpty()) {
			for (Map.Entry<Evidence, Integer> entry : idMap.entrySet()) {
				for (Integer evidence : indices) {
					if (entry.getValue().equals(evidence)) {
						evidences.add(entry.getKey());
					}
				}
			}
		}
		return evidences;
	}
}
