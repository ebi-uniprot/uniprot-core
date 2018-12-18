package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.SequenceReport;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;


public class AlternativeSequenceImpl implements AlternativeSequence {
	private String originalSequence;
	private List<String> alternativeSequences;
	private SequenceReport report;
	private static final Set<FeatureType> ALTERNATIVE_SEQUENCE_SET = EnumSet.of(FeatureType.CONFLICT,
			FeatureType.MUTAGEN, FeatureType.VARIANT, FeatureType.VAR_SEQ);

	private AlternativeSequenceImpl(){
		this.alternativeSequences = Collections.emptyList();
	}

	public AlternativeSequenceImpl(String originalSequence,
			List<String> alternativeSequences,
			SequenceReport report) {
		this.originalSequence = Utils.resetNull(originalSequence);
		this.alternativeSequences = Utils.unmodifierList(alternativeSequences);
	
		this.report = report;
	}

	@Override
	public String getOriginalSequence() {
		return this.originalSequence;
	}

	@Override
	public List<String> getAlternativeSequences() {
		return this.alternativeSequences;
	}

	@Override
	public SequenceReport getReport() {
		return report;
	}

	public static boolean hasAlternativeSequence(FeatureType type) {
		return ALTERNATIVE_SEQUENCE_SET.contains(type);
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alternativeSequences == null) ? 0 : alternativeSequences.hashCode());
		result = prime * result + ((originalSequence == null) ? 0 : originalSequence.hashCode());
		result = prime * result + ((report == null) ? 0 : report.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlternativeSequenceImpl other = (AlternativeSequenceImpl) obj;
		if (alternativeSequences == null) {
			if (other.alternativeSequences != null)
				return false;
		} else if (!alternativeSequences.equals(other.alternativeSequences))
			return false;
		if (originalSequence == null) {
			if (other.originalSequence != null)
				return false;
		} else if (!originalSequence.equals(other.originalSequence))
			return false;
		if (report == null) {
			if (other.report != null)
				return false;
		} else if (!report.equals(other.report))
			return false;
		return true;
	}




	public static class SequenceReportImpl implements SequenceReport {
		private List<String> value;

		private SequenceReportImpl(){
			this.value = Collections.emptyList();
		}
		public SequenceReportImpl(List<String> value) {
			if (value == null || value.isEmpty()) {
				this.value = Collections.emptyList();
			} else {
				this.value = Collections.unmodifiableList(value);
			}
		}

		@Override
		public List<String> getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SequenceReportImpl other = (SequenceReportImpl) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

	}

}
