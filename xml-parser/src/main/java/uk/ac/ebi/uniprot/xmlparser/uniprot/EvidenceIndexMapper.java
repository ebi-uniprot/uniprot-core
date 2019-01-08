package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

public final class EvidenceIndexMapper {
	private Map<Evidence, Integer> evidenceToIndexMap = new HashMap<>();
	private Map<Integer, Evidence> indexToEvidenceMap = new HashMap<>();
	private final Comparator<Integer> comparator = (o1, o2) -> o1.compareTo(o2);

	public EvidenceIndexMapper() {

	}

	public EvidenceIndexMapper(List<Evidence> evidences) {
		reset(evidences);
	}

	public void reset(List<Evidence> evidences) {
		this.evidenceToIndexMap.clear();
		this.indexToEvidenceMap.clear();
		int index = 1;
		for (Evidence evidence : evidences) {
			if (evidenceToIndexMap.containsKey(evidence))
				continue;
			evidenceToIndexMap.put(evidence, index);
			indexToEvidenceMap.put(index, evidence);

			index++;
		}
	}

	public void reset(Map<Evidence, Integer> evidences) {
		this.evidenceToIndexMap.clear();
		this.indexToEvidenceMap.clear();
		this.evidenceToIndexMap.putAll(evidences);
		for (Map.Entry<Evidence, Integer> entry : evidences.entrySet()) {
			this.indexToEvidenceMap.put(entry.getValue(), entry.getKey());
		}
	}

	public Evidence getEvidenceForIndex(Integer evidenceIndex) {
		Evidence evidence = indexToEvidenceMap.get(evidenceIndex);
		if (evidence == null) {
			throw new IllegalArgumentException("Evidence index is not available");
		}
		return evidence;
	}

	public List<Integer> writeEvidences(List<Evidence> evidences) {
		List<Integer> indices = new ArrayList<>();
		for (Evidence evidenceId : evidences) {

			Integer evId = evidenceToIndexMap.get(evidenceId);
			if (evId != null) {
				indices.add(evId);
			} else {
				int max = getMaxIndex() + 1;
				evidenceToIndexMap.put(evidenceId, max);
				indexToEvidenceMap.put(max, evidenceId);
				indices.add(max);
			}
		}
		return indices;
	}

	private int getMaxIndex() {
		Optional<Integer> val = this.evidenceToIndexMap.values().stream().max(comparator);
		return val.orElse(0);
	}

	public List<Evidence> parseEvidenceIds(List<Integer> indices) {
		if ((indices == null) || indices.isEmpty()) {
			return Collections.emptyList();
		}
		return indices.stream().map(index -> indexToEvidenceMap.get(index)).filter(val -> val != null)
				.collect(Collectors.toList());
	}
}
