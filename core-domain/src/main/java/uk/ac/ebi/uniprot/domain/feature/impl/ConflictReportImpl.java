package uk.ac.ebi.uniprot.domain.feature.impl;

import uk.ac.ebi.uniprot.domain.feature.ConflictReport;

public class ConflictReportImpl implements ConflictReport {
	private final String reference;
	private final String report;
	public ConflictReportImpl(String reference, String report) {
		this.reference = reference;
		this.report = report;
	}
	@Override
	public String getReference() {
		return reference;
	}

	@Override
	public String getReport() {
		return report;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
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
		ConflictReportImpl other = (ConflictReportImpl) obj;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (report == null) {
			if (other.report != null)
				return false;
		} else if (!report.equals(other.report))
			return false;
		return true;
	}

}
