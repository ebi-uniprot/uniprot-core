package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CitationXrefsImpl implements CitationXrefs {
	private final List<DBCrossReference<CitationXrefType>> xrefs;

	@JsonCreator
	public CitationXrefsImpl(@JsonProperty("xrefs") List<DBCrossReference<CitationXrefType>> xrefs) {
		if ((xrefs == null) || xrefs.isEmpty()) {
			this.xrefs = Collections.emptyList();
		} else
			this.xrefs = Collections.unmodifiableList(xrefs);
	}

	@Override
	public List<DBCrossReference<CitationXrefType>> getXrefs() {
		return xrefs;
	}

	@JsonIgnore
	@Override
	public Optional<DBCrossReference<CitationXrefType>> getTyped(CitationXrefType type) {
		return xrefs.stream().filter(xref -> xref.getDatabaseType() == type).findAny();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xrefs == null) ? 0 : xrefs.hashCode());
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
		CitationXrefsImpl other = (CitationXrefsImpl) obj;
		if (xrefs == null) {
			if (other.xrefs != null)
				return false;
		} else if (!xrefs.equals(other.xrefs))
			return false;
		return true;
	}

}
