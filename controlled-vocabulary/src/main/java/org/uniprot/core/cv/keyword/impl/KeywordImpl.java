package org.uniprot.core.cv.keyword.impl;

import org.uniprot.core.cv.keyword.Keyword;

public class KeywordImpl implements Keyword {
	private final String id;
	private final String accession;
	
	private KeywordImpl(){
		this(null, null);
	}
	public KeywordImpl(String id, String accession) {
		super();
		this.id = id;
		this.accession = accession;
	}
	public String getId() {
		return id;
	}
	public String getAccession() {
		return accession;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accession == null) ? 0 : accession.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		KeywordImpl other = (KeywordImpl) obj;
		if (accession == null) {
			if (other.accession != null)
				return false;
		} else if (!accession.equals(other.accession))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
