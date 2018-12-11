package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;


public class MassSpectrometryRangeImpl implements MassSpectrometryRange {
	private Range range;
	private String isoformId;

	private MassSpectrometryRangeImpl(){
		this.isoformId = "";
	}

	public MassSpectrometryRangeImpl(Integer start, Integer end, String isoformId) {
		this(new Range(start, end), isoformId);
	}


	public MassSpectrometryRangeImpl(Range range, String isoformId) {
		this.range = range;
		if (Strings.isNullOrEmpty(isoformId)) {
			this.isoformId = "";
		} else
			this.isoformId = isoformId;
	}

	@Override
	public Range getRange() {
		return range;
	}


	@Override
	public boolean hasIsoformId() {
		return ((isoformId != null) && !isoformId.isEmpty());
	}

	@Override
	public String getIsoformId() {
		return isoformId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isoformId == null) ? 0 : isoformId.hashCode());
		result = prime * result + ((range == null) ? 0 : range.hashCode());
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
		MassSpectrometryRangeImpl other = (MassSpectrometryRangeImpl) obj;
		if (isoformId == null) {
			if (other.isoformId != null)
				return false;
		} else if (!isoformId.equals(other.isoformId))
			return false;
		if (range == null) {
			if (other.range != null)
				return false;
		} else if (!range.equals(other.range))
			return false;
		return true;
	}

}
